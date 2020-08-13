package com.kopernik.ui.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.aleyn.mvvm.base.NoViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.SpacesItemDecoration
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.setting.adapter.InviteAddressAdapter
import com.kopernik.ui.setting.entity.InviteIntegralBean
import kotlinx.android.synthetic.main.fragment_invite_address.*

class InviteAddressFragment :BaseFragment<NoViewModel,ViewDataBinding>(), OnStatusChildClickListener {

    private var type = 0
    private var pageNumber = 1
    private var adapter: InviteAddressAdapter? = null
    private var statusLayoutManager: StatusLayoutManager? = null
    private var inviteIntegralBean: InviteIntegralBean? = null

    override fun layoutId() = R.layout.fragment_invite_address

    protected fun init() {
        type = getArguments()?.getInt("type", 1)!!
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, activity!!, this)
        val layoutManager =
            LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addItemDecoration(
            SpacesItemDecoration(
                APPHelper.dp2px(
                    activity!!,
                    1.toFloat()
                )
            )
        )
        adapter = InviteAddressAdapter(type)
        recyclerView.setAdapter(adapter)
        smartRefreshLayout!!.autoRefresh()
        smartRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                if (type == NODE_INVITE_NODE) {
                    getNodeInviteNodeIntegral()
                }
                getData()
            }
        })
        //        mEventBus.post(new LocalEvent<>(LocalEvent.inviteIntegral,type));
    }


    fun getData() {
//        val url: String
//        url = if (type == ACCOUNT_INVITE_ACCOUNT) {
//            ServiceUrl.accountInviteAccount
//        } else if (type == NODE_INVITE_ACCOUNT) {
//            ServiceUrl.nodeInviteAccount
//        } else {
//            ServiceUrl.nodeInviteNode
//        }
//        val request: GetRequest<BaseBean<InviteBean>> =
//            OkGo.< BaseBean < InviteBean > > get<BaseBean<InviteBean>>(url).tag(this)
//        request.params("pageSize", 10)
//        request.params("pageNumber", pageNumber)
//        request.execute(object : JsonCallback<BaseBean<InviteBean?>?>() {
//            fun onSuccess(response: Response<BaseBean<InviteBean?>?>) {
//                val datas: List<InviteBean.DatasBean>?
//                datas = if (response.body().data == null) {
//                    null
//                } else {
//                    response.body().data.getDatas()
//                }
//                if (pageNumber == 1) {
//                    smartRefreshLayout!!.finishRefresh(300)
//                    if (datas == null || datas.size() === 0) {
//                        statusLayoutManager.showEmptyLayout()
//                        smartRefreshLayout!!.setNoMoreData(true)
//                        return
//                    }
//                    if (datas.size() === 10) {
//                        pageNumber++
//                    } else {
//                        smartRefreshLayout!!.finishRefreshWithNoMoreData()
//                    }
//                    statusLayoutManager.showSuccessLayout()
//                    adapter.setNewData(datas)
//                } else {
//                    if (datas!!.size() < 10) {
//                        smartRefreshLayout!!.finishLoadMoreWithNoMoreData()
//                    } else {
//                        pageNumber++
//                        smartRefreshLayout!!.finishLoadMore(true)
//                    }
//                    statusLayoutManager.showSuccessLayout()
//                    adapter.addData(datas)
//                }
//            }
//
//            fun onShowErrorMsg(code: Int) {
//                statusLayoutManager.showErrorLayout()
//                ErrorMsg.showErrorMsg(getActivity(), code)
//            }
//        })
    }


    fun getNodeInviteNodeIntegral() {
//            OkGo.< BaseBean < InviteIntegralBean > > get<BaseBean<InviteIntegralBean>>(ServiceUrl.nodeInviteNodeIntegral)
//                .tag(this)
//                .execute(object : JsonCallback<BaseBean<InviteIntegralBean?>?>() {
//                    fun onSuccess(response: Response<BaseBean<InviteIntegralBean?>?>) {
//                        inviteIntegralBean = response.body().data
//                        mEventBus.post(
//                            LocalEvent(
//                                LocalEvent.inviteIntegral,
//                                inviteIntegralBean,
//                                type
//                            )
//                        )
//                    }
//
//                    fun onShowErrorMsg(code: Int) {
//                        ErrorMsg.showErrorMsg(getActivity(), code)
//                    }
//                })
    }

    override fun onEmptyChildClick(view: View?) {
        pageNumber = 1
        getData()
    }

    override fun onErrorChildClick(view: View?) {
        pageNumber = 1
        getData()
    }

    override fun onCustomerChildClick(view: View?) {}

    companion object {
        const val ACCOUNT_INVITE_ACCOUNT = 1
        const val NODE_INVITE_ACCOUNT = 2
        const val NODE_INVITE_NODE = 3
        fun newInstance(type: Int): InviteAddressFragment {
            val fragment = InviteAddressFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}

