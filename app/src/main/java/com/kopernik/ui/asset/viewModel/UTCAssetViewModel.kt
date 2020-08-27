package com.kopernik.ui.asset.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.asset.entity.AssetItemEntity
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.asset.entity.UTCAssetEntity
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.AssetConfitEntity
import com.kopernik.ui.mine.entity.RateEntity

class UTCAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val composeRecord = SingleLiveEvent<BaseResult<UTCAssetEntity>>()
    private val getRate = SingleLiveEvent<BaseResult<RateEntity>>()

    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun getRate(): SingleLiveEvent<BaseResult<RateEntity>> {
        launchGo({
            getRate.value = homeRepository.getRate()
        },isShowDialog = false)
        return getRate
    }
    fun composeRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UTCAssetEntity>> {
        launchGo({
            composeRecord.value = homeRepository.composeRecord(map)
        },isShowDialog = false)
        return composeRecord
    }

}