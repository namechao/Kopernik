package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.AssetOptBean
import com.kopernik.ui.mine.entity.AllConfigEntity

class WithdrawCoinDetailsViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }

    private val submit = SingleLiveEvent<BaseResult<Any>>()
    private val data = SingleLiveEvent<BaseResult<Any>>()
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }

    fun submitWithDrawlCoin(map: Map<String, String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            submit.value = homeRepository.submitWithDrawlCoin(map)
        })
        return return submit
    }

    //验证密码
    fun verifyPsw(pwd: String): MutableLiveData<BaseResult<Any>> {
        launchGo({
            data.value = homeRepository.verifyPwd(pwd)
        })
        return data
    }
}