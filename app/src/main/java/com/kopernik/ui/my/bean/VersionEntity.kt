package com.kopernik.ui.my.bean


data class VersionEntity(
    val deploy: Deploy,
    val user: User
)

data class Deploy(
    val id: Int,
    val source: String,
    val type: Int,
    val updateTime: Long,
    val url: String,
    val versionCode: Int,
    val versionDesc: String,
    val versionName: String
)

data class User(
    val achievement: String,
    val createTime: Long,
    val email: String,
    val ggPwd: String,
    val id: Int,
    val idCard: String,
    val invitationCode: String,
    val level: Int,
    val loginPwd: String,
    val machineLevel: String,
    val machineTotal: String,
    val memo: String,
    val name: String,
    val parentUid: Int,
    val phone: String,
    val salePwd: String,
    val token: String,
    val uid: Int
)
