package com.kopernik.data.service

import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.entity.UpdateBean
import retrofit2.http.*

/**
 *   @auther : zhanglichao
 *   time   : 2019/10/29
 */

interface HomeService {
    @FormUrlEncoded
    @POST("user/sendPhone")
    suspend fun sendCode(@Field("phone") phone:String):BaseResult<Any>
    @FormUrlEncoded
    @POST("user/sendMail")
    suspend fun sendEmailCode(@Field("phone") phone:String):BaseResult<Any>
    @FormUrlEncoded
    @POST("user/checkPhone")
    suspend fun checkPhone(@Field("phone") phone:String,@Field("verifyCode") verifyCode:String):BaseResult<Any>
    @FormUrlEncoded
    @POST("user/checkEMail")
    suspend fun checkEMail(@Field("email") phone:String,@Field("verifyCode") verifyCode:String):BaseResult<Any>
    //确认导入账户
    @FormUrlEncoded
    @POST("user/saveuser")
    suspend fun createAccount(@FieldMap map: Map<String, String>): BaseResult<AccountBean>
    @FormUrlEncoded
    @POST("user/saveLoginPwd")
    suspend fun forgetPassword(@FieldMap map: Map<String, String>): BaseResult<AccountBean>
    //登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@FieldMap map: Map<String, String>): BaseResult<AccountBean>
    //更新密码
    @FormUrlEncoded
    @POST("register/updatepwd")
    suspend fun updatePassword(@FieldMap map: Map<String, String>): BaseResult<Any>

    //更新昵称
    @FormUrlEncoded
    @POST("register/updatelabel")
    suspend fun updateNick(@FieldMap map: Map<String, String>): BaseResult<Any>

    //获取联系人列表
    @GET("register/getcontacts")
    suspend fun getContacts(): BaseResult<ContactBean>

    //添加联系人
    @FormUrlEncoded
    @POST("register/saveaddress")
    suspend fun addContact(@FieldMap map: Map<String, String>): BaseResult<Any>

    //删除选中联系人
    @FormUrlEncoded
    @POST("register/deladdress")
    suspend fun delContact(@Field("id") id: Int): BaseResult<Any>
    //验证密码
    @GET("register/checkPassword")
    suspend fun verifyPwd(@Query("acountPwd") pwd: String): BaseResult<Any>
    //验证密码
    @GET("register/deploy")
    suspend fun checkAppVersion(): BaseResult<UpdateBean>
    //获取推荐收益
    @GET("register/getGains")
    suspend fun getGains(@Query("iconType") iconType:String): BaseResult<ExtractBean>
    //提取收益
    @GET("register/saveGains")
    suspend fun saveGains(): BaseResult<Any>
}