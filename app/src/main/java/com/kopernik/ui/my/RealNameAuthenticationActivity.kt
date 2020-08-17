package com.kopernik.ui.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity

class RealNameAuthenticationActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_real_name_authentication

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_real_authentic))
    }

    override fun initData() {

    }

}