package com.kopernik.ui.asset.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.AssetConfitEntity
import com.kopernik.ui.mine.entity.RateEntity

class UDMTAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val gainsDetailRecord = SingleLiveEvent<BaseResult<UTDMAssetEntity>>()


    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun gainsDetailRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UTDMAssetEntity>> {
        launchGo({
            gainsDetailRecord.value = homeRepository.gainsDetailRecord(map)
        },isShowDialog = false)
        return gainsDetailRecord
    }


}