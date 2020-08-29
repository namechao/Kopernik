package com.kopernik.data.network

import com.kopernik.app.network.RetrofitClient
import com.kopernik.data.service.AssetService

class AssetNetWork {
    private val mService by lazy { RetrofitClient.getInstance().create(AssetService::class.java) }
    suspend fun getAsset() = mService.getAsset()
    suspend fun getTobeExtractedList(map: Map<String, String>) = mService.getTobeExtractedList(map)
    suspend fun getAssetTxRecord(map: Map<String, String>) = mService.getAssetTxRecord(map)
    suspend fun getUnMapRecord(map: Map<String, String>) = mService.getUnMapRecord(map)

    suspend fun submitWithDrawlCoin(map: Map<String, String>) = mService.submitWithDrawlCoin(map)
    suspend fun composeRecord(map: Map<String, String>) = mService.composeRecord(map)
    suspend fun exchangeRecord(map: Map<String, String>) = mService.exchangeRecord(map)
    suspend fun transferRecord(map: Map<String, String>) = mService.transferRecord(map)
    suspend fun receiveRecord(map: Map<String, String>) = mService.receiveRecord(map)
    suspend fun gainsDetailRecord(map: Map<String, String>) = mService.gainsDetailRecord(map)
    suspend fun exchange(map: Map<String, String>) = mService.exchange(map)
    suspend fun transfer(map: Map<String, String>) = mService.transfer(map)
    suspend fun cancelWithdraw(url: String) = mService.cancelcash(url)
    suspend fun getGains(iconType: String) = mService.getGains(iconType)
    suspend fun saveGains() = mService.saveGains()
    suspend fun withDrawlCoin() = mService.withDrawlCoin()
    suspend fun verifyPwd(pwd: String) = mService.verifyPwd(pwd)
    suspend fun getWithDrawSatatus(iconType :String) = mService.getWithDrawSatatus(iconType )
//    suspend fun deposit(iconType :String) = mService.deposit(iconType )
//    suspend fun cashwithdrawal(iconType :String) = mService.cashwithdrawal(iconType )
//    suspend fun transferaccount(iconType: String) = mService.transferaccount(iconType)
    suspend fun checkMapping(type: String, iconType: String) = mService.checkMapping(type, iconType)
    suspend fun getAssetConfig() = mService.getAssetConfig()
    suspend fun getRate() = mService.getRate()
    companion object {
        @Volatile
        private var netWork: AssetNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: AssetNetWork().also { netWork = it }
        }
    }

}