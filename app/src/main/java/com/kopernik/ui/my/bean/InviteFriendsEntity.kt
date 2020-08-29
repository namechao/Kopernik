package com.kopernik.ui.my.bean

/**
 *
 * @ProjectName:    Kopernik
 * @Package:        com.kopernik.ui.my.bean
 * @ClassName:      InviteFriendsEntity
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/8/24 4:01 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/8/24 4:01 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

data class InviteFriendsEntity(
    val datas: List<InviteFriendsItem>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class InviteFriendsItem(
    val achievement: String,
    val createTime: String,
    val email: String,
    val ggPwd: String,
    val id: Int,
    val idCard: String,
    val invitationCode: String,
    val level: Int,
    val loginPwd: String,
    val machineLevel: Int,
    val machineTotal: String,
    val memo: String,
    val name: String,
    val parentUid: Int,
    val phone: String,
    val salePwd: String,
    val token: String,
    val uid: String
)

