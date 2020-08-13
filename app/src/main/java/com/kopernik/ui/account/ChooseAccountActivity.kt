package com.kopernik.ui.account

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import kotlinx.android.synthetic.main.activity_choose_account.*

class ChooseAccountActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_choose_account

    override fun initView(savedInstanceState: Bundle?) {
        importAccount.setOnClickListener {
            LaunchConfig.startImportMnemonicAc(this)
        }
        newAccount.setOnClickListener {
            LaunchConfig.startAddNewAccountAc(this)
            finish()
        }
        userSkip.setOnClickListener {
            UserConfig.singleton?.accountString="skip"
            LaunchConfig.startMainAc(this)
            finish()
        }
    }

    override fun initData() {

    }
}