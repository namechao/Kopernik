package com.kopernik.ui.Ecology

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.Ecology.adapter.TimeLineAdapter
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.Ecology.entity.TimeLineItemBean
import com.kopernik.ui.Ecology.viewModel.RedeemTimeLineViewModel
import kotlinx.android.synthetic.main.activity_redeem_time_line.*
import java.util.*

class RedeemTimeLineActivity : NewBaseActivity<RedeemTimeLineViewModel,ViewDataBinding>() ,
    OnStatusChildClickListener {
    companion object{
        val REDEEM = 1 //赎回

        val TURN = 2 //转投
    }


    private var statusLayoutManager: StatusLayoutManager? = null
    private var requestType = ""
    private var nodeHash = ""
    private var pagerNumber = 1
    private val headList: MutableList<String> =
        ArrayList()
    private var adapter: TimeLineAdapter? = null

    override fun layoutId()=R.layout.activity_redeem_time_line

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.timeline))
        nodeHash = intent.getStringExtra("nodeHash")
        requestType = if (intent.getIntExtra("type", 1) === RedeemTimeLineActivity.REDEEM) {
            "REDEEM"
        } else {
            "SWITCH_TO"
        }
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        adapter = TimeLineAdapter(arrayListOf(), 1)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setAdapter(adapter)
        recyclerView.addItemDecoration(
            PinnedHeaderItemDecoration.Builder(1).create()
        )
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
    }

    override fun initData() {

    }
    private fun getData() {
        viewModel.run {
            var map= mapOf("type" to requestType,
                          "nodeHash" to nodeHash,
                          "pageNumber" to pagerNumber.toString(),
                           "pagerSize" to 10.toString())
       getFreeRecord(map).observe(this@RedeemTimeLineActivity, androidx.lifecycle.Observer {
           if (it.status==200){
               var datas: List<TimeLineBean.DatasBean>? = null
                    if (it.data != null) datas = it.data.datas
                    if (pagerNumber == 1) {
                        headList.clear()
                        if (datas == null || datas.size == 0) {
                            smartRefreshLayout.finishRefresh(300)
                            statusLayoutManager!!.showEmptyLayout()
                            smartRefreshLayout.setNoMoreData(true)
                            return@Observer
                        }
                        if (datas.size == 10) {
                            smartRefreshLayout.finishRefresh(300)
                            pagerNumber++
                        } else {
                            smartRefreshLayout.finishRefreshWithNoMoreData()
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        val tempDatas: MutableList<TimeLineItemBean> =
                            ArrayList<TimeLineItemBean>()
                        for (item in datas) {
                            if (headList.contains(item.year)) {
                                val itemBean =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_DATA, item)
                                tempDatas.add(itemBean)
                            } else {
                                item.year?.let { it1 -> headList.add(it1) }
                                val titleItem =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_HEADER, item.year)
                                tempDatas.add(titleItem)
                                val itemBean =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_DATA, item)
                                tempDatas.add(itemBean)
                            }
                        }
                        adapter!!.setNewData(tempDatas)
                    } else {
                        if (datas!!.size < 10) {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            pagerNumber++
                            smartRefreshLayout.finishLoadMore(true)
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        val tempDatas: MutableList<TimeLineItemBean> =
                            ArrayList<TimeLineItemBean>()
                        for (item in datas) {
                            if (headList.contains(item.year)) {
                                val itemBean =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_DATA, item)
                                tempDatas.add(itemBean)
                            } else {
                                item.year?.let { it1 -> headList.add(it1) }
                                val titleItem =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_HEADER, item.year)
                                tempDatas.add(titleItem)
                                val itemBean =
                                    TimeLineItemBean(TimeLineItemBean.TYPE_DATA, item)
                                tempDatas.add(itemBean)
                            }
                        }
                        adapter!!.addData(tempDatas)
                    }
           } else{
               ErrorCode.showErrorMsg(getActivity(), it.status)
           }
        })
        }

    }

    override fun onEmptyChildClick(view: View?) {
        getData()
    }

    override fun onErrorChildClick(view: View?) {
        pagerNumber = 1
        getData()
    }

    override fun onCustomerChildClick(view: View?) {}
}