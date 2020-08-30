package com.kopernik.ui.my.bean

data class VersionEntity(
    val deploy: Deploy
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