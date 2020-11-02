package com.kopernik.ui.asset

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.dialog.UTKTransferDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.DepositHistoryAdapter
import com.kopernik.ui.asset.adapter.UYTDepositWithdrawlAssetAdapter
import com.kopernik.ui.asset.adapter.UYTTransferAssetAdapter
import com.kopernik.ui.asset.entity.DepositWithdarwlRecord
import com.kopernik.ui.asset.entity.UYTTransferRecord
import com.kopernik.ui.asset.viewModel.UYTAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import kotlinx.android.synthetic.main.activity_utc_asset.*
import kotlinx.android.synthetic.main.activity_uyt_asset.*
import kotlinx.android.synthetic.main.activity_uyt_asset.assetTotal
import kotlinx.android.synthetic.main.activity_uyt_asset.ivBack
import kotlinx.android.synthetic.main.activity_uyt_asset.llTitle
import kotlinx.android.synthetic.main.activity_uyt_asset.llTitle1
import kotlinx.android.synthetic.main.activity_uyt_asset.recyclerView
import kotlinx.android.synthetic.main.activity_uyt_asset.recyclerView1
import kotlinx.android.synthetic.main.activity_uyt_asset.smartRefreshLayout
import kotlinx.android.synthetic.main.activity_uyt_asset.transfer

class DepositCoinHistoryActivity : NewBaseActivity<UYTAssetViewModel, ViewDataBinding>() {

    private var pager=1
    private var adapter= DepositHistoryAdapter(arrayListOf())
    private var type = ""
    private var historyType = ""
    override fun layoutId()=R.layout.activity_deposit_coin_history

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("")
        intent.getStringExtra("coinType")?.let {
            type=it
        }
        intent.getStringExtra("historyType")?.let {
            historyType=it
        }
    }
    //初始化数据
    override fun initData() {
        recyclerView.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getListData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pager=1
                getListData()
            }
        })
        smartRefreshLayout.autoRefresh()
        adapter.setOnItemClickListener { adapter, view, position ->
            ToastUtils.showShort(this@DepositCoinHistoryActivity,getString(R.string.button_purchase_not_open))
        }
    }

    //获取资产和下边记录列表
    private fun getListData() {
        viewModel.run {
                var map = mapOf("type" to type,"operate" to historyType,"pageNumber" to pager.toString(),"pageSize" to "10")
                rechargeCashRecord(map).observe(this@DepositCoinHistoryActivity, Observer {

                    if (it.status==200){
                        val datas: List<DepositWithdarwlRecord>?=it.data.datas
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

                            adapter?.setNewData(datas)
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
                                adapter?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })


        }


    }

}