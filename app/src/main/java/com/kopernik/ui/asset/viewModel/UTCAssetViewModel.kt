package com.kopernik.ui.asset.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.AssetConfitEntity
import com.kopernik.ui.mine.entity.RateEntity

class UTCAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val getAllConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val composeRecord = SingleLiveEvent<BaseResult<UTCAssetEntity>>()
    private val exchangeRecord = SingleLiveEvent<BaseResult<ExchangeRecordEntity>>()
    private val getRate = SingleLiveEvent<BaseResult<RateEntity>>()
    private val exchange = SingleLiveEvent<BaseResult<Any>>()
    private val getConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()

    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun getConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getConfig.value = homeRepository.getAssetConfig()
        })
        return getConfig
    }
    fun composeRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UTCAssetEntity>> {
        launchGo({
            composeRecord.value = homeRepository.composeRecord(map)
        },isShowDialog = false)
        return composeRecord
    }

    fun exchangeRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<ExchangeRecordEntity>> {
        launchGo({
            exchangeRecord.value = homeRepository.exchangeRecord(map)
        },isShowDialog = false)
        return exchangeRecord
    }
    fun exchange(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            exchange.value = homeRepository.exchange(map)
        },isShowDialog = false)
        return exchange
    }
    fun transfer(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            exchange.value = homeRepository.transfer(map)
        },isShowDialog = false)
        return exchange
    }


}