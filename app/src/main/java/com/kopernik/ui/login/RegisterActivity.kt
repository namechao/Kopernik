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
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception
import java.util.*

class RegisterActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.activity_register

    override fun initView(savedInstanceState: Bundle?) {
        setTitleAndRightonClick(getString(R.string.re_quick_register),getString(R.string.re_login_title),object :OnRightClickItem{
            override fun onClick() {
                finish()
            }
        })
        confirmBtn.setOnClickListener {
           LaunchConfig.startRegisterSetUpPasswordActivity(this)
        }
    }



override fun initData() {

}

//控制下一步按钮是否点亮
private fun resetOkBtn() {
//    nextBtn.isEnabled = inputs.size == 12
}


}