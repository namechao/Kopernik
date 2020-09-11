package com.kopernik.data.repository

import com.aleyn.mvvm.base.BaseModel
import com.kopernik.ui.home.Entity.HomeEntity
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.network.HomeNetWork
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.home.Entity.GetUtkEntity
import com.kopernik.ui.home.Entity.NoticeEntity
import com.kopernik.ui.mine.entity.*
import com.kopernik.ui.my.bean.InviteFriendsEntity
import com.kopernik.ui.my.bean.VersionEntity
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.entity.UpdateBean
import okhttp3.ResponseBody
import retrofit2.Call

class HomeRepository private constructor(private val newWork: HomeNetWork):BaseModel(){
    suspend fun sendCode(phone:String,imageCode:String):BaseResult<Any>{
        return newWork.sendCode(phone,imageCode)
    }
    suspend fun sendEmailCode(phone:String):BaseResult<Any>{
        return newWork.sendEmailCode(phone)
    }
    suspend fun checkUser(phone:String):BaseResult<Any>{
        return newWork.checkUser(phone)
    }
    suspend fun checkPhone(phone:String,verifyCode:String):BaseResult<Any>{
        return newWork.checkPhone(phone,verifyCode)
    }
    suspend fun getImageCode(phone:String): Call<ResponseBody> {
        return newWork.getImageCode(phone)
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
    suspend fun inviteFriendsSecond(map: Map<String, String>): BaseResult<InviteFriendsEntity> {
        return newWork.inviteFriendsSecond(map)
    }

    suspend fun changeTradePsw(map: Map<String, String>): BaseResult<Any> {
        return newWork.changeTradePsw(map)
    }
    suspend fun checkSalePwd(map: Map<String, String>): BaseResult<Any> {
        return newWork.checkSalePwd(map)
    }

    suspend fun addContact(map: Map<String, String>): BaseResult<Any> {
        return newWork.addContact(map)
    }
    suspend fun buyMineMachine(map: Map<String, String>): BaseResult<Any> {
        return newWork.buyMineMachine(map)
    }
    suspend fun getMachineList(): BaseResult<MineBean> {
        return newWork.getMachineList()
    }
    suspend fun getUtdmTotal(): BaseResult<Amounts> {
        return newWork.getUtdmTotal()
    }
    suspend fun getAssetBlance(): BaseResult<SynthetiseUtcEntity> {
        return newWork.getAssetBlance()
    }
    suspend fun getAssetConfig(): BaseResult<AllConfigEntity> {
        return newWork.getAssetConfig()
    }
  suspend fun getMachine( map:Map<String,String>): BaseResult<OtherMineBean> {
        return newWork.getMachine(map)
    }
    suspend fun compose( map:Map<String,String>): BaseResult<Any> {
        return newWork.compose(map)
    }
    suspend fun checkTradePsw( map:Map<String,String>): BaseResult<Any> {
        return newWork.checkTradePsw(map)
    }

    suspend fun getContacts(): BaseResult<ContactBean> {
        return newWork.getContacts()
    }
    suspend fun getHomeList(): BaseResult<HomeEntity> {
        return newWork.getHomeList()
    }
    suspend fun getNotice(): BaseResult<NoticeEntity> {
        return newWork.getNotice()
    }
  suspend fun getUtkStatus(): BaseResult<GetUtkEntity> {
        return newWork.getUtkStatus()
    }
    suspend fun getUtk(map: Map<String, String>): BaseResult<Any> {
        return newWork.getUtk(map)
    }
    suspend fun delContact(id: Int): BaseResult<Any> {
        return newWork.delContact(id)
    }
    suspend fun realNameAuth(name: String,idCard:String): BaseResult<Any> {
        return newWork.realNameAuth(name,idCard)
    }
    suspend fun checkVersion(): BaseResult<VersionEntity> {
        return newWork.checkVersion()
    }
    suspend fun loginOut(): BaseResult<Any> {
        return newWork.loginOut()
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

