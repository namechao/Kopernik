package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.mine.entity.AllConfigEntity

class UYTAssetViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val withDrawlCoin = SingleLiveEvent<BaseResult<WithDrawlCoinEntity>>()
    private val data = SingleLiveEvent<BaseResult<Any>>()
    private val mapping = SingleLiveEvent<BaseResult<Any>>()
    private val unMapping = SingleLiveEvent<BaseResult<Any>>()
    private val getGains = SingleLiveEvent<BaseResult<ExtractBean>>()
    private val saveGains = SingleLiveEvent<BaseResult<Any>>()
    private val getAssetConfig = SingleLiveEvent<BaseResult<AllConfigEntity>>()

    fun getAssetConfig(): SingleLiveEvent<BaseResult<AllConfigEntity>> {
        launchGo({
            getAssetConfig.value = homeRepository.getAssetConfig()
        },isShowDialog = false)
        return getAssetConfig
    }

    fun withDrawlCoin(): SingleLiveEvent<BaseResult<WithDrawlCoinEntity>> {
        launchGo({
            withDrawlCoin.value = homeRepository.withDrawlCoin()
        })
        return  withDrawlCoin
    }

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