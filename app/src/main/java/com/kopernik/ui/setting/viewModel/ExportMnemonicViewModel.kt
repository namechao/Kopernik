package com.kopernik.ui.setting.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

class ExportMnemonicViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = MutableLiveData<BaseResult<Any>>()

    fun exportMnemonic(psw:String): MutableLiveData<BaseResult<Any>> {
        launchGo({
//            accountBean.value = homeRepository.verifyPwd(psw)
        })
        return accountBean
    }
}