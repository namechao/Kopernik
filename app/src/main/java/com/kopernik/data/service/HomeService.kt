package com.kopernik.data.service

import com.kopernik.ui.home.Entity.HomeEntity
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.asset.entity.ExtractBean
import com.kopernik.ui.home.Entity.GetUtkEntity
import com.kopernik.ui.home.Entity.NoticeEntity
import com.kopernik.ui.mine.entity.*
import com.kopernik.ui.my.bean.InviteFriendsEntity
import com.kopernik.ui.my.bean.VersionEntity
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
    //邀请好友
    @FormUrlEncoded
    @POST("user/inviteFriendsSecond")
    suspend fun inviteFriendsSecond(@FieldMap map: Map<String, String>): BaseResult<InviteFriendsEntity>

    //修改交易密码
    @FormUrlEncoded
    @POST("user/saveSalePwd")
    suspend fun changeTradePsw(@FieldMap map: Map<String, String>): BaseResult<Any>

    //获取联系人列表
    @GET("register/getcontacts")
    suspend fun getContacts(): BaseResult<ContactBean>

    //获取首页数据
    @GET("home/home")
    suspend fun getHomeList(): BaseResult<HomeEntity>
    //获取首页数据
    @GET("home/getNotice")
    suspend fun getNotice(): BaseResult<NoticeEntity>
    //获取领取utk状态
    @GET("seckill/getTime")
    suspend fun getUtkStatus(): BaseResult<GetUtkEntity>
    //领取utk
    @FormUrlEncoded
    @POST("seckill/getUtk")
    suspend fun getUtk(@FieldMap map: Map<String, String>): BaseResult<Any>
  //获取矿机列表
    @GET("seckill/machineList")
    suspend fun getMachineList(): BaseResult<MineBean>
    //获取utdm收益
    @GET("seckill/getUtdmTotal")
    suspend fun getUtdmTotal(): BaseResult<Amounts>
    //获取资产总值
    @GET("asset/blance")
    suspend fun getAssetBlance(): BaseResult<SynthetiseUtcEntity>
    //兑换比例
    @GET("asset/getAll")
    suspend fun getAssetConfig(): BaseResult<AllConfigEntity>
 //获取可用矿机
    @GET("seckill/getMachine")
    suspend fun getMachine(@QueryMap map:Map<String,String>): BaseResult<OtherMineBean>
    //买矿机
    @FormUrlEncoded
    @POST("seckill/buy")
    suspend fun buyMineMachine(@FieldMap map: Map<String, String>): BaseResult<Any>
    //合成
    @FormUrlEncoded
    @POST("asset/compose")
    suspend fun compose(@FieldMap map: Map<String, String>): BaseResult<Any>
    //验证交易密码
    @FormUrlEncoded
    @POST("user/checkSalePwd")
    suspend fun checkTradePsw(@FieldMap map: Map<String, String>): BaseResult<Any>

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
    //检查版本
    @GET("user/deploy")
    suspend fun checkVersion(): BaseResult<VersionEntity>
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