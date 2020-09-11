package com.kopernik.data.network

import com.kopernik.app.network.RetrofitClient
import com.kopernik.data.service.HomeService

class HomeNetWork {
    private val mService by lazy { RetrofitClient.getInstance().create(HomeService::class.java) }

    suspend fun sendCode(phone: String,imageCode:String) = mService.sendCode(phone,imageCode)
    suspend fun checkUser(phone: String) = mService.checkUser(phone)
    suspend fun sendEmailCode(phone: String) = mService.sendEmailCode(phone)
    suspend fun checkPhone(phone: String, verifyCode:String) = mService.checkPhone(phone,verifyCode)
    suspend fun checkEMail(email: String, verifyCode:String) = mService.checkEMail(email,verifyCode)
    suspend fun getImageCode(phone: String) = mService.getImageCode(phone)
    suspend fun createAccount(map: Map<String, String>) = mService.createAccount(map)
    suspend fun forgetPassword(map: Map<String, String>) = mService.forgetPassword(map)
    suspend fun login(map: Map<String, String>) = mService.login(map)
    suspend fun inviteFriends(map: Map<String, String>) = mService.inviteFriends(map)
    suspend fun inviteFriendsSecond(map: Map<String, String>) = mService.inviteFriendsSecond(map)
    suspend fun changeTradePsw(map: Map<String, String>) = mService.changeTradePsw(map)
    suspend fun checkSalePwd(map: Map<String, String>) = mService.checkSalePwd(map)
    suspend fun addContact(map: Map<String, String>) = mService.addContact(map)
    suspend fun buyMineMachine(map: Map<String, String>) = mService.buyMineMachine(map)
    suspend fun checkAppVersion() = mService.checkAppVersion()
    suspend fun getHomeList() = mService.getHomeList()
    suspend fun getNotice() = mService.getNotice()
    suspend fun getUtkStatus() = mService.getUtkStatus()
    suspend fun getUtk( map:Map<String,String>) = mService.getUtk(map)
    suspend fun compose( map:Map<String,String>) = mService.compose(map)
    suspend fun getContacts() = mService.getContacts()
    suspend fun getMachineList() = mService.getMachineList()
    suspend fun getUtdmTotal() = mService.getUtdmTotal()
    suspend fun getAssetBlance() = mService.getAssetBlance()
    suspend fun getAssetConfig() = mService.getAssetConfig()
    suspend fun getMachine( map:Map<String,String>) = mService.getMachine(map)
    suspend fun checkTradePsw( map:Map<String,String>) = mService.checkTradePsw(map)
    suspend fun delContact(id: Int) = mService.delContact(id)
    suspend fun checkVersion() = mService.checkVersion()
    suspend fun loginOut() = mService.loginOut()
    suspend fun realNameAuth(name: String,idCard:String) = mService.realNameAuth(name,idCard)
    suspend fun getGains(iconType: String) = mService.getGains(iconType)
    suspend fun saveGains() = mService.saveGains()

    companion object {
        @Volatile
        private var netWork: HomeNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HomeNetWork().also { netWork = it }
        }
    }

}