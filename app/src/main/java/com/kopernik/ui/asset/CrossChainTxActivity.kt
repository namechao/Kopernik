package com.kopernik.ui.asset

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.asset.adapter.CrossChainTxAdapter
import com.kopernik.ui.asset.entity.CrossChainTxBean
import com.kopernik.ui.asset.viewModel.CrossChainTxViewModel
import com.kopernik.app.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_cross_chain_tx.*

class CrossChainTxActivity : NewBaseActivity<CrossChainTxViewModel,ViewDataBinding>() ,
    OnStatusChildClickListener {
    private var adapter: CrossChainTxAdapter? = null
    private var statusLayoutManager: StatusLayoutManager? = null
    private var pagerNumber = 1
    private var chainType: String? = null
    override fun layoutId()=R.layout.activity_cross_chain_tx

    override fun initView(savedInstanceState: Bundle?) {
        val type = intent.getIntExtra("type", 1)
        if (type == 1) {
            chainType = "UYT"
            setTitle("UYT" + resources.getString(R.string.nav_title_asset))
        } else if (type == 2) {
            chainType = "BTC"
            setTitle("U-BTC" + resources.getString(R.string.nav_title_asset))
        } else if (type == 3) {
            chainType = "ETH"
            setTitle("U-ETH" + resources.getString(R.string.nav_title_asset))
        } else if (type == 4) {
            chainType = "USDT"
            setTitle("U-USDT" + resources.getString(R.string.nav_title_asset))
        } else if (type == 5) {
            chainType = "HT"
            setTitle("U-HT" + resources.getString(R.string.nav_title_asset))
        }
        adapter = CrossChainTxAdapter(type)
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setAdapter(adapter)
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                getData()
            }
        })
        adapter?.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val item: CrossChainTxBean.DatasBean =
                    adapter.data[position] as CrossChainTxBean.DatasBean
                cancelWithdraw(item.id)
            }
    }

    override fun initData() {

    }
    private fun getData() {
        viewModel.run {
            var map= mapOf<String,String>(
                "pageNumber" to  pagerNumber.toString(),
             "pageSize"  to  10.toString(),
             "iconType" to chainType.toString())
            getAssetTxRecord(map).observe(this@CrossChainTxActivity, Observer {
                if (it.status==200){
                val datas: List<CrossChainTxBean.DatasBean>? =
                    it.data.datas
                if (pagerNumber == 1) {
                    if (datas == null || datas.size == 0) {
                        smartRefreshLayout.finishRefresh(300)
                        statusLayoutManager?.showEmptyLayout()
                        smartRefreshLayout.setNoMoreData(true)
                        return@Observer
                    }
                    if (datas.size == 10) {
                        smartRefreshLayout.finishRefresh(300)
                        pagerNumber++
                    } else {
                        smartRefreshLayout.finishRefreshWithNoMoreData()
                    }
                    statusLayoutManager?.showSuccessLayout()
                    adapter?.setNewData(datas)
                } else {
                    if (datas?.size!! < 10) {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        pagerNumber++
                        smartRefreshLayout.finishLoadMore(true)
                    }
                    statusLayoutManager!!.showSuccessLayout()
                    adapter!!.addData(datas)
                }
            }else{
                    ErrorCode.showErrorMsg(this@CrossChainTxActivity,it.status)
                }
            }
            )
        }
    }
    private fun cancelWithdraw(id: Int) {
       viewModel.run {
           var url = "asset/cancelcash/$id"
           cancelWithdraw(url).observe(this@CrossChainTxActivity, Observer {
               if (it.status == 200) {
                   ToastUtils.showShort(
                       getActivity(),
                       getActivity()!!.resources.getString(R.string.cancel_withdraw_success)
                   )
                   pagerNumber = 1
                   smartRefreshLayout.autoRefresh()
               } else {
                   ErrorCode.showErrorMsg(this@CrossChainTxActivity, it.status)
               }
           })
       }
    }

    override fun onEmptyChildClick(view: View?) {
        getData()
    }

    override fun onErrorChildClick(view: View?) {
        getData()
    }

    override fun onCustomerChildClick(view: View?) {}
}