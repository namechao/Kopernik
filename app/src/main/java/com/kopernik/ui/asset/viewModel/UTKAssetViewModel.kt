package com.kopernik.ui.asset.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.mine.entity.AssetConfitEntity
import com.kopernik.ui.mine.entity.RateEntity

class UTKAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val getAllConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val composeRecord = SingleLiveEvent<BaseResult<UTCAssetEntity>>()
    private val exchangeRecord = SingleLiveEvent<BaseResult<ExchangeRecordEntity>>()
    private val getRate = SingleLiveEvent<BaseResult<RateEntity>>()
    private val exchange = SingleLiveEvent<BaseResult<Any>>()
    private val receiveRecord = SingleLiveEvent<BaseResult<ReceiveRecordEntity>>()
    private val transferRecord = SingleLiveEvent<BaseResult<TransferRecordEntity>>()

    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }
    fun transfer(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            exchange.value = homeRepository.transfer(map)
        },isShowDialog = false)
        return exchange
    }
    fun receiveRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<ReceiveRecordEntity>> {
        launchGo({
            receiveRecord.value = homeRepository.receiveRecord(map)
        },isShowDialog = false)
        return receiveRecord
    }
    fun transferRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<TransferRecordEntity>> {
        launchGo({
            transferRecord.value = homeRepository.transferRecord(map)
        },isShowDialog = false)
        return transferRecord
    }
}