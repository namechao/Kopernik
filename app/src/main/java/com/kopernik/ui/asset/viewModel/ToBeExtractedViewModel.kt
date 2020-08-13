package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*

class ToBeExtractedViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val tobeExtractedBean = MutableLiveData<BaseResult<TobeExtractedBean>>()
    private val assetDetailsBean = MutableLiveData<BaseResult<AssetDetailsChildBean>>()

    fun getTobeExtractedList(map: Map<String, String>): MutableLiveData<BaseResult<TobeExtractedBean>> {
        launchGo({
            tobeExtractedBean.value = homeRepository.getTobeExtractedList(map)
        })
        return tobeExtractedBean
    }

    fun cancelWithdrawl(map: Map<String?, String?>): MutableLiveData<BaseResult<AssetDetailsChildBean>> {
        launchGo({
            assetDetailsBean.value = homeRepository.getAssetDetails(map)
        })
        return assetDetailsBean
    }

}