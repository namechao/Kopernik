package com.kopernik.data.service

import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.asset.entity.*
import retrofit2.http.*

/**
 *   @auther : zhanglichao
 *   time   : 2019/10/29
 */

interface AssetService {

    //获取所有资产
    @GET("asset/asset")
    suspend fun getAsset(): BaseResult<AssetBean>

    //跨链充提记录
    @GET("asset/cashwithdrawalrecord")
    suspend fun getAssetTxRecord(@QueryMap map: Map<String, String>): BaseResult<CrossChainTxBean>

    //取消映射记录
    @GET("asset/mappingrecord")
    suspend fun getUnMapRecord(@QueryMap map: Map<String, String>): BaseResult<TimeLineBean>

    //取消提现
    @GET
    suspend fun cancelcash(@Url url: String): BaseResult<Any>

    //资产详情
    @FormUrlEncoded
    @POST("asset/record")
    suspend fun getDetailsAsset(@FieldMap map: Map<String?, String?>): BaseResult<AssetDetailsChildBean>

    //充币按钮
    @GET("asset/recharge")
    suspend fun deposit(@Query("iconType") iconType:String): BaseResult<AddressHash>
    //提币按钮校验
    @GET("asset/cashwithdrawal")
    suspend fun cashwithdrawal(@Query("iconType") iconType:String): BaseResult<AvailableAmount>
    //转账按钮校验
    @GET("asset/transferaccount")
    suspend fun transferaccount(@Query("iconType") iconType: String): BaseResult<TransferAccount>

    //获取推荐收益
    @GET("asset/gains")
    suspend fun getGains(@Query("iconType") iconType: String): BaseResult<ExtractBean>

    //提取收益
    @GET("register/saveGains")
    suspend fun saveGains(): BaseResult<Any>

    //验证密码
    @GET("register/checkPassword")
    suspend fun verifyPwd(@Query("acountPwd") pwd: String): BaseResult<Any>

    //确认转账
    @GET("asset/savetransferaccount")
    suspend fun getWithDrawSatatus(@Query("iconType") iconType: String): BaseResult<AssetOptBean>

    //提现代替列表
    @GET("asset/waitingListRecord")
    suspend fun getTobeExtractedList(@QueryMap map: Map<String, String>): BaseResult<TobeExtractedBean>

    //提币地址提交
    @GET("asset/saveWithdrawalAddress")
    suspend fun submitWithDrawlCoin(@QueryMap map: Map<String, String>): BaseResult<Any>

    //检验映射按钮 或者取消映射按钮
    @GET("asset/getmapping")
    suspend fun checkMapping(
        @Query("type") type: String,
        @Query("iconType") iconType: String
    ): BaseResult<Any>
}