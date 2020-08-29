package com.kopernik.ui.mine.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.mine.entity.*

class MineViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val getUtdmTotal = SingleLiveEvent<BaseResult<Amounts>>()
    private val getAssetBlance = SingleLiveEvent<BaseResult<SynthetiseUtcEntity>>()
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val compose = SingleLiveEvent<BaseResult<Any>>()


    fun getUtdmTotal(): SingleLiveEvent<BaseResult<Amounts>> {
        launchGo({
            getUtdmTotal.value = homeRepository.getUtdmTotal()
        },isShowDialog = false)
        return getUtdmTotal
    }
    fun getAssetBlance(): SingleLiveEvent<BaseResult<SynthetiseUtcEntity>> {
        launchGo({
            getAssetBlance.value = homeRepository.getAssetBlance()
        },isShowDialog = false)
        return getAssetBlance
    }
    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun compose(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            compose.value = homeRepository.compose(map)
        },isShowDialog = true)
        return compose
    }
}