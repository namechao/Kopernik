package com.kopernik.ui.my

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.ui.my.ViewModel.InviteFriendsViewModel
import com.kopernik.ui.my.adapter.InviteFriendsSecondAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_invite_second_friends.*

class InviteFriendsSecondActivity : NewBaseActivity<InviteFriendsViewModel, ViewDataBinding>() {
    private var disposable: Disposable? = null
    var pagerNumber =1
    var uid=""
    override fun layoutId()=R.layout.activity_invite_second_friends
    var adapter= InviteFriendsSecondAdapter(arrayListOf())
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_invite_detailed))
        intent?.getStringExtra("uid")?.let {
            uid=it
        }
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
    override fun initData() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(object:OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
            }
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pagerNumber=1
                getList()
            }
        })
        smartRefreshLayout.autoRefresh()
        }
  private  fun getList(){
      viewModel.run {
          var map= mapOf("pageNumber" to pagerNumber.toString() ,"pageSize" to "10","uid" to uid)
          inviteFriendsSecond(map).observe(this@InviteFriendsSecondActivity, Observer {
              if (it.status == 200) {

                  if (pagerNumber == 1) {
                      if (it.data == null || it.data.datas.isEmpty()) {
                          smartRefreshLayout.finishRefreshWithNoMoreData()
                          return@Observer
                      }
                      if (it.data.datas.size == 10) {
                          smartRefreshLayout.finishRefresh(300)
                          pagerNumber++
                      } else {
                          smartRefreshLayout.finishRefreshWithNoMoreData()
                      }

                      adapter!!.setNewData(it.data.datas)
                  } else {
                      if (it.data!!.datas.size < 10) {
                          smartRefreshLayout.finishLoadMoreWithNoMoreData()
                      } else {
                          pagerNumber++
                          smartRefreshLayout.finishLoadMore(true)
                      }

                      adapter!!.addData(it.data.datas)
                  }
              } else {
                  ErrorCode.showErrorMsg(getActivity(), it.status)
              }
          })
      }
  }
    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) disposable!!.dispose()
    }
}