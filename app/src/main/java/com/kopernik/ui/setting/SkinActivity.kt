package com.kopernik.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.StringUtils
import kotlinx.android.synthetic.main.activity_skin.*
import skin.support.SkinCompatManager
import skin.support.utils.SkinPreference

class SkinActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_skin

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("皮肤切换")
        val skinName = SkinPreference.getInstance().skinName
        if (StringUtils.isEmpty(skinName)) {
            whiteSkinSpt.rightIconIV.visibility = View.VISIBLE
            goldSkinSpt.rightIconIV.visibility = View.INVISIBLE
        } else {
            whiteSkinSpt.rightIconIV.visibility = View.INVISIBLE
            goldSkinSpt.rightIconIV.visibility = View.VISIBLE
        }
        // TODO: 2019/11/13 关闭有问题
        // TODO: 2019/11/13 关闭有问题
        whiteSkinSpt.setOnClickListener {
            if (StringUtils.isEmpty(skinName)) return@setOnClickListener
            SkinCompatManager.getInstance().restoreDefaultTheme()
            resetSkinSwitchBtn("")
            mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
        }
        goldSkinSpt.setOnClickListener {
            if (skinName == "glob") return@setOnClickListener
            SkinCompatManager.getInstance()
                .loadSkin("glob", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
            resetSkinSwitchBtn("glob")
            getActivity()!!.finish()
            mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
        }
    }
    private fun resetSkinSwitchBtn(skinName: String) {
        if (StringUtils.isEmpty(skinName)) {
            whiteSkinSpt.rightIconIV.visibility = View.VISIBLE
            goldSkinSpt.rightIconIV.visibility = View.INVISIBLE
        } else {
            whiteSkinSpt.rightIconIV.visibility = View.INVISIBLE
            goldSkinSpt.rightIconIV.visibility = View.VISIBLE
        }
    }
    override fun initData() {

    }

}