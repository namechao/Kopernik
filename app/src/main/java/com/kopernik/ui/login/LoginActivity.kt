package com.kopernik.ui.login

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}