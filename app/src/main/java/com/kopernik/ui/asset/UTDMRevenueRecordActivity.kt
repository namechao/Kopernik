package com.kopernik.ui.asset

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.ui.asset.adapter.UDMTRevenueRecordsAdapter
import com.kopernik.ui.asset.entity.UTDMRecord
import com.kopernik.ui.asset.viewModel.UDMTAssetViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_udmt_revenue_record.*


class UTDMRevenueRecordActivity : NewBaseActivity<UDMTAssetViewModel, ViewDataBinding>() {
    override fun layoutId()= R.layout.activity_udmt_revenue_record
    private var pager=1
    var adpter= UDMTRevenueRecordsAdapter(arrayListOf())
    override fun initView(savedInstanceState: Bundle?) {
       setTitle(getString(R.string.income_record))
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adpter
        smartRefreshLayout.setOnRefreshLoadMoreListener(object:OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
        }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager=1
                getList()
            }

        })
        smartRefreshLayout.autoRefresh()
    }

    fun getList(){
        viewModel.run {

                var map = mapOf("pageNumber" to pager.toString(),"pageSize" to "10")
            gainsDetailRecord(map).observe(this@UTDMRevenueRecordActivity, Observer {

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