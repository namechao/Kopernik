package com.kopernik.ui.asset

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.TransferAssetAdapter
import com.kopernik.ui.asset.entity.UYTTransferRecord
import com.kopernik.ui.asset.viewModel.UYTTESTAssetViewModel
import kotlinx.android.synthetic.main.activity_transfer_history.*

class TransferHistoryActivity : NewBaseActivity<UYTTESTAssetViewModel, ViewDataBinding>() {

    private var pager1=1
    private var adapter1 = TransferAssetAdapter(arrayListOf())
    private var type = ""
    override fun layoutId()=R.layout.activity_transfer_history

    override fun initView(savedInstanceState: Bundle?) {
        intent.getStringExtra("coinType")?.let {
            type = it
        }
    }
    //初始化数据
    override fun initData() {
        recyclerView1.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView1.adapter = adapter1
         setTitle("")
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getListData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pager1=1
                getListData()
            }
        })
        smartRefreshLayout.autoRefresh()
        adapter1.setOnItemClickListener { adapter, view, position ->
            ToastUtils.showShort(this@TransferHistoryActivity,getString(R.string.button_purchase_not_open))
        }
    }




    //获取资产和下边记录列表
    private fun getListData() {
        viewModel.run {

                var map = mapOf("type" to type,"pageNumber" to pager1.toString(),"pageSize" to "10")
                transferRecord(map).observe(this@TransferHistoryActivity, Observer {

                    if (it.status==200){
                        val datas: List<UYTTransferRecord>?=it.data.datas
                        if (pager1 == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager1++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adapter1?.setNewData(datas)
                        } else {
                            if (datas != null) {
                                if (datas.size < 10) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                                } else {
                                    pager1++
                                    smartRefreshLayout.finishLoadMore(true)
                                }
                            }
                            if (datas != null) {
                                adapter1?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
        }
    }

}