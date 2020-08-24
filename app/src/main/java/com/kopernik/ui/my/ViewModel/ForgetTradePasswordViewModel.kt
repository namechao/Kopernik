package com.kopernik.ui.my.ViewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

class ForgetTradePasswordViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = MutableLiveData<BaseResult<Any>>()

    fun changeTradePsw(map: Map<String, String>): MutableLiveData<BaseResult<Any>> {
        launchGo({
            accountBean.value = homeRepository.changeTradePsw(map)
        })
        return accountBean
    }
}