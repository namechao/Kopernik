package com.kopernik.ui.setting.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.setting.entity.UpdateBean

class CheckAppVersionViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val result = MutableLiveData<BaseResult<UpdateBean>>()

    fun checkAppVersion(): MutableLiveData<BaseResult<UpdateBean>> {
        launchGo({
            result.value = homeRepository.checkAppVersion()
        },{},isShowDialog = false)
        return result
    }
}