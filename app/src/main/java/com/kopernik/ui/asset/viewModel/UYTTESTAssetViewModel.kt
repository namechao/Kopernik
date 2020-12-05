package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.mine.entity.AllConfigEntity

class UYTTESTAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val withDrawlCoin = SingleLiveEvent<BaseResult<WithDrawlCoinEntity>>()
    private val getTransferConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val transferRecord = SingleLiveEvent<BaseResult<UYTTransferEntity>>()
    private val rechargeCashRecord = SingleLiveEvent<BaseResult<UYTDepositWithdarwlAssetBean>>()
    private val getConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()
    private val exchange = SingleLiveEvent<BaseResult<Any>>()
    private val uytTestExchange = SingleLiveEvent<BaseResult<UytTestExchangeRecordEntity>>()

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
    fun getTransferConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getTransferConfig.value = homeRepository.getAssetConfig()
        })
        return getTransferConfig
    }
    fun withDrawlCoin(type:String): SingleLiveEvent<BaseResult<WithDrawlCoinEntity>> {
        launchGo({
            withDrawlCoin.value = homeRepository.withDrawlCoin(type)
        })
        return  withDrawlCoin
    }

    fun exchange(map: Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            exchange.value = homeRepository.uytTestexchange(map)
        },isShowDialog = false)
        return exchange
    }
    fun rechargeCashRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UYTDepositWithdarwlAssetBean>> {
        launchGo({
            rechargeCashRecord.value = homeRepository.rechargeCashRecord(map)
        },isShowDialog = false)
        return rechargeCashRecord
    }
    fun transferRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UYTTransferEntity>> {
        launchGo({
            transferRecord.value = homeRepository.uytTransferRecord(map)
        },isShowDialog = false)
        return transferRecord
    }
    fun exchangeRecord(map: Map<String,String>): SingleLiveEvent<BaseResult<UytTestExchangeRecordEntity>> {
        launchGo({
            uytTestExchange.value = homeRepository.exchangeRecordUytTest(map)
        },isShowDialog = false)
        return uytTestExchange
    }
}