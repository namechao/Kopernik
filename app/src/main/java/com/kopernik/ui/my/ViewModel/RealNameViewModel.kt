package com.kopernik.ui.my.ViewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

class RealNameViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }
    private val data = SingleLiveEvent<BaseResult<Any>>()

    //验证密码
    fun realNameAuth(name: String,idCard:String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            data.value = homeRepository.realNameAuth(name,idCard)
        })
        return data
    }
}