package com.kopernik.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.TimeConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.LanguageDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.factory.ViewModelFactory
import com.kopernik.app.utils.LocalManageUtil
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.setting.viewModel.DefaultViewModel
import dev.utils.common.StringUtils
import kotlinx.android.synthetic.main.activity_base_setting.*
import org.greenrobot.eventbus.EventBus
import skin.support.SkinCompatManager
import skin.support.utils.SkinPreference

class BaseSettingActivity : NewBaseActivity<NoViewModel,ViewDataBinding>(),
    LanguageDialog.LanguageChooseListener {


    override fun layoutId()=R.layout.activity_base_setting

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_base_set))
        EventBus.getDefault().post(LocalEvent<Any>(LocalEvent.openSetting))

    }

    override fun initData() {
        if (UserConfig.singleton?.accountBean == null) {
            contactSpt.visibility = View.GONE
            modifyNodeLogoSpt.visibility = View.GONE
        }

        var localLanguageName = getString(R.string.chinese)
        val languageType: Int? = UserConfig.singleton?.languageTag
        if (languageType == 1) {
            localLanguageName = getString(R.string.chinese)
        } else if (languageType == 2) {
            localLanguageName = getString(R.string.english)
        } else if (languageType == 3) {
            localLanguageName = getString(R.string.korean)
        }
        languageSpt.setRightString(localLanguageName)
        val finalLocalLanguageName = localLanguageName
        languageSpt.setOnClickListener {
            val dialog: LanguageDialog = LanguageDialog.newInstance(finalLocalLanguageName)
            dialog.setListener(this)
            dialog.show(supportFragmentManager, "language")
        }
        contactSpt.setOnClickListener { LaunchConfig.startContactAc(this) }
        modifyNodeLogoSpt.setOnClickListener {
            if (StringUtils.isEmpty(UserConfig.singleton?.accountBean?.nodeHash)) {
                ToastUtils.showShort(getActivity(), getString(R.string.user_has_not_node))
            } else {
                LaunchConfig.startChooseNodeLogoAc(
                   this,
                    UserConfig.singleton?.accountBean?.nodeHash
                )
            }
        }
        initSkin()
    }

    private fun initSkin() {
        val skinName = ""
        skinSpt.checkBox.isChecked = !StringUtils.isEmpty(skinName)
        skinSpt.checkBox.setOnClickListener { v ->
            if (StringUtils.isEmpty(SkinPreference.getInstance().skinName)) {
                //切换为夜间模式
                SkinCompatManager.getInstance()
                    .loadSkin("glob", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
                ImmersionBar.with(this)
                    .statusBarColor(R.color.glob_status)
                    .navigationBarColor(R.color.black)
                    .statusBarDarkFont(false)
                    .init()
            } else {
                //切换为白天模式
                SkinCompatManager.getInstance().restoreDefaultTheme()
                ImmersionBar.with(this)
                    .statusBarColor(R.color.white_status)
                    .navigationBarColor(R.color.black)
                    .statusBarDarkFont(true)
                    .init()
            }
           mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
            finish()
        }
    }


    override fun languageChooseListener(languageName: String?) {
        showLoading()
        var n = 1
        when (languageName) {
            getString(R.string.chinese) -> {
                n = 1
            }
            getString(R.string.english) -> {
                n = 2
            }
            getString(R.string.korean) -> {
                n = 3
            }
        }
        UserConfig.singleton?.isReloadHomeWeb = true
        UserConfig.singleton?.isReloadTradWeb = true
        LocalManageUtil.saveSelectLanguage(this, n)
        mEventBus.post(LocalEvent<Any>(LocalEvent.reLoadWeb))
        getViewModel()?.addDisposable(
            getViewModel()?.delayByString(TimeConfig.RESTART_APP, "tag")
                ?.subscribe { tag: String -> disMissWaitingDialog(tag) }
        )
    }

    private fun disMissWaitingDialog(tag: String) {
        dismissLoading()
        mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
        finish()
    }

    fun getViewModel(): DefaultViewModel? {
        return ViewModelFactory.factory().defaultViewModel
    }
}