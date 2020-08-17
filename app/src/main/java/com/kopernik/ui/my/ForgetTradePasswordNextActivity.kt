package com.kopernik.ui.my

import android.os.Bundle

import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity


class ForgetTradePasswordNextActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_trade_password_next
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_set_trade_psw))

    }

    override fun initData() {
    }

}