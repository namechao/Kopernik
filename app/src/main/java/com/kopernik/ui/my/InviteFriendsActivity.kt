package com.kopernik.ui.my

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.Ecology.entity.TimeLineItemBean
import com.kopernik.ui.my.ViewModel.InviteFriendsViewModel
import com.kopernik.ui.my.adapter.InviteFriendsAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.app.ScreenUtils
import kotlinx.android.synthetic.main.activity_invite_friends.*
import kotlinx.android.synthetic.main.activity_invite_friends.recyclerView
import kotlinx.android.synthetic.main.activity_invite_friends.smartRefreshLayout
import kotlinx.android.synthetic.main.activity_redeem_time_line.*
import java.util.ArrayList

class InviteFriendsActivity : NewFullScreenBaseActivity<InviteFriendsViewModel, ViewDataBinding>() {

    var pagerNumber =1
    override fun layoutId()=R.layout.activity_invite_friends
    var adapter= InviteFriendsAdapter(arrayListOf())
    override fun initView(savedInstanceState: Bundle?) {
        llGeneratePoster.setOnClickListener {

        }
        //头部信息
        var view=LayoutInflater.from(this).inflate(R.layout.header_invite_layout,null)
        adapter.addHeaderView(view)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
        view.findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() }
        var inviteCode=UserConfig.singleton?.accountBean?.invitationCode
        view.findViewById<TextView>(R.id.myInviteCode).text=getString(R.string.my_invite_code)+inviteCode
        view.findViewById<TextView>(R.id.inviteCopy).setOnClickListener {
            APPHelper.copy(this,inviteCode)
        }
        llGeneratePoster.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        var dialog = AlertDialog.Builder(this).create()
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_poster_layout, null)
        var saveTo = view.findViewById<LinearLayout>(R.id.llSavePoster)
        saveTo.setOnClickListener {
            dialog.dismiss()
            ToastUtils.showShort(this,getString(R.string.save_to_picture_success))
        }
        dialog.setView(view)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(true)
        dialog.show()
        dialog.window?.setLayout(
            ScreenUtils.getScreenWidth() * 4 / 5,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }
    override fun initData() {
        smartRefreshLayout.setOnRefreshLoadMoreListener(object:OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {

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
          var map= mapOf("pageNumber" to "1" ,"pageSize" to "10")
          inviteFriends(map).observe(this@InviteFriendsActivity, Observer {
              if (it.status == 200) {

                  if (pagerNumber == 1) {
                      if (it.data == null || it.data.datas.isEmpty()) {
                          smartRefreshLayout.finishRefresh(300)
                          smartRefreshLayout.setNoMoreData(true)
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

}