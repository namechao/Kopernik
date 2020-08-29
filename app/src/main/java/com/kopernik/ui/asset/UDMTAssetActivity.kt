package com.kopernik.ui.asset

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.UDMTDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.adapter.UDMTFinanceRecordsAdapter
import com.kopernik.ui.asset.entity.UTDMRecord
import com.kopernik.ui.asset.viewModel.UDMTAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_udmt_asset.*


class UDMTAssetActivity : NewFullScreenBaseActivity<UDMTAssetViewModel, ViewDataBinding>() {
    override fun layoutId()= R.layout.activity_udmt_asset
    private var pager=1
    private var allConfigEntity: AllConfigEntity?=null
    var adpter= UDMTFinanceRecordsAdapter(arrayListOf())
    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager= LinearLayoutManager(this)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_finance_record_head,null))
        recyclerView.adapter=adpter
        ///兑换
//        synthesisUtc.setOnClickListener {
//            allConfigEntity?.let {
//                var exchangeDialog = UDMTDialog.newInstance(it)
//                exchangeDialog!!.setOnRequestListener(object : UDMTDialog.RequestListener {
//                    override fun onRequest(type: Int, params: String) {
//
//                    }
//                })
//                exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
//            }
//
//        }
        smartRefreshLayout.setOnRefreshLoadMoreListener(object:OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
        }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager=1
                getList()
                getCurrentAsset()
            }

        })
        smartRefreshLayout.autoRefresh()
    }
    private  fun  getCurrentAsset(){
        viewModel.run {
            getAssetConfig().observe(this@UDMTAssetActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                    assetTotal.text= BigDecimalUtils.roundDOWN(it.data.utc,2)
                }
            })
        }
    }
    fun getList(){
        viewModel.run {

                var map = mapOf("pageNumber" to pager.toString(),"pageSize" to "10")
            gainsDetailRecord(map).observe(this@UDMTAssetActivity, Observer {

                    if (it.status==200){
                        val datas: List<UTDMRecord>?=it.data.datas
                        if (pager == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adpter?.setNewData(datas)
                        } else {
                            if (datas != null) {
                                if (datas.size < 10) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                                } else {
                                    pager++
                                    smartRefreshLayout.finishLoadMore(true)
                                }
                            }
                            if (datas != null) {
                                adpter?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }

        }
    override fun initData() {

    }

}