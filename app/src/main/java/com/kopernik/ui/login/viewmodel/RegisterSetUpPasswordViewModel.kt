package com.kopernik.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.login.bean.AccountBean

class RegisterSetUpPasswordViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = SingleLiveEvent<BaseResult<AccountBean>>()

    fun createAccount(
        type: String, acount: String,
        invitationCode: String,pwd:String,ipAddress:String): SingleLiveEvent<BaseResult<AccountBean>> {
        launchGo({
            var map = mapOf(
                "type" to type,
                "acount" to acount,
                "invitationCode" to invitationCode,
                "pwd" to pwd,
                "ipAddress" to ipAddress
            )
            accountBean.value = homeRepository.createAccount(map)
        })
        return accountBean
    }
}