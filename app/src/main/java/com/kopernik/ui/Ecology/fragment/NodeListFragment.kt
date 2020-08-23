package com.kopernik.ui.Ecology.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.Ecology.adapter.NodeAdapter
import com.kopernik.ui.Ecology.entity.NodeBean
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_node_list.*
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.VoteBetActivity
import com.kopernik.ui.Ecology.viewModel.NodeListViewModel

class NodeListFragment : NewBaseFragment<NodeListViewModel, ViewDataBinding>(),
    OnStatusChildClickListener {
    private var adapter: NodeAdapter? = null
    private var type = 0
    private var pagerNumber = 1
    private var statusLayoutManager: StatusLayoutManager? = null


    companion object {
        fun newInstance(type: Int): NodeListFragment {
            val fragment = NodeListFragment()
            val bundle = Bundle()
         bundle.putInt("type", type)
         fragment.arguments = bundle
         return fragment
     }
 }

    override fun layoutId()=R.layout.fragment_node_list

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        type = arguments!!.getInt("type", 1)
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, activity!!, this)
        val layoutManager =
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            SpacesItemDecoration(
                APPHelper.dp2px(activity!!, 10.toFloat()),
                APPHelper.dp2px(activity!!, 100.toFloat())
            )
        )
        adapter = NodeAdapter(R.layout.item_node_list)
        recyclerView.adapter = adapter
        val footerView: View = LayoutInflater.from(activity!!)
            .inflate(R.layout.layout_empty_footer, null)
        adapter?.addFooterView(footerView)

        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                requestNodeList()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                requestNodeList()
            }
        })
        smartRefreshLayout.autoRefresh()
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view.id === R.id.node_vote_spb) {
//                if (UserConfig.singleton?.getAccount() == null) return@setOnItemChildClickListener
                val bean: NodeBean.DatasBean = adapter.data[position] as NodeBean.DatasBean
                LaunchConfig.startVoteBetAc(activity!!, VoteBetActivity.VOTE, bean.nodeHash)
            }
        }
        //点击item进入详情
        adapter?.setOnItemClickListener { adapter, view, position ->
            val bean: NodeBean.DatasBean = adapter.data[position] as NodeBean.DatasBean
            LaunchConfig.startWebViewAc(
                activity!!,
                Api.appSearch + bean.nodeHash,
                getString(R.string.node_details)
            )
        }
    }

    override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.reloadVoteList)) {
            smartRefreshLayout.autoRefresh()
        }
    }


    private fun requestNodeList() {
        val url: String
        url = if (type == 1) {
            Api.verifyNodelist
        } else if (type == 2) {
            Api.syncNodelist
        } else {
            Api.quitNodelist
        }
        var map = mutableMapOf<String, String>(
            "pageNumber" to pagerNumber.toString()
        )
        if (UserConfig.singleton?.accountBean != null) {
//            UserConfig.singleton?.token?.let { map.put("token", it) }
        }
        viewModel.run {
            getNodeList(url, map).observe(this@NodeListFragment, Observer {
                if (it.status == 200) {
                    val datas: List<NodeBean.DatasBean> =
                        it.data.datas!!
                    if (pagerNumber == 1) {
                        smartRefreshLayout.finishRefresh(300)
                        if (datas == null || datas.size == 0) {
                            statusLayoutManager!!.showEmptyLayout()
                            smartRefreshLayout.setNoMoreData(true)
                            return@Observer
                        }
                        if (datas.size > 9) {
                            pagerNumber++
                        } else {
                            smartRefreshLayout.finishRefreshWithNoMoreData()
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        adapter!!.setNewData(datas)
                    } else {
                        if (datas.size < 10) {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            pagerNumber++
                            smartRefreshLayout.finishLoadMore(true)
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        adapter!!.addData(datas)
                    }


                } else {
                    statusLayoutManager!!.showErrorLayout()
                    ErrorCode.showErrorMsg(activity, it.status)
                }

            })
        }
    }

    override  fun onEmptyChildClick(view: View?) {
        requestNodeList()
    }

    override fun onErrorChildClick(view: View?) {
        requestNodeList()
    }

    override  fun onCustomerChildClick(view: View?) {}

}