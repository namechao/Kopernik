package com.kopernik.ui.my

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.TimeConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.LanguageDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.factory.ViewModelFactory
import com.kopernik.app.utils.LocalManageUtil
import com.kopernik.ui.setting.viewModel.DefaultViewModel
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus

class SettingActivity : NewBaseActivity<NoViewModel,ViewDataBinding>(),
    LanguageDialog.LanguageChooseListener {


    override fun layoutId()=R.layout.activity_setting

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.my_setting))
        EventBus.getDefault().post(LocalEvent<Any>(LocalEvent.openSetting))

    }

    override fun initData() {


        var localLanguageName = getString(R.string.chinese)
        val languageType: Int? = UserConfig.singleton?.languageTag
        if (languageType == 1) {
            localLanguageName = getString(R.string.chinese)
        } else if (languageType == 2) {
            localLanguageName = getString(R.string.english)
        }
        languageSpt.setRightString(localLanguageName)
        val finalLocalLanguageName = localLanguageName
        languageSpt.setOnClickListener {
            val dialog: LanguageDialog = LanguageDialog.newInstance(finalLocalLanguageName)
            dialog.setListener(this)
            dialog.show(supportFragmentManager, "language")
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
        }
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