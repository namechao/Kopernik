package com.kopernik.data.network

import com.kopernik.app.network.RetrofitClient
import com.kopernik.data.service.HomeService

class HomeNetWork {
    private val mService by lazy { RetrofitClient.getInstance().create(HomeService::class.java) }

    suspend fun sendCode(phone: String) = mService.sendCode(phone)
    suspend fun sendEmailCode(phone: String) = mService.sendEmailCode(phone)
    suspend fun checkPhone(phone: String, verifyCode:String) = mService.checkPhone(phone,verifyCode)
    suspend fun checkEMail(email: String, verifyCode:String) = mService.checkEMail(email,verifyCode)
    suspend fun createAccount(map: Map<String, String>) = mService.createAccount(map)
    suspend fun forgetPassword(map: Map<String, String>) = mService.forgetPassword(map)
    suspend fun login(map: Map<String, String>) = mService.login(map)
    suspend fun updatePassword(map: Map<String, String>) = mService.updatePassword(map)
    suspend fun updateNick(map: Map<String, String>) = mService.updateNick(map)
    suspend fun addContact(map: Map<String, String>) = mService.addContact(map)
    suspend fun checkAppVersion() = mService.checkAppVersion()
    suspend fun getContacts() = mService.getContacts()
    suspend fun delContact(id: Int) = mService.delContact(id)
    suspend fun verifyPwd(pwd: String) = mService.verifyPwd(pwd)
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