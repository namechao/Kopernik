package com.kopernik.ui.asset


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.utils.KeyboardUtils
import dev.utils.app.ScreenUtils
import kotlinx.android.synthetic.main.activity_asset_transform.*

class AssetTransformActivty : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    private var availableAmount: String = ""
    private var chainType: Int = -1
    private var chainName: String = ""
    private var webview: WebView? = null
    override fun layoutId(): Int = R.layout.activity_asset_transform

    override fun initView(savedInstanceState: Bundle?) {
        //可用余额
        availableAmount = intent.getStringExtra("availableAmount")
        //传来的类型
        chainName = intent.getStringExtra("chainName")
        chainType = intent.getIntExtra("chainType", -1)
        setTitle(resources.getString(R.string.asset_transform))
        passwordEt?.addTextChangedListener(passwordWatcher)
        passwordEt.isEnabled = false
        passwordEt.setText(availableAmount)
        okBtn.setOnClickListener {
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            showDialog()
        }
        //可用
        withdrawTip.text =
            resources.getString(R.string.tip_user_balance) + availableAmount + chainName
        initWebview()
    }

    //    初始化加载js
    private fun initWebview() {
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


    private fun showDialog() {
        var dialog = AlertDialog.Builder(this).create()
        var view = LayoutInflater.from(this).inflate(R.layout.dialoig_reminder_layout, null)
        var content = view.findViewById<TextView>(R.id.content)
        var cancel = view.findViewById<TextView>(R.id.cancel)
        var confirm = view.findViewById<TextView>(R.id.confirm)
        dialog.setView(view)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.setCancelable(false)
        cancel?.setOnClickListener {
            dialog.dismiss()
        }
        confirm?.setOnClickListener {
//            确定提取
            confirmExtracted()
        }
        content?.text =
            resources.getString(R.string.title_vote_his_num) + ":" + availableAmount + chainName
        dialog.show()
        dialog.window?.setLayout(
            ScreenUtils.getScreenWidth() * 4 / 5,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private fun confirmExtracted() {
//        UserConfig.singleton?.mnemonic?.mnemonic?.let { list ->
//            val sb = StringBuilder()
//            for (i in list.indices) {
//                sb.append(list[i]).append(" ")
//            }
//            webview?.evaluateJavascript(
//                "widthdrawlCoin($chainType,'${sb.toString().trim()}','1000000000000')"
//            ) {
//
//            }
//        }


    }

    override fun initData() {

    }

    private var passwordWatcher: TextWatcher = object : TextWatcher {
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
            okBtn!!.isEnabled = passwordEt!!.text.toString().isNotEmpty()
        }
    }



}