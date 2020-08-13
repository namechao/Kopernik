package com.kopernik.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.allenliu.versionchecklib.core.http.HttpHeaders
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener
import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.UYTAlertDialog2
import com.kopernik.app.dialog.UYTQuitAlertDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.DBLog
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.app.utils.fingerprint.FingerprintHelper
import com.kopernik.data.api.Api
import com.kopernik.ui.account.bean.AccountBean
import com.kopernik.ui.account.bean.AccountListBean
import com.kopernik.ui.fragment.AssetFragment
import com.kopernik.ui.fragment.EcologyFragment
import com.kopernik.ui.fragment.HomeFragment
import com.kopernik.ui.mine.fragment.MineFragment
import com.kopernik.ui.my.fragment.MyFragment
import com.kopernik.ui.setting.entity.UpdateBean2
import com.kopernik.ui.setting.viewModel.CheckAppVersionViewModel
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_main.*
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

@Suppress("DEPRECATION")
class MainActivity : NewBaseActivity<CheckAppVersionViewModel,ViewDataBinding>() , PermissionCallbacks {
    private val fragments = ArrayList<Fragment>()
    private var mIndex:Int=0
    private val homeFragment=HomeFragment.newInstance()
    private val mineFragment= MineFragment.newInstance()
    private val ecologyFragment= EcologyFragment.newInstance()
    private val assetFragment=AssetFragment.newInstance()
    private val myFragment= MyFragment.newInstance()
    var navCtl: NavigationController? =null
    private var mWebview: WebView?=null

    private var reLoginDialog: UYTQuitAlertDialog? = null
    companion object{
        const val REQUEST_CODE_QRCODE_PERMISSIONS = 1
    }

