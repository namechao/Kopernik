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
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.dialog.UYTAlertDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.SpacesItemDecoration
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.common.SignatureType
import com.kopernik.ui.Ecology.adapter.MyVoteAdapter
import com.kopernik.ui.Ecology.entity.MyVoteBean
import kotlinx.android.synthetic.main.activity_my_vote.*
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.viewModel.MyVoteViewModel

class MyVoteActivity : NewBaseActivity<MyVoteViewModel,ViewDataBinding>(), OnStatusChildClickListener {

    private var adapter: MyVoteAdapter? = null
    private var statusLayoutManager: StatusLayoutManager? = null
    private var pagerNumber = 1
    override fun layoutId()=R.layout.activity_my_vote

    override fun initView(savedInstanceState: Bundle?) {
        setTitleAndRight(getString(R.string.title_vote_mine), getString(R.string.history))
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        adapter = MyVoteAdapter(R.layout.item_my_vote)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpacesItemDecoration(APPHelper.dp2px(this, 10.toFloat())))
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getMyVoteList()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                getMyVoteList()
            }
        })
    }

    override fun initData() {
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            val bean: MyVoteBean.DatasBean = adapter.data[position] as MyVoteBean.DatasBean
            when (view.id) {
                R.id.node_vote_spb -> LaunchConfig.startVoteBetAc(
                    this,
                    VoteBetActivity.GTONVOTE,
                    bean.nodeHash
                )
                R.id.vote_redeem_spb -> LaunchConfig.startVoteOperateAc(
                    this,
                    VoteOperateActivity.REDEEM,
                    bean.nodeHash,
                    null,
                    bean.address
                )
                R.id.vote_turn_spb -> LaunchConfig.startVoteOperateAc(
                    this,
                    VoteOperateActivity.TURN,
                    bean.nodeHash,
                    null,
                    bean.address
                )
                R.id.vote_deduct_spb -> LaunchConfig.startVoteOperateAc(
                    this,
                    VoteOperateActivity.DEDUCT,
                    bean.nodeHash,
                    bean.vate?.unclaimed,
                    bean.address
                )
            }
        }
        adapter!!.setOnItemClickListener { adapter, view, position ->
            val bean: MyVoteBean.DatasBean = adapter.data[position] as MyVoteBean.DatasBean
            LaunchConfig.startWebViewAc(
               this,
                Api.appSearch + bean.nodeHash,
                getString(R.string.node_details)
            )
        }
        oneBtnSpb.setOnClickListener { v -> oneClickRaiseInterestCheck() }
    }
    override fun functionCall() {
        LaunchConfig.startMyVoteHistoryAc(this)
    }

    override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.reloadMyVoteList)) {
            smartRefreshLayout.autoRefresh()
        }
    }

    private fun getMyVoteList() {
        viewModel.run {
            var map= mapOf("pageNumber" to pagerNumber.toString(),"pageSize" to 10.toString())
            getvotelist(map).observe(this@MyVoteActivity, Observer {
                if (it.status==200){
                    val datas: List<MyVoteBean.DatasBean>? =
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
                        adapter!!.setNewData(datas)
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
                    statusLayoutManager!!.showErrorLayout()
                    ErrorCode.showErrorMsg(this@MyVoteActivity, it.status)
                }
            })
        }
    }

    private fun oneClickRaiseInterestCheck() {
        viewModel.run {
            checkextract().observe(this@MyVoteActivity, Observer {
                var bean=it.data
              if (it.status==200){
                  UYTAlertDialog(this@MyVoteActivity)
                        .setTitle(getString(R.string.hint))
                        .setMsg(getString(R.string.one_click_raise_interest_hint))
                        .setPositiveButton(
                            getString(R.string.confirm),
                            View.OnClickListener {
                                val dialog: SignatureDialog = SignatureDialog.newInstance(
                                    SignatureType.ONE_KEY,
                                    bean
                                )
                                dialog.show(supportFragmentManager, "oneKey")
                            })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .show()
              }  else{
                  ErrorCode.showErrorMsg(this@MyVoteActivity, it.status)
              }
            })
        }
    }

    override fun onEmptyChildClick(view: View?) {
        getMyVoteList()
    }

    override fun onErrorChildClick(view: View?) {
        pagerNumber = 1
        getMyVoteList()
    }

    override fun onCustomerChildClick(view: View?) {}
}