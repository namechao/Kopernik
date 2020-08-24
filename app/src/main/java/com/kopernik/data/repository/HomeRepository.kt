package com.kopernik.data.repository

import com.aleyn.mvvm.base.BaseModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.network.HomeNetWork
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.login.bean.User
import com.kopernik.ui.my.bean.InviteFriendsEntity
import com.kopernik.ui.my.bean.InviteFriendsItem
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.entity.UpdateBean

class HomeRepository private constructor(private val newWork: HomeNetWork):BaseModel(){
    suspend fun sendCode(phone:String):BaseResult<Any>{
        return newWork.sendCode(phone)
    }
    suspend fun sendEmailCode(phone:String):BaseResult<Any>{
        return newWork.sendEmailCode(phone)
    }
    suspend fun checkPhone(phone:String,verifyCode:String):BaseResult<Any>{
        return newWork.checkPhone(phone,verifyCode)
    }
    suspend fun checkEMail(email:String,verifyCode:String):BaseResult<Any>{
        return newWork.checkEMail(email,verifyCode)
    }
    suspend fun createAccount(map: Map<String, String>): BaseResult<AccountBean> {
        return newWork.createAccount(map)
    }
    suspend fun forgetPassword(map: Map<String, String>): BaseResult<Any> {
        return newWork.forgetPassword(map)
    }
    suspend fun login(map: Map<String, String>): BaseResult<AccountBean> {
        return newWork.login(map)
    }

    suspend fun inviteFriends(map: Map<String, String>): BaseResult<InviteFriendsEntity> {
        return newWork.inviteFriends(map)
    }

    suspend fun changeTradePsw(map: Map<String, String>): BaseResult<Any> {
        return newWork.changeTradePsw(map)
    }

    suspend fun addContact(map: Map<String, String>): BaseResult<Any> {
        return newWork.addContact(map)
    }

    suspend fun getContacts(): BaseResult<ContactBean> {
        return newWork.getContacts()
    }

    suspend fun delContact(id: Int): BaseResult<Any> {
        return newWork.delContact(id)
    }
    suspend fun realNameAuth(name: String,idCard:String): BaseResult<Any> {
        return newWork.realNameAuth(name,idCard)
    }
    suspend fun checkAppVersion(): BaseResult<UpdateBean> {
        return newWork.checkAppVersion()
    }
    suspend fun getGains(iconType:String): BaseResult<ExtractBean> {
        return newWork.getGains(iconType)
    }
    suspend fun saveGains(): BaseResult<Any> {
        return newWork.saveGains()
    }

    companion object {
        private var INSTANCE: HomeRepository? = null

        fun getInstance(netWork: HomeNetWork) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HomeRepository(netWork).also { INSTANCE = it }
        }
    }
}