    override fun layoutId()=R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
        initView()
        initSmartRefreshLayout()
    }




    override fun initData() {
        requestCodeQRCodePermissions()
        requestUpdateInfo()
    }
    private fun initView() {
        fragments.add(homeFragment)
        fragments.add(mineFragment)
        fragments.add(ecologyFragment)
        fragments.add(assetFragment)
        fragments.add(myFragment)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragments[0])
            .commitNow()
        navCtl = page_navigation_view.material()
            .addItem(R.drawable.icon_home_selector, resources.getString(R.string.nav_title_main),resources.getColor(R.color.color_F4C41B))
            .addItem(R.drawable.icon_mine_selector, resources.getString(R.string.nav_title_mine),resources.getColor(R.color.color_F4C41B))
            .addItem(R.drawable.icon_ecology_selector,  resources.getString(R.string.nav_title_ecology),resources.getColor(R.color.color_F4C41B))
            .addItem(R.drawable.icon_asset_selector,  resources.getString(R.string.nav_title_asset),resources.getColor(R.color.color_F4C41B))
            .addItem(R.drawable.icon_my_selector,  resources.getString(R.string.nav_title_my),resources.getColor(R.color.color_F4C41B))
            .setDefaultColor(resources.getColor(R.color.color_70659F))
            .build()

        navCtl?.addTabItemSelectedListener(object : OnTabItemSelectedListener {

            override fun onSelected(index: Int, old: Int) {
                mIndex=index
                switchPage(index, old)
                resetImmersionBar(index)
            }

            override fun onRepeat(index: Int) {
            }
        })


    }


    @AfterPermissionGranted(MainActivity.REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )


        if (!EasyPermissions.hasPermissions(this,Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.carmen_denied_notice),
                MainActivity.REQUEST_CODE_QRCODE_PERMISSIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }
    private fun switchPage(index: Int, old: Int) {
        val now = fragments[index]
        supportFragmentManager.beginTransaction().apply {
            if (!now.isAdded) {
                add(R.id.container, now)
            }
            show(now)
            hide(fragments[old])
            commit()
        }
    }



    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onEvent(event: LocalEvent<Any>) {
        super.onEvent(event)
      if (event.status_type.equals(LocalEvent.restartApp)) {
            val intent = intent
            finish()
            startActivity(intent)
        } else if (event.status_type.equals(LocalEvent.switchFragment)) {
            val code = event.data as Int
            navCtl?.setSelect(code)
            mEventBus.post(LocalEvent(LocalEvent.switchNodeFragment, 0))
        }
        else if (event.status_type.equals(LocalEvent.reLogin)) {
            //删除指纹解锁
            if (UserConfig.singleton?.isUseFingerprint!!) {
                FingerprintHelper.getInstance().init(getActivity())
                FingerprintHelper.getInstance().closeAuthenticate()
                UserConfig.singleton?.isUseFingerprint=false
            }
            if (reLoginDialog == null) {
                val allAccountListStr: String? = UserConfig.singleton?.allAccount
                var accountListBean: AccountListBean? = null
                if (allAccountListStr != null && !allAccountListStr.isEmpty()) {
                    accountListBean = Gson().fromJson<AccountListBean>(
                        allAccountListStr,
                        AccountListBean::class.java
                    )
                    var delAccountBean: AccountBean? = null
                    var delBehaviorAccountBean: AccountBean? = null
                    for (bean in accountListBean.accounts!!) {
                        if (bean.loginAcountHash
                                .equals(UserConfig.singleton!!.accountBean?.loginAcountHash!!)
                        ) {
                            delAccountBean = bean
                            break
                        }
                    }
                    for (bean in accountListBean.behaviorAccounts!!) {
                        if (bean.loginAcountHash
                                .equals(UserConfig.singleton!!.accountBean?.loginAcountHash)
                        ) {
                            delBehaviorAccountBean = bean
                            break
                        }
                    }
                    accountListBean.accounts?.remove(delAccountBean)
                    accountListBean.behaviorAccounts?.remove(delBehaviorAccountBean)
                    UserConfig.singleton?.allAccount=Gson().toJson(accountListBean)
                }
                reLoginDialog = UYTQuitAlertDialog(this)
                    .setCancelable(false)
                    .setTitle(getActivity()!!.getString(R.string.account_abnormal))
                    .setMsg(
                        java.lang.String.format(
                            getActivity()!!.getString(R.string.account_abnormal_hint),
                            UserConfig.singleton?.accountBean?.loginlabel
                        )
                    )
                    .setButton1(
                        getActivity()!!.getString(R.string.import_or_created_account),
                        View.OnClickListener {
                            val intent = intent
                            finish()
                            startActivity(intent)
                            LaunchConfig.startChooseAccountAc(this)
                        }
                        )
                if (accountListBean != null && accountListBean.accounts?.size!! > 0) {
                    reLoginDialog
                        ?.setButton2(getActivity()!!.getString(R.string.switch_account),
                        View.OnClickListener {
                            val intent = intent
                            finish()
                            startActivity(intent)
                            LaunchConfig.startSwitchAccountAc(getActivity()!!)
                        }
                            )
                        ?.setSkip(getActivity()!!.getString(R.string.user_jump),
                        View.OnClickListener {
                            val intent = intent
                            finish()
                            startActivity(intent)
                        })
                }
                UserConfig.singleton?.accountString="skip"
                UserConfig.singleton?.clear()
                mEventBus.post(LocalEvent<Any>(LocalEvent.reloadAsset))
                reLoginDialog?.show()
            } else {
                if (!reLoginDialog?.isShowing!!) reLoginDialog?.show()
            }
        }
    }


    private fun resetImmersionBar(position: Int) {
        when (position) {
            4->{
                ImmersionBar.with(this).reset().init()
            }
            3 -> {
                ImmersionBar.with(this)
                    .statusBarColor(R.color.asset_fg_status)
                    .navigationBarColor(R.color.white)
                    .init()
            }
            2 -> {
                ImmersionBar.with(this)
                    .statusBarColor(R.color.white)
                    .navigationBarColor(R.color.white)
                    .statusBarDarkFont(false)
                    .init()
            }
            else -> {
                ImmersionBar.with(this)
                    .statusBarColor(R.color.glob_status)
                    .navigationBarColor(R.color.white)
                    .statusBarDarkFont(false)
                    .init()
            }
        }
//        } else {
//            ImmersionBar.with(this)
//                .statusBarColor(R.color.white_status)
//                .navigationBarColor(R.color.black)
//                .statusBarDarkFont(true)
//                .init()
//        }
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //需要设置这个flag contentView才能延伸到状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
            window.setStatusBarColor(Color.TRANSPARENT)
        } else {
            //让contentView延伸到状态栏并且设置状态栏颜色透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
    private fun initSmartRefreshLayout() {
        //注意：上次更新时间的英文格式需要加单引号如： 'Last update' M-d HH:mm
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.me_header_pulling)
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.me_header_refreshing)
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.me_header_loading)
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.me_header_release)
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.me_header_finish)
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.me_header_failed)
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.me_header_update)
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.me_header_update)
        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.me_footer_pulling)
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.me_footer_release)
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.me_footer_loading)
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.me_footer_refreshing)
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.me_footer_finish)
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.me_footer_failed)
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.me_footer_nothing)
    }
    private fun forciblyUpdate() {
        val httpHeaders = HttpHeaders()
        httpHeaders.put("apptype", "android")
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
            .setForceUpdateListener(object : ForceUpdateListener {
               override fun onShouldForceUpdate() {
                    ToastUtils.showShort(getActivity(), getString(R.string.must_update))
                }
            })
            .setShowNotification(true)
            .setCustomVersionDialogListener(createUpdateDialog(2))
            .setShowDownloadingDialog(false)
            .setForceRedownload(false)
            .executeMission(this)
    }

    private fun showUpdateDialog() {
        val httpHeaders = HttpHeaders()
        httpHeaders.put("apptype", "android")
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
            .executeMission(this)
    }

    private fun createUpdateDialog(type: Int): CustomVersionDialogListener {
        return CustomVersionDialogListener { context, versionBundle ->
            val baseDialog = Dialog(context, R.style.UpdateDialog)
            baseDialog.setContentView(R.layout.dialog_update)
            baseDialog.setCanceledOnTouchOutside(false)
            val textView =
                baseDialog.findViewById<TextView>(R.id.tv_msg)
            val version =
                baseDialog.findViewById<TextView>(R.id.tv_version)
            val content: String = versionBundle.content.replace("\\n", " \n")
            textView.text = content
            version.setText(versionBundle.getTitle())
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
            baseDialog
        }
    }

    // TODO: 2019/9/8 改为 4
    private fun initAccountListForVersionCode3() {
        if (UserConfig.singleton?.getAccount() != null &&
            UserConfig.singleton?.allAccount==null && BuildConfig.VERSION_CODE < 5
        ) {
            val gson = Gson()
            val accountListBean = AccountListBean()
            val accounts: MutableList<AccountBean> =
                java.util.ArrayList()
            accounts.add( UserConfig.singleton?.getAccount()!!)
            accountListBean.accounts=accounts
            accountListBean.behaviorAccounts=accounts
            UserConfig.singleton?.allAccount=gson.toJson(accountListBean)
        }
    }

    private fun requestUpdateInfo() {
        viewModel.checkAppVersion().observe(this, Observer {
            if (it.data != null) {
                if (it.data.deploy
                        ?.versionCode!! > BuildConfig.VERSION_CODE
                ) {
                    if (it.data.deploy?.type == 1) {
                        //提示升级
                        showUpdateDialog()
                    } else if (it.data.deploy?.type == 2) {
                        //强制升级
                        forciblyUpdate()
                    }
                } else if (it.data.notice != null) {
                    if (UserConfig.singleton
                            ?.noticeId !== it.data.notice?.id
                    ) {
                        UserConfig.singleton
                            ?.noticeId=it.data.notice?.id!!
                        UYTAlertDialog2(this)
                            .setCancelable(false)
                            .setTitle(this.getString(R.string.notice))
                            .setMsg(""+it.data.notice?.content)
                            .setButton(getActivity()!!.getString(R.string.confirm), null)
                            .show()
                    }
                }
            }


        })

    }
     //对低版本手机进行账户处理
    override fun onResume() {
        super.onResume()
        initAccountListForVersionCode3()
        KeyboardUtils.mustHideSoftKeyboard(this)
    }
}