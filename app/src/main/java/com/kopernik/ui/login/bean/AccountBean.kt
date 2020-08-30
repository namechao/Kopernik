package com.kopernik.ui.login.bean

import java.io.Serializable

/**
 *
 * @ProjectName:    UYT
 * @Package:        com.kopernik.ui.login.bean
 * @ClassName:      Account
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/10 3:06 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/10 3:06 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class AccountBean(
    val user: User
)

data class User(
    val createTime: String,
    val email: String,
    val ggPwd: String,
    val id: String,
    val idCard: String,
    val invitationCode: String,
    val level: Int,
    val loginPwd: String,
    val memo: String,
    val name: String,
    val parentUid: String,
    val phone: String,
    val salePwd: String,
    val token: String,
    val uid: String
)