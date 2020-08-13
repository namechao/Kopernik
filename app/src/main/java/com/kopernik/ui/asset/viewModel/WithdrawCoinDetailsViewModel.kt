package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.AssetOptBean

class WithdrawCoinDetailsViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val assetOptBean = MutableLiveData<BaseResult<AssetOptBean>>()
    private val submit = SingleLiveEvent<BaseResult<Any>>()
    private val data = SingleLiveEvent<BaseResult<Any>>()
    fun getWithDrawSatatus(iconType :String): MutableLiveData<BaseResult<AssetOptBean>> {
        launchGo({
            assetOptBean.value = homeRepository.getWithDrawSatatus(iconType)
        }, {}, isShowDialog = true)
        return assetOptBean
    }

    fun submitWithDrawlCoin(map: Map<String, String>): MutableLiveData<BaseResult<Any>> {
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