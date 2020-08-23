package com.kopernik.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.login.bean.AccountBean

class ForgetPasswordViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = SingleLiveEvent<BaseResult<AccountBean>>()

    fun forgetPassword(
        type: String, acount: String, pwd:String): SingleLiveEvent<BaseResult<AccountBean>> {
        launchGo({
            var map = mapOf(
                "type" to type,
                "acount" to acount,
                "pwd" to pwd
            )
            accountBean.value = homeRepository.forgetPassword(map)
        })
        return accountBean
    }
}