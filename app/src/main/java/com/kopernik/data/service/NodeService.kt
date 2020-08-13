package com.kopernik.data.service

import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.ui.Ecology.entity.*
import com.kopernik.ui.asset.entity.*
import retrofit2.http.*

/**
 *   @auther : zhanglichao
 *   time   : 2019/10/29
 */

interface NodeService {

    //获取节点列表
    @GET
    suspend fun getNodeList(
        @Url url: String,
        @QueryMap map: Map<String, String>
    ): BaseResult<NodeBean>

    //获取投票信息
    @GET("bnode/voteinfo")
    suspend fun checkVoteInfo(): BaseResult<CheckRegisterInfo>

    //检查是否符合条件注册
    @GET("bnode/registerinfo")
    suspend fun getRegisterInfo(): BaseResult<CheckRegisterInfo>

    //检查节点名字是否已存在
    @GET("bnode/nodecheck")
    suspend fun checkNodeName(@Query("nodeName") nodeName: String): BaseResult<Any>

    //修改节点logo
    @GET("bnode/nodecheck")
    suspend fun modifyNodeLogo(@QueryMap map: Map<String, String>): BaseResult<Any>
    // //公投详情 后拼接id
    @GET
    suspend fun getReferendumDetails(@Url url:String): BaseResult<ReferendumDetailsBean>

    //节点logo 数据
    @GET("register/getNodeImgList")
    suspend fun getNodeLogo(): BaseResult<List<NodeLogoListBean>>
    //我的投票数据
    @GET("bnode/getvotelist")
    suspend fun getvotelist(@QueryMap map: Map<String, String>): BaseResult<MyVoteBean>

    //投票操作记录
    @GET("bnode/voterecord")
    suspend fun getVoteHistory(@QueryMap map: Map<String, String>): BaseResult<VoteHistoryBean>
    //提息 赎回 转投验证信息
    @GET("bnode/checkinfo")
    suspend fun getNodeCheckInfo(@QueryMap map: Map<String, String>): BaseResult<VoteOperateCheckBean>
    //赎回 转投的时间轴
    @GET("bnode/freerecord")
    suspend fun getFreeRecord(@QueryMap map: Map<String, String>): BaseResult<TimeLineBean>
    //投票操作记录
    @FormUrlEncoded
    @POST("bnode/rankNodeList")
    suspend fun getRankNodeList(@FieldMap map: Map<String, String>): BaseResult<NodeLeaderBoardBean>
    //公投列表
    @FormUrlEncoded
    @POST("register/referendumList" )
    suspend fun getReferendumList(@FieldMap map: Map<String, String>): BaseResult<ReferendumListBean>
    //一键提息检查
    @FormUrlEncoded
    @POST("bnode/checkextract" )
    suspend fun checkextract(): BaseResult<OneKeyExtractSignatureBean>
}