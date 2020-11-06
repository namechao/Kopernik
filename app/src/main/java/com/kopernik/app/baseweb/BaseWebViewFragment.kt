package com.kopernik.app.baseweb

import android.annotation.SuppressLint

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface

import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseFragment
import com.aleyn.mvvm.base.BaseViewModel
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode



abstract class BaseWebViewFragment<T : BaseViewModel, U : ViewDataBinding>() : BaseFragment<T, U>() {

    protected  var mBridgeWebView: WebView?=null

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        mBridgeWebView?.webViewClient=MyWebViewClient()
        mBridgeWebView?.webChromeClient= WebChromeClient()
        mBridgeWebView?.loadUrl(getUrl())
        mBridgeWebView?.setBackgroundColor(Color.TRANSPARENT)
        mBridgeWebView?.addJavascriptInterface(MyJavascriptInterface(),"web2app")

    }

    inner  class MyJavascriptInterface{

        @JavascriptInterface
        fun gotoLogin() {
            LaunchConfig.startLoginActivity(activity!!)
        }



    }

    public override fun onPause() {
        mBridgeWebView?.onPause()
        super.onPause()
    }

    public override fun onResume() {
        mBridgeWebView?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBridgeWebView?.destroy()

        mBridgeWebView=null
    }

    @Nullable
    protected open fun getUrl(): String? {
        return null
    }

    inner class  MyWebViewClient() : com.tencent.smtt.sdk.WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            writeData()
        }
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
            mBridgeWebView?.evaluateJavascript( 
                    "window.localStorage.setItem('$key','$`token`');",
                    null
                )
            mBridgeWebView?.evaluateJavascript("window.localStorage.setItem('skin','$skinName');", null)
        } else {
            mBridgeWebView?.loadUrl("javascript:(function({window.localStorage.setItem('$key','$token')})()")
            mBridgeWebView?.loadUrl("javascript:(function({window.localStorage.setItem('skin','$skinName')})()")
        }
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
    @Nullable
    protected open abstract fun getWebView():WebView?
}