package com.kopernik.ui.asset.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.ExtractBean

class AssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val assetBean = SingleLiveEvent<BaseResult<AssetBean>>()
    private val getGains = SingleLiveEvent<BaseResult<ExtractBean>>()
    private val saveGains = SingleLiveEvent<BaseResult<Any>>()
    fun getAsset(): SingleLiveEvent<BaseResult<AssetBean>> {
        launchGo({
            assetBean.value = homeRepository.getAsset()
        }, {}, isShowDialog = false)
        return assetBean
    }

    fun getGains(iconType: String): SingleLiveEvent<BaseResult<ExtractBean>> {
        launchGo({
            getGains.value = homeRepository.getGains(iconType)
        }, {}, isShowDialog = true)
        return getGains
    }

    fun saveGains(): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            saveGains.value = homeRepository.saveGains()
        }, {}, isShowDialog = true)
        return saveGains
    }
}