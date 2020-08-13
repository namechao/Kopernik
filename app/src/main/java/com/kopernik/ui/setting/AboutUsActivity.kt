package com.kopernik.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.APPHelper
import com.kopernik.common.AboutUsConstant
import kotlinx.android.synthetic.main.activity_about_us.*
import org.greenrobot.eventbus.EventBus

class AboutUsActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_about_us

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_about_us))
        EventBus.getDefault().post(LocalEvent<Any>(LocalEvent.openSetting))
        versinoTv.text = BuildConfig.VERSION_NAME
        telegramSpt.setRightString(AboutUsConstant.email)
        telegramSpt.setOnClickListener { v ->
            APPHelper.copy(
               this,
                AboutUsConstant.email
            )
        }
        versionLL.setOnClickListener(View.OnClickListener { v: View? ->
            LaunchConfig.startVersionAc(
                this
            )
        })
    }


    override fun initData() {

    }
}