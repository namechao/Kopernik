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
    val createTime: Long,
    val email: Any,
    val ggPwd: Any,
    val id: Int,
    val idCard: Any,
    val invitationCode: String,
    val level: Int,
    val loginPwd: String,
    val memo: String,
    val name: Any,
    val parentUid: Int,
    val phone: String,
    val salePwd: Any,
    val token: String,
    val uid: Int
)