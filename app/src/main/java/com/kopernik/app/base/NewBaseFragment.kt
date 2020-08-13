package com.kopernik.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.BaseFragment
import com.aleyn.mvvm.base.BaseViewModel
import com.kopernik.app.events.LocalEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NewBaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseFragment<VM, DB>() {
    val mEventBus = EventBus.getDefault()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this)
        }
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
}