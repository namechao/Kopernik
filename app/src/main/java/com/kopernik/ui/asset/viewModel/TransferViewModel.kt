package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.mine.entity.AllConfigEntity


class TransferViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val transfer = SingleLiveEvent<BaseResult<Any>>()
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun transfer(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            transfer.value = homeRepository.transfer(map)
        },isShowDialog = false)
        return transfer
    }

}