package com.kopernik.ui.Ecology

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.kopernik.R
import com.kopernik.app.baseweb.BaseWebViewActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseWebViewActivity<NoViewModel,ViewDataBinding>() {
    var webUrl: String? = null
    override fun getWebView(): BridgeWebView =activtyWebview
    override fun layoutId()=R.layout.activity_webview

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(intent.getStringExtra("title"))
        webUrl = intent.getStringExtra("url")
    }

    override fun initData() {

    }

    override fun getUrl(): String? {
        return webUrl
    }

}