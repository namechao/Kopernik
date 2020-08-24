package com.kopernik.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.login.bean.AccountBean

class RegisterViewModel :BaseViewModel(){
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val phoneCode = SingleLiveEvent<BaseResult<Any>>()
    private val emailCode = SingleLiveEvent<BaseResult<Any>>()
    private val checkPhoneCode = SingleLiveEvent<BaseResult<Any>>()
    private val checkEmailCode = SingleLiveEvent<BaseResult<Any>>()

    fun sendCode(sendCode:String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            phoneCode.value = homeRepository.sendCode(sendCode)
        })
        return phoneCode
    }
    fun sendEmailCode(sendCode:String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            emailCode.value = homeRepository.sendEmailCode(sendCode)
        })
        return emailCode
    }
    fun checkPhone(phone: String, verifyCode:String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            checkPhoneCode.value = homeRepository.checkPhone(phone,verifyCode)
        })
        return checkPhoneCode
    }
    fun checkEMail(email: String, verifyCode:String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            checkEmailCode.value = homeRepository.checkEMail(email,verifyCode)
        })
        return checkEmailCode
    }

}