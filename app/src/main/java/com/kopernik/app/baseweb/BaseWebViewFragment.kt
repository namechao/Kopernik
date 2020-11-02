package com.kopernik.app.baseweb

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseFragment
import com.aleyn.mvvm.base.BaseViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.just.agentweb.*
import com.just.agentweb.DefaultWebClient.OpenOtherPageWays
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import org.json.JSONException
import org.json.JSONObject


abstract class BaseWebViewFragment<T : BaseViewModel, U : ViewDataBinding>() : BaseFragment<T, U>() {

    protected var mAgentWeb: AgentWeb? = null
    private val mAgentWebUIController: AgentWebUIControllerImplBase? = null
    private var mMiddleWareWebChrome: MiddlewareWebChromeBase? = null
    private var mMiddleWareWebClient: MiddlewareWebClientBase? = null
    private var mBridgeWebView: BridgeWebView? = null

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mAgentWeb == null) {
            buildAgentWeb()
        }
    }

    protected open fun buildAgentWeb() {
        AgentWebConfig.debug()
        if (mBridgeWebView == null) mBridgeWebView = BridgeWebView(activity)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(getAgentWebParent()!!, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
            .setWebChromeClient(getWebChromeClient())
            .setWebViewClient(getWebViewClient())
            .setWebView(getWebView())
            .setPermissionInterceptor(getPermissionInterceptor())
            .setWebLayout(getWebLayout())
            .setAgentWebUIController(getAgentWebUIController())
            .setOpenOtherPageWays(getOpenOtherAppWay())
            .useMiddlewareWebChrome(getMiddleWareWebChrome()!!)
            .useMiddlewareWebClient(getMiddleWareWebClient()!!)
            .setAgentWebWebSettings(getAgentWebSettings()) //                .setMainFrameErrorView(R.layout.layout_error, R.id.bt_status_error_click)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .createAgentWeb()
            .ready()
            .go(getUrl())
        mAgentWeb?.getWebCreator()?.webView
            ?.setBackgroundColor(Color.TRANSPARENT)
        val frameLayout = mAgentWeb?.getWebCreator()?.webParentLayout
        frameLayout?.setBackgroundColor(Color.TRANSPARENT)
        val websettings = mAgentWeb?.getAgentWebSettings()?.webSettings
        websettings?.domStorageEnabled = true // 开启 DOM storage 功能
        websettings?.allowFileAccess = false // 可以读取文件缓存
        websettings?.setAppCacheEnabled(false) //开启H5(APPCache)缓存功能
        mBridgeWebView!!.registerHandler(
            "copyFun"
        ) { data, function ->
            try {
                val jsonObject = JSONObject(data)
                APPHelper.copy(activity!!, jsonObject.getString("Data"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        mBridgeWebView!!.registerHandler(
            "gotoLogin"
        ) { data, function -> LaunchConfig.startLoginActivity(activity!!) }
        mBridgeWebView!!.registerHandler(
            "gotoReferendumListActivity"
        ) { data, function -> LaunchConfig.startReferendumListAc(activity!!) }
        mBridgeWebView!!.registerHandler(
            "gotoReferendumDetailsActivity"
        ) { data, function ->
            try {
                val jsonObject = JSONObject(data)
                val id = jsonObject.getInt("id")
                LaunchConfig.startReferendumDetailsAc(activity!!, id)
            } catch (ex: Exception) {
                ToastUtils.showShort(activity, "携带数据出错" + ex.message)
            }
        }
        mBridgeWebView!!.registerHandler(
            "gotoNodeLeaderBoardActivity"
        ) { data, function -> LaunchConfig.startNodeLeaderBoardAc(activity!!) }
    }


    protected open fun getAgentWeb(): AgentWeb? {
        return mAgentWeb
    }

    override fun onPause() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onPause()
        }
        super.onPause()
    }

    override fun onResume() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onResume()
        }
        super.onResume()
    }


    override fun onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onDestroy()
            mAgentWeb!!.clearWebCache()
            mAgentWeb = null
        }
        super.onDestroy()
    }


    protected open fun setHeadTitle(view: WebView?, title: String?) {}

    @Nullable
    protected open fun getUrl(): String? {
        return null
    }

    @Nullable
    open fun getAgentWebSettings(): IAgentWebSettings<*>? {
        return AgentWebSettingsImpl.getInstance()
    }

    @NonNull
    protected abstract fun getAgentWebParent(): ViewGroup?

    @Nullable
    protected open fun getWebChromeClient(): WebChromeClient? {
        return null
    }

    @ColorInt
    protected open fun getIndicatorColor(): Int {
        return 0
    }

    protected open fun getIndicatorHeight(): Int {
        return 0
    }

    @Nullable
    protected open fun getWebViewClient(): WebViewClient? {
        var webViewClient: WebViewClient? = null
        if (webViewClient == null) {
            webViewClient = object : WebViewClient() {
                var mBridgeWebViewClient = BridgeWebViewClient(mBridgeWebView)
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    url: String
                ): Boolean {
                    return mBridgeWebViewClient.shouldOverrideUrlLoading(view, url)
                }

                override fun onPageStarted(
                    view: WebView,
                    url: String,
                    favicon: Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    writeData()
                    mBridgeWebViewClient.onPageFinished(view, url)
                }
            }
        }
        return webViewClient
    }

    open fun writeData() {
        val key = "token"
        var token = ""
        val skinName = "glob"
        if (UserConfig.singleton!=null){
            UserConfig.singleton!!.accountBean?.token?.let {
                token=it
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAgentWeb!!.webCreator.webView
                .evaluateJavascript(
                    "window.localStorage.setItem('$key','$`token`');",
                    null
                )
            mAgentWeb!!.webCreator.webView
                .evaluateJavascript("window.localStorage.setItem('skin','$skinName');", null)
        } else {
            mAgentWeb!!.webCreator.webView
                .loadUrl("javascript:(function({window.localStorage.setItem('$key','$token')})()")
            mAgentWeb!!.webCreator.webView
                .loadUrl("javascript:(function({window.localStorage.setItem('skin','$skinName')})()")
        }
    }

    @Nullable
    protected open fun getWebView(): WebView? {
        if (mBridgeWebView == null) mBridgeWebView = BridgeWebView(activity)
        return mBridgeWebView
    }

    @Nullable
    protected open fun getWebLayout(): IWebLayout<*, *>? {
        return null
    }

    @Nullable
    protected open fun getPermissionInterceptor(): PermissionInterceptor? {
        return null
    }

    @Nullable
    open fun getAgentWebUIController(): AgentWebUIControllerImplBase? {
        return null
    }

    @Nullable
    open fun getOpenOtherAppWay(): OpenOtherPageWays? {
        return null
    }

    @NonNull
    protected open fun getMiddleWareWebChrome(): MiddlewareWebChromeBase? {
        return object : MiddlewareWebChromeBase() {}.also { mMiddleWareWebChrome = it }
    }

    @NonNull
    protected open fun getMiddleWareWebClient(): MiddlewareWebClientBase? {
        return object : MiddlewareWebClientBase() {}.also { mMiddleWareWebClient = it }
    }
    //对返回按键做处理
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        getWebView()?.let {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return back()
            }
        }
        return false
    }

    open fun back(): Boolean {
        if (mBridgeWebView != null && mBridgeWebView?.canGoBack()!!) {
            mBridgeWebView?.goBack()
            return true
        }
        return false
    }
}