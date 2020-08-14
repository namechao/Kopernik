package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.login.bean.AccountListBean
import com.kopernik.ui.login.bean.Mnemonic
import com.kopernik.ui.login.viewmodel.CreateAccountViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : NewBaseActivity<CreateAccountViewModel, ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_forget_password

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.login_change_password))
        confirmBtn.setOnClickListener {
            LaunchConfig.startForgetPasswordNextActivity(this)
        }
    }


    override fun initData() {

        }


}