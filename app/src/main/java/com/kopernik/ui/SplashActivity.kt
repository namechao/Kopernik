package com.kopernik.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.TimeConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.factory.ViewModelFactory
import com.kopernik.app.utils.ApplicationCheck

class SplashActivity : NewFullScreenBaseActivity<NoViewModel, ViewDataBinding>() {
    var mViewModel = ViewModelFactory.factory().splashViewModel
    override fun layoutId() = R.layout.activity_splash


    override fun initView(savedInstanceState: Bundle?) {

//        setTheme(R.style.AppTheme);//恢复原有的样式
        ImmersionBar.with(this).reset().hideBar(BarHide.FLAG_HIDE_BAR).init()
        mViewModel?.addDisposable(
            mViewModel?.delayByInt(TimeConfig.SPLASH, getLoginStatus())
                ?.subscribe { loginStatus: Int -> this.toNext(loginStatus) }
        )
    }
    private fun getLoginStatus(): Int {
        return if (UserConfig.singleton?.accountString?.isEmpty()!!) -1 else 0
    }
    /**
     * -1 未登录
     * @param loginStatus
     */
    private fun toNext(loginStatus: Int) {
        var check= ApplicationCheck(this)
        if (check.check() or check.checkPkg() or check.checkFileDir(this) or check.checkProxy()) {
            finish()
        }else {
            if (loginStatus == -1) {
                LaunchConfig.startLoginActivity(this)
                finish()
            } else {
                LaunchConfig.startMainAc(this)
                finish()
            }
        }
    }
    override fun initData() {


    }
}