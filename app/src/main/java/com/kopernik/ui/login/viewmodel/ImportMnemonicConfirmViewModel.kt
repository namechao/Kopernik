package com.kopernik.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.login.bean.AccountBean

class ImportMnemonicConfirmViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = MutableLiveData<BaseResult<AccountBean>>()

    fun importAccount(
        acountPwd: String, chainHash: String,
        acountHash: String
    ): MutableLiveData<BaseResult<AccountBean>> {
        launchGo({
            var map = mapOf(
                "acountPwd" to acountPwd,
                "chainHash" to chainHash,
                "acountHash" to acountHash
            )
            accountBean.value = homeRepository.importAccount(map)
        })
        return accountBean
    }
}