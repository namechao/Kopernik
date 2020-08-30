package com.kopernik.ui.my.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.allenliu.versionchecklib.core.http.HttpHeaders
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener
import com.google.gson.Gson
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ExitAlertDialog
import com.kopernik.app.dialog.UYTAlertDialog2
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.DBLog
import com.kopernik.ui.Ecology.fragment.NodeListFragment
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.viewModel.NodeViewModel
import com.kopernik.ui.MainActivity
import com.kopernik.ui.login.LoginActivity
import com.kopernik.ui.my.ViewModel.MyViewModel
import com.kopernik.ui.setting.entity.UpdateBean2
import kotlinx.android.synthetic.main.fragment_my.*
import java.util.*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyFragment : NewBaseFragment<MyViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

    override fun layoutId() = R.layout.fragment_my
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        UserConfig.singleton?.accountBean?.phone?.let {
            if (it.length>5){
                tvPhoneNumber.text="${it.subSequence(0,3)}****${it.subSequence(it.length-4,it.length)}"
            }

        }
        UserConfig.singleton?.accountBean?.email?.let {
            tvPhoneNumber.text=it
        }
        UserConfig.singleton?.accountBean?.uid?.let {
            tvId.text="ID："+it
        }
        UserConfig.singleton?.accountBean?.level?.let {
         when(it){
             0-> ivVip.setImageResource(0)
             1-> ivVip.setImageResource(R.mipmap.ic_vip1)
             2-> ivVip.setImageResource(R.mipmap.ic_vip2)
             3-> ivVip.setImageResource(R.mipmap.ic_vip3)
             4-> ivVip.setImageResource(R.mipmap.ic_vip4)
         }
        }
        //是否实名
        if (UserConfig.singleton?.accountBean?.name.isNullOrEmpty()){
            realNameAuth.setRightString(getString(R.string.no_verify))
        }else{
            realNameAuth.setRightString(getString(R.string.had_verified))
        }
        //交易密码
        if (UserConfig.singleton?.password.isNullOrEmpty()){
            tradePsw.setRightString(getString(R.string.no_setting))
        }else{
            tradePsw.setRightString(getString(R.string.had_settting))
        }
      //设置
        setting.setOnClickListener {
            activity?.let {LaunchConfig.startSettingActivityAc(it) }
        }
        //实名认证
        realNameAuth.setOnClickListener {
            if (UserConfig.singleton?.accountBean?.name.isNullOrEmpty())
            activity?.let { LaunchConfig.startRealNameAuthenticationActivity(it) }
        }
       //修改登录密码
        changeLoginPsw.setOnClickListener {

            //已登录
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    activity?.let { LaunchConfig.startForgetPasswordActivity(it,1,2) }
                }else{
                    activity?.let { LaunchConfig.startForgetPasswordActivity(it,2,2) }
                }
            }


        }
        //修改交易密码
        tradePsw.setOnClickListener {
            //已登录
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    if (UserConfig.singleton?.accountBean?.salePwd.isNullOrEmpty()){
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,1) }
                    }else{
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,2) }
                    }

                }else{
                    if (UserConfig.singleton?.accountBean?.salePwd.isNullOrEmpty()){
                    activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,1) }
                    }else{
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,2) }
                    }
                }
            }

        }

        //联系我们 点击复制邮箱
        contactSpt.setOnClickListener {
            activity?.let { it1 -> APPHelper.copy(it1,"kopernik.net@gmail.com") }
        }
        //google验证
        googleAuthen.setOnClickListener {
            activity?.let { LaunchConfig.startGoogleVerifyActivity(it) }
        }
         //邀请朋友
          inviteFriends.setOnClickListener {
            activity?.let { LaunchConfig.startInviteFriendsActivity(it) }
        }
        exitLogin.setOnClickListener {
            ExitAlertDialog(activity!!)
                .setCancelable(false)
                .setPositiveButton(View.OnClickListener {
                    UserConfig.singleton?.accountString=null
                    UserConfig.singleton?.tradePassword=null
                    val intent =
                        Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    activity?.finish()
                })
                .show()

        }
        //关于我们
        aboutUs.setOnClickListener {
            activity?.let { LaunchConfig.startAboutUsActivityAc(it) }
        }
        activity?.let {
            tvVersion.text=APPHelper.getVerName(it) }
        versionSpt.setOnClickListener {
            showUpdateDialog()
        }
        versionSpt.isEnabled=false
        tvVersionTip.visibility=View.GONE
        circleView.visibility=View.GONE
        requestUpdateInfo()
    }

    override fun onResume() {
        super.onResume()
        //交易密码
        if (UserConfig.singleton?.password.isNullOrEmpty()){
            tradePsw.setRightString(getString(R.string.no_setting))
        }else{
            tradePsw.setRightString(getString(R.string.had_settting))
        }
    }

    override fun onEvent(event: LocalEvent<Any>) {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            requestUpdateInfo()
        }
    }




    private fun showUpdateDialog() {
        val httpHeaders = HttpHeaders()
        httpHeaders.put("apptype", "android")
        httpHeaders.put("token", UserConfig.singleton?.accountBean?.token)
        AllenVersionChecker
            .getInstance()
            .requestVersion()
            .setHttpHeaders(httpHeaders)
            .setRequestUrl(Api.checkUpdate)
            .request(object : RequestVersionListener {
                @Nullable
                override  fun onRequestVersionSuccess(result: String?): UIData? {
                    val updateBean: UpdateBean2 =
                        Gson().fromJson<UpdateBean2>(result, UpdateBean2::class.java)
                    val deployBean: UpdateBean2.DataBean.DeployBean? =
                        updateBean?.data?.deploy
                    if (updateBean.status!=200) return null
                    return if (deployBean?.versionCode!! > BuildConfig.VERSION_CODE) {
                        UIData
                            .create()
                            .setDownloadUrl(deployBean.url)
                            .setTitle(deployBean.versionName)
                            .setContent(deployBean.versionDesc)
                    } else null
                }

                override  fun onRequestVersionFailure(message: String?) {
                    DBLog.error(message!!)
                }
            })
            .setShowNotification(true)
            .setCustomVersionDialogListener(createUpdateDialog(1))
            .setShowDownloadingDialog(false)
            .setForceRedownload(true)
            .executeMission(activity!!)
    }
    private fun forciblyUpdate() {
        val httpHeaders = HttpHeaders()
        httpHeaders.put("apptype", "android")
        httpHeaders.put("token", UserConfig.singleton?.accountBean?.token)
        AllenVersionChecker
            .getInstance()
            .requestVersion()
            .setHttpHeaders(httpHeaders)
            .setRequestUrl(Api.checkUpdate)
            .request(object : RequestVersionListener {
                @Nullable
                override fun onRequestVersionSuccess(result: String?): UIData? {
                    val updateBean: UpdateBean2 =
                        Gson().fromJson<UpdateBean2>(result, UpdateBean2::class.java)
                    val deployBean: UpdateBean2.DataBean.DeployBean? =
                        updateBean?.data?.deploy
                    return if (deployBean?.versionCode!! > BuildConfig.VERSION_CODE) {
                        UIData
                            .create()
                            .setDownloadUrl(deployBean.url)
                            .setTitle(deployBean.versionName)
                            .setContent(deployBean.versionDesc)
                    } else null
                }

                override fun onRequestVersionFailure(message: String?) {
                    DBLog.error(message!!)
                }
            })
            .setForceUpdateListener { ToastUtils.showShort(activity, getString(R.string.must_update)) }
            .setShowNotification(true)
            .setCustomVersionDialogListener(createUpdateDialog(2))
            .setShowDownloadingDialog(false)
            .setForceRedownload(false)
            .executeMission(activity!!)
    }
    private fun createUpdateDialog(type: Int): CustomVersionDialogListener {
        return CustomVersionDialogListener { context, versionBundle ->
            val baseDialog = Dialog(context, R.style.UpdateDialog)
            baseDialog.setContentView(R.layout.dialog_update)
            baseDialog.setCanceledOnTouchOutside(false)
            val textView =
                baseDialog.findViewById<TextView>(R.id.tv_msg)
            var container= baseDialog.findViewById<LinearLayout>(R.id.container)
            val version =
                baseDialog.findViewById<TextView>(R.id.tv_version)
            val content: String = versionBundle.content.replace("\\n", " \n")
            textView.text = content
            version.text = versionBundle.title
            if (type == 2) {
                baseDialog.findViewById<View>(R.id.versionchecklib_version_dialog_cancel).visibility =
                    View.GONE
            }
            baseDialog.setOnKeyListener( object :DialogInterface.OnKeyListener{
                override fun onKey(
                    dialog: DialogInterface?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (type == 2 && keyCode == KeyEvent.KEYCODE_BACK) return true
                    return false
                }

            })
            val windowManager = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
             var display = windowManager.defaultDisplay
            // 调整dialog背景大小
            container.layoutParams = FrameLayout.LayoutParams(
                (display.width * 0.8).toInt(),
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            baseDialog
        }
    }


    private fun requestUpdateInfo() {
        viewModel.checkVersion().observe(this, androidx.lifecycle.Observer {
            if (it.data != null) {
                if (it.data.deploy
                        ?.versionCode!! > BuildConfig.VERSION_CODE
                ) {
                    tvVersionTip.visibility=View.VISIBLE
                    tvVersionTip.text=getString(R.string.check_new_version)
                    versionSpt.isEnabled=true
                    circleView.visibility=View.VISIBLE
//                    if (it.data.deploy?.type == 1) {
//                        //提示升级
//                        showUpdateDialog()
//                    } else if (it.data.deploy?.type == 2) {
//                        //强制升级
//                        forciblyUpdate()
//                    }
                }
//                else if (it.data.notice != null) {
//                    if (UserConfig.singleton
//                            ?.noticeId !== it.data.notice?.id
//                    ) {
//                        UserConfig.singleton
//                            ?.noticeId=it.data.notice?.id!!
//                        UYTAlertDialog2(activity!!)
//                            .setCancelable(false)
//                            .setTitle(this.getString(R.string.notice))
//                            .setMsg(""+it.data.notice?.content)
//                            .setButton(getString(R.string.confirm), null)
//                            .show()
//                    }
//                }
            }
        })
    }
}