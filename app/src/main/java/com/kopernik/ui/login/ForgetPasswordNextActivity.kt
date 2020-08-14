package com.kopernik.ui.login

import android.os.Bundle

import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity


class ForgetPasswordNextActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_forget_password_next
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.login_change_password))

    }

    override fun initData() {
    }

}