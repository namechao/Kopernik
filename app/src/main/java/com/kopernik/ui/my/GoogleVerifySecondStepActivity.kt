package com.kopernik.ui.my

import android.os.Bundle

import androidx.databinding.ViewDataBinding

import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig

import com.kopernik.ui.login.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_google_verify_second_step.*

class GoogleVerifySecondStepActivity : NewBaseActivity<RegisterViewModel, ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_google_verify_second_step

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.title_two_step))
        okBtn.setOnClickListener {
            LaunchConfig.startGoogleVerifySecondStepActivity(this)
        }
    }


    override fun initData() {

        }


}