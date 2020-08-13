package com.kopernik.ui.Ecology

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.Ecology.adapter.ReferendumAdapter
import com.kopernik.ui.Ecology.entity.ReferendumListBean
import com.kopernik.ui.Ecology.viewModel.ReferendumListViewModel
import kotlinx.android.synthetic.main.activity_referendum_list.*

class ReferendumListActivity : NewBaseActivity<ReferendumListViewModel,ViewDataBinding>(),
    OnStatusChildClickListener {
    private var statusLayoutManager: StatusLayoutManager? = null
    private var adapter: ReferendumAdapter? = null
    private var pagerNumber = 1

    override fun layoutId()=R.layout.activity_referendum_list

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.comm_referendum))
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        adapter = ReferendumAdapter(R.layout.item_referendum)
        recyclerView.adapter = adapter
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getList()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                getList()
            }
        })
        adapter?.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val bean: ReferendumListBean.DatasBean =
                    adapter.data[position] as ReferendumListBean.DatasBean
                LaunchConfig.startReferendumDetailsAc(this, bean.id)
            }
    }
    private fun getList() {
        viewModel.run {
            var map= mapOf("pageNumber" to pagerNumber.toString(),
                   "pageSize" to 10.toString())
            getReferendumList(map).observe(this@ReferendumListActivity, Observer {
                if (it.status==200){
                    val datas: List<ReferendumListBean.DatasBean>? =
                        it.data.datas
                    if (pagerNumber == 1) {
                        if (datas == null || datas.size == 0) {
                            smartRefreshLayout.finishRefresh(300)
                            statusLayoutManager!!.showEmptyLayout()
                            smartRefreshLayout.setNoMoreData(true)
                            return@Observer
                        }
                        if (datas.size > 9) {
                            smartRefreshLayout.finishRefresh(300)
                            pagerNumber++
                        } else {
                            smartRefreshLayout.finishRefreshWithNoMoreData()
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        adapter?.setNewData(datas)
                    } else {
                        if (datas != null) {
                            if (datas.size < 10) {
                                smartRefreshLayout.finishLoadMoreWithNoMoreData()
                            } else {
                                pagerNumber++
                                smartRefreshLayout.finishLoadMore(true)
                            }
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        if (datas != null) {
                            adapter?.addData(datas)
                        }
                    }
                }else{
                    statusLayoutManager?.showErrorLayout()
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }
            })
        }
    }

    override fun onEmptyChildClick(view: View?) {
        getList()
    }

    override fun onErrorChildClick(view: View?) {
        pagerNumber = 1
        getList()
    }

    override fun onCustomerChildClick(view: View?) {}
    override fun initData() {

    }
}