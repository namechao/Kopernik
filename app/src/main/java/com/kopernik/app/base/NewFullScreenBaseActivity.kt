package com.kopernik.app.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseActivity
import com.aleyn.mvvm.base.BaseViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.config.AppConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.LocalManageUtil
import dev.DevUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NewFullScreenBaseActivity<VM : BaseViewModel, DB : ViewDataBinding>:BaseActivity<VM,DB>(){
    val mEventBus=EventBus.getDefault()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        DevUtils.init(this)
        if (AppConfig.isImmersionBar){ initImmersionBar()}
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this)
        }
    }
    private fun initImmersionBar() {
            ImmersionBar.with(this)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) //单独指定软键盘模式
                .addTag("tag")
                .init()

    }


    @Subscribe(threadMode = ThreadMode.MAIN) //sticky = true,
    open fun onEvent(event: LocalEvent<Any>) {
//        if (!(this instanceof LoginActivity)) {
//            if (event.status_type.equals(LocalEvent.RE_LOGIN)) {
//                finish();
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this)
        }
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase))
    }

}