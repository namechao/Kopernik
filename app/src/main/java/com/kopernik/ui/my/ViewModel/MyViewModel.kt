package com.kopernik.ui.my.ViewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.my.bean.VersionEntity

class MyViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }
    private val data = SingleLiveEvent<BaseResult<VersionEntity>>()

    //验证密码
    fun checkVersion(): SingleLiveEvent<BaseResult<VersionEntity>> {
        launchGo({
            data.value = homeRepository.checkVersion()
        },isShowDialog = false)
        return data
    }
}