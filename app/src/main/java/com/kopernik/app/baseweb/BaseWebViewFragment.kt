package com.kopernik.app.baseweb

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseFragment
import com.aleyn.mvvm.base.BaseViewModel
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig.Companion.singleton
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONException
import org.json.JSONObject


abstract class BaseWebViewFragment<T : BaseViewModel, U : ViewDataBinding>() : BaseFragment<T, U>() {

    protected  var mBridgeWebView: BridgeWebView?=null

    var mEventBus: EventBus = EventBus.getDefault()
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mBridgeWebView==null) {
            buildAgentWeb()
        }
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this)
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
        mBridgeWebView?.setBackgroundColor(Color.TRANSPARENT)
        mBridgeWebView?.registerHandler("copyFun",
            BridgeHandler { data, function ->
                try {
                    val jsonObject = JSONObject(data)
                    APPHelper.copy(activity!!, jsonObject.getString("Data"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        mBridgeWebView?.registerHandler("gotoLogin",
            BridgeHandler { data, function -> LaunchConfig.startChooseAccountAc(activity!!) })
        mBridgeWebView?.registerHandler("gotoReferendumListActivity",
            BridgeHandler { data, function -> LaunchConfig.startReferendumListAc(activity!!) })
        mBridgeWebView?.registerHandler("gotoReferendumDetailsActivity",
            BridgeHandler { data, function ->
                try {
                    val jsonObject = JSONObject(data)
                    val id = jsonObject.getInt("id")
                    LaunchConfig.startReferendumDetailsAc(activity!!, id)
                } catch (ex: java.lang.Exception) {
                    ToastUtils.showShort(activity, "携带数据出错" + ex.message)
                }
            })
        mBridgeWebView?.registerHandler("gotoNodeLeaderBoardActivity",
            BridgeHandler { data, function -> LaunchConfig.startNodeLeaderBoardAc(activity!!) })

    }

    public override fun onPause() {
        mBridgeWebView?.let { it.onPause()}
        super.onPause()
    }

    public override fun onResume() {
        mBridgeWebView?.let { it.onResume()}
        super.onResume()
    }

     override fun onDestroy() {
        super.onDestroy()
        mBridgeWebView?.let {
        it.destroy()
        }
        mBridgeWebView=null
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this)
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    open fun onEvent(event: LocalEvent<Any>) {
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

    }
    //对返回按键做处理
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        getWebView()?.let {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return back(it)
            }
        }
        return false
    }

    open fun back(mWebView:WebView): Boolean {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return false
    }
    @Nullable
    protected open abstract fun getWebView(): BridgeWebView?

}