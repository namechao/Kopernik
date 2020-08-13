package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.CrossChainTxBean


class CrossChainTxViewModel :BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val assetTxRecord = MutableLiveData<BaseResult<CrossChainTxBean>>()
    private val baseResult = MutableLiveData<BaseResult<Any>>()
    fun getAssetTxRecord( map:Map<String,String>): MutableLiveData<BaseResult<CrossChainTxBean>> {
        launchGo({
            assetTxRecord.value = homeRepository.getAssetTxRecord(map)
        },{

        })
        return assetTxRecord
    }

    fun cancelWithdraw(url: String): MutableLiveData<BaseResult<Any>> {
        launchGo({
            baseResult.value = homeRepository.cancelWithdraw(url)
        })
        return baseResult
    }




}