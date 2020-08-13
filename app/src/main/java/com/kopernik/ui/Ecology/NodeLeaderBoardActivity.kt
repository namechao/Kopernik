package com.kopernik.ui.Ecology

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.Ecology.adapter.NodeLeaderBoardAdapter
import com.kopernik.ui.Ecology.entity.NodeLeaderBoardBean
import com.kopernik.ui.Ecology.viewModel.NodeLeaderBoardViewModel
import kotlinx.android.synthetic.main.activity_node_leader_board.*

class NodeLeaderBoardActivity : NewBaseActivity<NodeLeaderBoardViewModel,ViewDataBinding>(),
    OnStatusChildClickListener {

    private var statusLayoutManager: StatusLayoutManager? = null
    private var adapter: NodeLeaderBoardAdapter? = null
    private var pagerNumber = 1


    override fun layoutId()= R.layout.activity_node_leader_board

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.node_leader_board))
        adapter = NodeLeaderBoardAdapter(R.layout.item_node_leader_board)
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
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
    }

    override fun initData() {

    }
    private fun getData() {
        viewModel.run {
            var map= mapOf("pageNumber" to pagerNumber.toString(),"pageSize" to 18.toString())
            getRankNodeList(map).observe(this@NodeLeaderBoardActivity, Observer {
                if (it.status==200){
                    val datas: List<NodeLeaderBoardBean.DatasBean>? =
                        it.data.datas
                    if (pagerNumber == 1) {
                        if (datas == null || datas.size == 0) {
                            smartRefreshLayout.finishRefresh(300)
                            statusLayoutManager!!.showEmptyLayout()
                            smartRefreshLayout.setNoMoreData(true)
                            return@Observer
                        }
                        if (datas.size == 18) {
                            smartRefreshLayout.finishRefresh(300)
                            pagerNumber++
                        } else {
                            smartRefreshLayout.finishRefreshWithNoMoreData()
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        adapter!!.setNewData(datas)
                    } else {
                        if (datas != null) {
                            if (datas.size < 18) {
                                smartRefreshLayout.finishLoadMoreWithNoMoreData()
                            } else {
                                pagerNumber++
                                smartRefreshLayout.finishLoadMore(true)
                            }
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        if (datas != null) {
                            adapter!!.addData(datas)
                        }
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
        getData()
    }

    override fun onCustomerChildClick(view: View?) {}
}