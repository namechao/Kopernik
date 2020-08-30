package com.kopernik.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
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
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.login.bean.AccountListBean
import com.kopernik.ui.asset.fragment.AssetFragment
import com.kopernik.ui.Ecology.fragment.EcologyFragment
import com.kopernik.ui.home.fragment.HomeFragment
import com.kopernik.ui.mine.fragment.MineFragment
import com.kopernik.ui.my.fragment.MyFragment
import com.kopernik.ui.setting.entity.UpdateBean2
import com.kopernik.ui.setting.viewModel.CheckAppVersionViewModel
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my.*
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
    private val assetFragment= AssetFragment.newInstance()
    private val myFragment= MyFragment.newInstance()
    var navCtl: NavigationController? =null
    private var mWebview: WebView?=null

    private var reLoginDialog: UYTQuitAlertDialog? = null
    companion object{
        const val REQUEST_CODE_QRCODE_PERMISSIONS = 1
    }

    override fun layoutId()=R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

        initView()
        initSmartRefreshLayout()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        ImmersionBar.with(this).reset().init()
        return super.onCreateView(name, context, attrs)
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

        }
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
            .executeMission(this)
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
            .setForceUpdateListener { ToastUtils.showShort(this, getString(R.string.must_update)) }
            .setShowNotification(true)
            .setCustomVersionDialogListener(createUpdateDialog(2))
            .setShowDownloadingDialog(false)
            .setForceRedownload(false)
            .executeMission(this!!)
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
                    if (it.data.deploy?.type == 1) {
                        //提示升级
                        showUpdateDialog()
                    } else if (it.data.deploy?.type == 2) {
                        //强制升级
                        forciblyUpdate()
                    }
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
     //对低版本手机进行账户处理
    override fun onResume() {
        super.onResume()
        KeyboardUtils.mustHideSoftKeyboard(this)
    }
}