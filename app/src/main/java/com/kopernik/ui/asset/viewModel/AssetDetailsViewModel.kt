package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*

class AssetDetailsViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
//    private val assetBean = MutableLiveData<BaseResult<AssetBean>>()
    private val assetDetailsBean = MutableLiveData<BaseResult<AssetDetailsChildBean>>()
//    private val deposit = SingleLiveEvent<BaseResult<AddressHash>>()
//    private val availableAmount = SingleLiveEvent<BaseResult<AvailableAmount>>()
//    private val transferAccount = SingleLiveEvent<BaseResult<TransferAccount>>()
    private val data = SingleLiveEvent<BaseResult<Any>>()
    private val mapping = SingleLiveEvent<BaseResult<Any>>()
    private val unMapping = SingleLiveEvent<BaseResult<Any>>()
    private val getGains = SingleLiveEvent<BaseResult<ExtractBean>>()
    private val saveGains = MutableLiveData<BaseResult<Any>>()
//    fun getAsset(): SingleLiveEvent<BaseResult<AssetEntity>> {
//        launchGo({
//            assetBean.value = homeRepository.getAsset()
//        }, {}, isShowDialog = false)
//        return assetBean
//    }

    fun getAssetDetails(map: Map<String?, String?>): MutableLiveData<BaseResult<AssetDetailsChildBean>> {
        launchGo({
//            assetDetailsBean.value = homeRepository.getAssetDetails(map)
        })
        return  assetDetailsBean
    }
//    fun deposit(iconType:String): SingleLiveEvent<BaseResult<AddressHash>> {
//        launchGo({
//            deposit.value = homeRepository.deposit(iconType)
//        })
//        return  deposit
//    }
//    fun cashwithdrawal(iconType:String): SingleLiveEvent<BaseResult<AvailableAmount>> {
//        launchGo({
//            availableAmount.value = homeRepository.cashwithdrawal(iconType)
//        })
//        return availableAmount
//    }
//
//    fun transferaccount(iconType: String): SingleLiveEvent<BaseResult<TransferAccount>> {
//        launchGo({
//            transferAccount.value = homeRepository.transferaccount(iconType)
//        })
//        return transferAccount
//    }

    fun mapping(type: String, iconType: String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            mapping.value = homeRepository.checkMapping(type, iconType)
        })
        return mapping
    }
    fun unMapping(type: String, iconType: String): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            unMapping.value = homeRepository.checkMapping(type, iconType)
        })
        return unMapping
    }
    fun getGains(iconType: String): MutableLiveData<BaseResult<ExtractBean>> {
        launchGo({
            getGains.value = homeRepository.getGains(iconType)
        }, {}, isShowDialog = true)
        return getGains
    }

    fun saveGains(): MutableLiveData<BaseResult<Any>> {
        launchGo({
            saveGains.value = homeRepository.saveGains()
        }, {}, isShowDialog = true)
        return saveGains
    }
    //验证密码
    fun verifyPsw(pwd: String): MutableLiveData<BaseResult<Any>> {
        launchGo({
            data.value = homeRepository.verifyPwd(pwd)
        })
        return data
    }
}