package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil


class TransferViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val data = SingleLiveEvent<BaseResult<Any>>()

    //验证密码
    fun verifyPsw(pwd: String): MutableLiveData<BaseResult<Any>> {
        launchGo({
            data.value = homeRepository.verifyPwd(pwd)
        })
        return data
    }


}