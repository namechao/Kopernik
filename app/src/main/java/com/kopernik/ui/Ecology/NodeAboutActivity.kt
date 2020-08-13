package com.kopernik.ui.Ecology

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_node_about.*

class NodeAboutActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_node_about

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_node_related))
        mEventBus.post(LocalEvent<Any>(LocalEvent.openSetting))
        modifyNodeLogoSpt.setOnClickListener {
            if (StringUtils.isEmpty(UserConfig.singleton?.getAccount()?.nodeHash)) {
                ToastUtils.showShort(getActivity(), getString(R.string.user_has_not_node))
            } else {
                LaunchConfig.startChooseNodeLogoAc(
                    this,
                    UserConfig.singleton?.getAccount()?.nodeHash
                )
            }
        }
    }

    override fun initData() {

    }
}