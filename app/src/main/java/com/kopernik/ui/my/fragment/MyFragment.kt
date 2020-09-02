package com.kopernik.ui.my.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.FileProvider
import androidx.databinding.ViewDataBinding
import com.allenliu.versionchecklib.core.http.HttpHeaders
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener
import com.google.gson.Gson
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ExitAlertDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.DBLog
import com.kopernik.app.utils.ToastUtils
import com.kopernik.app.widget.NumberProgressBar
import com.kopernik.data.api.Api
import com.kopernik.ui.login.LoginActivity
import com.kopernik.ui.my.ViewModel.MyViewModel
import com.kopernik.ui.my.bean.VersionEntity
import com.kopernik.ui.setting.entity.UpdateBean2
import com.tencent.bugly.Bugly.applicationContext
import kotlinx.android.synthetic.main.fragment_my.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyFragment : NewBaseFragment<MyViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }
    private var versionEntity: VersionEntity?=null
    private var numberProgressBar: NumberProgressBar?=null
    private var mSavePath: String? = null
    private var cancelUpdate = false
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
        if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
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
                    activity?.let { LaunchConfig.startTradePasswordActivity(it, 2,1) }
                    }else{
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 2,2) }
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
            if (versionEntity != null && versionEntity?.deploy != null) {
                if (versionEntity?.deploy
                        ?.versionCode!! > BuildConfig.VERSION_CODE
                ) {
                    if (versionEntity?.deploy?.type == 1) {
                        //提示升级
                        createUpdateDialog(1)
                    } else if (versionEntity?.deploy?.type == 2) {
                        //强制升级
                        createUpdateDialog(2)
                    }
                }
            }
        }
        versionSpt.isEnabled=false
        tvVersionTip.visibility=View.GONE
        circleView.visibility=View.GONE
        requestUpdateInfo()
    }

    override fun onResume() {
        super.onResume()
        //交易密码
        if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
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




    private fun requestUpdateInfo() {
        viewModel.checkVersion().observe(this, androidx.lifecycle.Observer {
            if (it.data != null) {
                if(it.data.deploy!=null) {
                    versionEntity=it.data
                    if (it.data.deploy
                            ?.versionCode!! > BuildConfig.VERSION_CODE
                    ) {
                        tvVersionTip.visibility = View.VISIBLE
                        tvVersionTip.text = getString(R.string.check_new_version)
                        versionSpt.isEnabled = true
                        circleView.visibility = View.VISIBLE
                    }
                }
                if (it.data.user!=null){
                    it.data.user?.level?.let{ ib->
                        when(ib){
                            0-> ivVip.setImageResource(0)
                            1-> ivVip.setImageResource(R.mipmap.ic_vip1)
                            2-> ivVip.setImageResource(R.mipmap.ic_vip2)
                            3-> ivVip.setImageResource(R.mipmap.ic_vip3)
                            4-> ivVip.setImageResource(R.mipmap.ic_vip4)
                        }
                    }

                }

            }
        })
    }


    private fun createUpdateDialog(type: Int) {
        val baseDialog = Dialog(activity, R.style.UpdateDialog)
        baseDialog.setContentView(R.layout.dialog_update)
        baseDialog.setCanceledOnTouchOutside(false)
        val textView =
            baseDialog.findViewById<TextView>(R.id.tv_msg)
        var container= baseDialog.findViewById<LinearLayout>(R.id.container)
        val version =
            baseDialog.findViewById<TextView>(R.id.tv_version)
        numberProgressBar=baseDialog.findViewById(R.id.mProgress)
        numberProgressBar?.visibility=View.GONE
        val content: String ?= versionEntity?.deploy?.versionDesc?.replace("\\n", " \n")
        textView.text = content
        version.text = versionEntity?.deploy?.versionName
        var cancle= baseDialog.findViewById<View>(R.id.versionchecklib_version_dialog_cancel)
        if (type == 2) {
            cancle.visibility =
                View.GONE
        }
        cancle.setOnClickListener {
            baseDialog.dismiss()
        }
        var confrim= baseDialog.findViewById<Button>(R.id.versionchecklib_version_dialog_commit)
        confrim.setOnClickListener {
            confrim.visibility=View.GONE
            numberProgressBar?.visibility=View.VISIBLE
            if (versionEntity!=null&&versionEntity?.deploy!=null&&versionEntity?.deploy?.url!=null)
                downloadApk()
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
        val windowManager = activity
            ?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display = windowManager.defaultDisplay
        // 调整dialog背景大小
        container.layoutParams = FrameLayout.LayoutParams(
            (display.width * 0.8).toInt(),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        baseDialog.show()

    }



    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    fun installApk(apkFile: File) {
        val installApkIntent = Intent()
        installApkIntent.action = Intent.ACTION_VIEW
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT)
        installApkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(applicationContext, "com.kopernik.file_provider", apkFile), "application/vnd.android.package-archive")
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
        activity?.packageManager?.queryIntentActivities(installApkIntent, 0)?.size?.let {
            if (it> 0) {
            startActivity(installApkIntent)
        }
        }


    }

    /**
     * 下载apk文件
     */
    private fun downloadApk() {
        // 启动新线程下载软件
        downloadApkThread().start()
    }

    /**
     * 下载文件线程
     */
    private inner class downloadApkThread : Thread() {
        override fun run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    // 获得存储卡的路径
                    val sdpath = Environment.getExternalStorageDirectory().toString() + "/"
                    mSavePath = sdpath + "download"
                    val file = File(mSavePath)
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir()
                    }
                    val apkFile = File(mSavePath, "kopernik.apk")

                    //                    String cachePath = getExternalFilesDir("upgrade_apk") + File.separator + getPackageName() + ".apk";
                    //                    caacheFile = new File(cachePath);

                    val url = URL(versionEntity?.deploy?.url)
                    // 创建连接
                    val conn = url.openConnection() as HttpURLConnection
                    conn.connect()
                    // 获取文件大小
                    val length = conn.contentLength
                    // 创建输入流
                    val `is` = conn.inputStream
                    val fos = FileOutputStream(apkFile)
                    var count = 0
                    // 缓存
                    val buf = ByteArray(1024)
                    // 写入到文件中
                    do {
                        val numread = `is`.read(buf)
                        count += numread
                        // 计算进度条位置
                        val progress = (count.toFloat() / length * 100).toInt()
                        // 更新进度
                       activity?.runOnUiThread {
                            // 进度条更新进度
                            numberProgressBar?.let { it. progress = progress}
                        }

                        if (numread <= 0) {
                            // 下载完成
                            installApk(File(mSavePath, "kopernik.apk"))
                            break
                        }
                        // 写入文件
                        fos.write(buf, 0, numread)
                    } while (!cancelUpdate)// 点击取消就停止下载.
                    fos.close()
                    `is`.close()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


}