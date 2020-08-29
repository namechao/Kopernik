package com.kopernik.ui.my

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.QRCode.QRCodeEncoderModel
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import com.kopernik.data.api.Api
import com.kopernik.ui.my.ViewModel.InviteFriendsViewModel
import com.kopernik.ui.my.adapter.InviteFriendsAdapter
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.app.ScreenUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_invite_friends.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class InviteFriendsActivity : NewFullScreenBaseActivity<InviteFriendsViewModel, ViewDataBinding>() {
    private var disposable: Disposable? = null
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
    private val mHandler: Handler = Handler()
    private fun showDialog() {
        var dialog = AlertDialog.Builder(this).create()
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_poster_layout, null)
        var saveTo = view.findViewById<LinearLayout>(R.id.llSavePoster)
        var qrcodeIv=view.findViewById<QMUIRadiusImageView>(R.id.qrcodeIv)
        var clPoster=view.findViewById<ConstraintLayout>(R.id.clPoster)

        qrcodeIv.cornerRadius=50
       var  invitationCode: String? = UserConfig.singleton?.accountBean?.invitationCode ?: return
        disposable = QRCodeEncoderModel.EncodeQRCode(Api.InviteFriendsCodeApi+invitationCode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmap -> qrcodeIv.setImageBitmap(bitmap) }
        saveTo.setOnClickListener {
            clPoster.isDrawingCacheEnabled=true
            clPoster.buildDrawingCache()
            mHandler.postDelayed(Runnable {
                // 要在运行在子线程中
                val bmp: Bitmap = clPoster.getDrawingCache() // 获取图片
                savePicture(bmp, "poster.jpg") // 保存图片
                clPoster.destroyDrawingCache() // 保存过后释放资源
                dialog.dismiss()
            }, 100)
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

    fun savePicture(bm: Bitmap?, fileName: String?) {
        Log.i("xing", "savePicture: ------------------------")
        if (null == bm) {
            Log.i("xing", "savePicture: ------------------图片为空------")
            return
        }
        val foder =
            File(Environment.getExternalStorageDirectory().absolutePath + "/Kopernik")
        if (!foder.exists()) {
            foder.mkdirs()
        }
        val myCaptureFile = File(foder, fileName)
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile()
            }
            val bos = BufferedOutputStream(FileOutputStream(myCaptureFile))
            //压缩保存到本地
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        ToastUtils.showShort(this,getString(R.string.save_to_picture_success))
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
    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) disposable!!.dispose()
    }
}