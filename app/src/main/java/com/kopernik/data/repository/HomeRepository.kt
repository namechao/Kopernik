package com.kopernik.data.repository

import com.aleyn.mvvm.base.BaseModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.network.HomeNetWork
import com.kopernik.ui.account.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.entity.UpdateBean

class HomeRepository private constructor(private val newWork: HomeNetWork):BaseModel(){
    suspend fun createAccount(acountLabel:String,acountPwd:String,
                              chainHash:String,  acountHash:String):BaseResult<AccountBean>{
        return newWork.creatAccount(acountLabel, acountPwd, chainHash, acountHash)
    }

    suspend fun importAccount(map: Map<String, String>): BaseResult<AccountBean> {
        return newWork.importAccount(map)
    }

    suspend fun updatePassword(map: Map<String, String>): BaseResult<Any> {
        return newWork.updatePassword(map)
    }

    suspend fun updateNick(map: Map<String, String>): BaseResult<Any> {
        return newWork.updateNick(map)
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
    suspend fun verifyPwd(pwd: String): BaseResult<Any> {
        return newWork.verifyPwd(pwd)
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

