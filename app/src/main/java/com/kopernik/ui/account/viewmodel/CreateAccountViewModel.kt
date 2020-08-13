package com.kopernik.ui.account.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.account.bean.AccountBean

class CreateAccountViewModel :BaseViewModel(){
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = MutableLiveData<BaseResult<AccountBean>>()

    fun createAccount(acountLabel:String,acountPwd:String,
                      chainHash:String,  acountHash:String): MutableLiveData<BaseResult<AccountBean>> {
        launchGo({
            accountBean.value = homeRepository.createAccount(acountLabel, acountPwd, chainHash, acountHash)
        })
        return accountBean
    }
}