package com.kopernik.ui.account.bean

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
    val loginAcountHash: String = "",
    var loginlabel: String = "",
    val nodeHash: String,
    val token: String,
    val imgUrl: String
)

data class Mnemonic(
    val mnemonic: List<String>,
    val address: String,
    val publicKey: String,
    val seed: String
) : Serializable
