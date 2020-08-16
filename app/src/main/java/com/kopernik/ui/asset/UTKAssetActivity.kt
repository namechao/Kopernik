package com.kopernik.ui.asset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity

class UTKAssetActivity : NewFullScreenBaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_utk_asset

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

}