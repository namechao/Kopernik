package com.kopernik.app.baseweb

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseViewModel
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig.Companion.singleton
import com.kopernik.app.utils.APPHelper.copy
import com.kopernik.app.utils.ToastUtils
import org.json.JSONException
import org.json.JSONObject

abstract class BaseWebViewActivity<T:BaseViewModel,U:ViewDataBinding> : NewBaseActivity<T, U>() {
    protected  var mBridgeWebView: BridgeWebView?=null

    override fun onStart() {
        super.onStart()
        if (mBridgeWebView==null) {
            buildAgentWeb()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected open fun buildAgentWeb() {
        mBridgeWebView= getWebView()
        mBridgeWebView?.settings?.javaScriptEnabled=true
        mBridgeWebView?.settings?.allowContentAccess=true
        mBridgeWebView?.settings?.allowFileAccess=true
        mBridgeWebView?.settings?.domStorageEnabled=true
        mBridgeWebView?.let {
            mBridgeWebView?.webViewClient=MyWebViewClient(it)
        }
        mBridgeWebView?.webChromeClient= WebChromeClient()
        mBridgeWebView?.loadUrl(getUrl())
        mBridgeWebView?.registerHandler("copyFun",
            BridgeHandler { data, function ->
                try {
                    val jsonObject = JSONObject(data)
                    copy(this, jsonObject.getString("Data"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        mBridgeWebView?.registerHandler("gotoLogin",
            BridgeHandler { data, function -> LaunchConfig.startChooseAccountAc(this) })
        mBridgeWebView?.registerHandler("gotoReferendumListActivity",
            BridgeHandler { data, function -> LaunchConfig.startReferendumListAc(this) })
        mBridgeWebView?.registerHandler("gotoReferendumDetailsActivity",
            BridgeHandler { data, function ->
                try {
                    val jsonObject = JSONObject(data)
                    val id = jsonObject.getInt("id")
                    LaunchConfig.startReferendumDetailsAc(this, id)
                } catch (ex: java.lang.Exception) {
                    ToastUtils.showShort(this, "携带数据出错" + ex.message)
                }
            })
        mBridgeWebView?.registerHandler("gotoNodeLeaderBoardActivity",
            BridgeHandler { data, function -> LaunchConfig.startNodeLeaderBoardAc(this) })

    }
    public override fun onPause() {
        mBridgeWebView?.let { it.onPause()}
        super.onPause()
    }

    public override fun onResume() {
        mBridgeWebView?.let { it.onResume()}
        super.onResume()
    }

    public override fun onDestroy() {
        mBridgeWebView?.let {
            it.destroy()
        }
        mBridgeWebView=null
        super.onDestroy()
    }
    @Nullable
    protected open fun getUrl(): String? {
        return null
    }

    inner class  MyWebViewClient(webView: BridgeWebView) : BridgeWebViewClient(webView) {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            writeData()
        }
    }

    open fun writeData() {
        val key = "token"
        var local = "zh-CN"
        var skinName = "daylight"
        when (singleton!!.languageTag) {
            1 -> local = "zh-CN"
            2 -> local = "en-Us"
            3 -> local = "Korea"
        }
//        if (!StringUtils.isEmpty(SkinPreference.getInstance().getSkinName())) {
        skinName = "glob"
//        }
        var `val` = ""
        singleton?.token?.let {
            `val` =  it
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mBridgeWebView?.evaluateJavascript(
                "window.localSorage.setItem('$key','$`val`');",
                null
            )
            mBridgeWebView?.evaluateJavascript("window.localStorage.setItem('locale','$local');", null)
            mBridgeWebView?.evaluateJavascript("window.localStorage.setItem('skin','$skinName');", null)
        } else {
            mBridgeWebView?.loadUrl("javascript:(function({window.localStorage.setItem('$key','$`val`')})()")
            mBridgeWebView?.loadUrl("javascript:(function({window.localStorage.setItem('locale','$local')})()")
            mBridgeWebView?.loadUrl("javascript:(function({window.localStorage.setItem('skin','$skinName')})()")
        }
    }


    @Nullable
    protected open abstract fun getWebView(): BridgeWebView?
}