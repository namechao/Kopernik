package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.bean.Mnemonic
import kotlinx.android.synthetic.main.activity_import_mnemonic.*
import java.lang.Exception
import java.util.*

class ImportMnemonicActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
    private var webview: WebView? = null
    private val inputs: MutableList<String> = ArrayList()
    override fun layoutId() = R.layout.activity_import_mnemonic

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.user_import_title))
        initJs()
        nextBtn.setOnClickListener {
            var mnemonicString=mnemonicEt.text.toString().trim()
            webview?.evaluateJavascript("getMnemonicInfo('$mnemonicString')") {
                if (!it.equals("null")) {
                    try {
                        var datas = it.replace("\\", "").replaceFirst("\"", "")
                        var gson = Gson()
                        var data = gson.fromJson(
                            datas.subSequence(0, datas.length - 1).toString(),
                            Mnemonic::class.java
                        )
                        if (data != null) {
                            LaunchConfig.startImportMnemonicConfirmAc(this, data)
                        }
                    } catch (e: Exception) {
                        // TODO 要加别国语言
                        ToastUtils.showShort(this, "无效助记词，请重新输入")
                        e.printStackTrace()
                    }
                }
            }

        }
    }


private fun initJs() {
    if (webview == null) webview = WebView(this)
    webview?.let {
        it.settings.javaScriptEnabled = true
        it.settings.allowFileAccess = true
        it.settings.domStorageEnabled = true
        it.webViewClient = WebViewClient()
        it.webChromeClient = WebChromeClient()
        it.loadUrl("file:///android_asset/build/index.html")
    }
}

override fun initData() {
    mnemonicEt.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            inputs.clear()
            val mnemonicStr = s.toString().trim()
            if (mnemonicStr.isNotEmpty()) {
                val temp = mnemonicStr.split(" ")
                inputs?.addAll(temp)
            }
            resetOkBtn()
        }
    })
}

//控制下一步按钮是否点亮
private fun resetOkBtn() {
    nextBtn.isEnabled = inputs.size == 12
}

override fun onDestroy() {
    super.onDestroy()
    webview?.destroy()
    webview == null
}
}