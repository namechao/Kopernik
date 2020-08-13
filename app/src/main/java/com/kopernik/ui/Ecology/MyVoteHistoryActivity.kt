package com.kopernik.ui.Ecology

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
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.Ecology.adapter.MyVoteHistoryAdapter
import com.kopernik.ui.Ecology.entity.VoteHistoryBean
import com.kopernik.ui.Ecology.viewModel.MyVoteHistoryViewModel
import kotlinx.android.synthetic.main.activity_my_vote_history.*

class MyVoteHistoryActivity : NewBaseActivity<MyVoteHistoryViewModel, ViewDataBinding>(),
    OnStatusChildClickListener {

    private var adapter: MyVoteHistoryAdapter? = null
    private var statusLayoutManager: StatusLayoutManager? = null
    private var pagerNumber = 1
    override fun layoutId() = R.layout.activity_my_vote_history

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.history))
        adapter = MyVoteHistoryAdapter(R.layout.item_vote_history)
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getHistory()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                getHistory()
            }
        })
    }

    override fun initData() {

    }


    private fun getHistory() {
        viewModel.run {
            var map = mapOf("pageNumber" to pagerNumber.toString(), "pageSize" to 10.toString())
            getVoteHistory(map).observe(this@MyVoteHistoryActivity, Observer {
                if (it.status == 200) {
                    val datas: List<VoteHistoryBean.DatasBean>? =
                        it.data.datas
                    if (pagerNumber == 1) {
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
                        adapter!!.setNewData(datas)
                    } else {
                        if (datas?.size!! < 10) {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            pagerNumber++
                            smartRefreshLayout.finishLoadMore(true)
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        if (datas != null) {
                            adapter!!.addData(datas)
                        }
                    }
                } else {
                    ErrorCode.showErrorMsg(getActivity(), it.status)

                }
            })
        }

    }
    override fun onEmptyChildClick(view: View?) {
        getHistory()
    }

    override fun onErrorChildClick(view: View?) {
        getHistory()
    }

    override fun onCustomerChildClick(view: View?) {}
}