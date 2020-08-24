package com.kopernik.data.service

import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.login.bean.User
import com.kopernik.ui.my.bean.InviteFriendsEntity
import com.kopernik.ui.my.bean.InviteFriendsItem
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
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    suspend fun sendEmailCode(@Field("email") phone:String):BaseResult<Any>
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
    suspend fun forgetPassword(@FieldMap map: Map<String, String>): BaseResult<Any>
    //登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@FieldMap map: Map<String, String>): BaseResult<AccountBean>
    //邀请好友
    @FormUrlEncoded
    @POST("user/inviteFriends")
    suspend fun inviteFriends(@FieldMap map: Map<String, String>): BaseResult<InviteFriendsEntity>

    //修改交易密码
    @FormUrlEncoded
    @POST("user/saveSalePwd")
    suspend fun changeTradePsw(@FieldMap map: Map<String, String>): BaseResult<Any>

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
    //实人认证
    @FormUrlEncoded
    @POST("user/certification")
    suspend fun realNameAuth(@Field("name") name: String,@Field("idCard") idCard: String): BaseResult<Any>
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