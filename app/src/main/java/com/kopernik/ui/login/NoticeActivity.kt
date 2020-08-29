package com.kopernik.ui.login

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseActivity
import com.aleyn.mvvm.base.NoViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.baseweb.BaseWebViewActivity
import com.kopernik.data.api.Api
import kotlinx.android.synthetic.main.activity_user_protocol.*


class NoticeActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
  companion object{
      var MNEMONIC_EXTRA = "mnemonicStr"
  }

     private var id=""
    override fun layoutId()=R.layout.activity_user_protocol

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.user_notice))
         intent.getStringExtra("id")?.let {
             id=it
         }
        var webSetting= webview.settings
        webSetting.javaScriptEnabled=true
        webSetting.domStorageEnabled = true;
        webview.webChromeClient =WebChromeClient()
        webview?.setBackgroundColor(Color.TRANSPARENT)
        webview.webViewClient = WebViewClient()
        webview.loadUrl(Api.UserNoticeApi+id)
    }

    override fun initData() {

    }


}