package com.kopernik.ui.account

import android.os.Bundle
import android.webkit.*

import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.account.adapter.MnemonicAdapter
import com.kopernik.ui.account.bean.Mnemonic
import kotlinx.android.synthetic.main.activity_add_new_account.*
import java.lang.Exception


class AddNewAccountActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
    private var adapter:MnemonicAdapter?=null
    private var webview: WebView?=null
    private var mnemonic:Mnemonic?=null
    override fun layoutId()=R.layout.activity_add_new_account
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.back_mnemonic))
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.alignItems = AlignItems.STRETCH
        adapter = MnemonicAdapter(listOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = flexboxLayoutManager
        //点击确定按钮
        tvConfirm.setOnClickListener {
            mnemonic?.let {
                LaunchConfig.startMnemonicConfirmAc(this, mnemonic)
            }

        }
    }
//    获取助记词等信息从js中
    private fun getMnemonicFromJS() {
    if (webview==null) webview= WebView(this)
    webview?.let {
        it.settings.javaScriptEnabled=true
        it.settings.allowFileAccess=true
        it.settings.domStorageEnabled=true
        it.webViewClient= webViewClient
        it.webChromeClient= WebChromeClient()
        it.loadUrl("file:///android_asset/build/index.html")
       }
    }
    var webViewClient=object : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            view?.evaluateJavascript("getMnemonic(1)") { value ->
                value?.let {
                    try {
                        var datas = it.replace("\\", "").replaceFirst("\"", "")
                        var gson = Gson()
                        var data = gson.fromJson(
                            datas.subSequence(0, datas.length - 1).toString(),
                            Mnemonic::class.java
                        )
                        mnemonic = data
                        data?.mnemonic
                        UserConfig.singleton?.mnemonic=data
                        adapter!!.setNewData(data.mnemonic)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }

    }

    override fun initData() {
        getMnemonicFromJS()
    }

}