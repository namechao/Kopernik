package com.kopernik.data.repository

import com.aleyn.mvvm.base.BaseModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.network.AssetNetWork
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.asset.entity.*

class AssetRepository private constructor(private val newWork: AssetNetWork):BaseModel(){

    suspend fun getAsset(): BaseResult<AssetBean> {
        return newWork.getAsset()
    }

    suspend fun getTobeExtractedList(map: Map<String, String>): BaseResult<TobeExtractedBean> {
        return newWork.getTobeExtractedList(map)
    }

    suspend fun getAssetDetails(map: Map<String?, String?>): BaseResult<AssetDetailsChildBean> {
        return newWork.getAssetDetails(map)
    }

    suspend fun submitWithDrawlCoin(map: Map<String, String>): BaseResult<Any> {
        return newWork.submitWithDrawlCoin(map)
    }

    suspend fun getAssetTxRecord(map: Map<String, String>): BaseResult<CrossChainTxBean> {
        return newWork.getAssetTxRecord(map)
    }

    suspend fun getUnMapRecord(map: Map<String, String>): BaseResult<TimeLineBean> {
        return newWork.getUnMapRecord(map)
    }

    suspend fun cancelWithdraw(url: String): BaseResult<Any> {
        return newWork.cancelWithdraw(url)
    }


    suspend fun getGains(iconType: String): BaseResult<ExtractBean> {
        return newWork.getGains(iconType)
    }

    suspend fun deposit(iconType: String): BaseResult<AddressHash> {
        return newWork.deposit(iconType)
    }

    suspend fun cashwithdrawal(iconType: String): BaseResult<AvailableAmount> {
        return newWork.cashwithdrawal(iconType)
    }

    suspend fun transferaccount(iconType: String): BaseResult<TransferAccount> {
        return newWork.transferaccount(iconType)
    }

    suspend fun saveGains(): BaseResult<Any> {
        return newWork.saveGains()
    }
    suspend fun verifyPwd(pwd: String): BaseResult<Any> {
        return newWork.verifyPwd(pwd)
    }

    suspend fun getWithDrawSatatus(iconType: String): BaseResult<AssetOptBean> {
        return newWork.getWithDrawSatatus(iconType)
    }

    suspend fun checkMapping(type: String, iconType: String): BaseResult<Any> {
        return newWork.checkMapping(type, iconType)
    }

    companion object {
        private var INSTANCE: AssetRepository? = null

        fun getInstance(netWork: AssetNetWork) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: AssetRepository(netWork).also { INSTANCE = it }
        }
    }
}
