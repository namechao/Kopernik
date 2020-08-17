package com.kopernik.ui.my

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.ui.login.viewmodel.CreateAccountViewModel
import kotlinx.android.synthetic.main.activity_forget_password.*

class GoogleVerifiedActivity : NewBaseActivity<CreateAccountViewModel, ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_google_verified

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.title_set_trade_psw))
        confirmBtn.setOnClickListener {
            LaunchConfig.startTradePasswordNextActivity(this)
        }
    }


    override fun initData() {

        }


}