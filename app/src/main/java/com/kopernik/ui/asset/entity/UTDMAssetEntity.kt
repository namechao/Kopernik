package com.kopernik.ui.asset.entity

data class UTDMAssetEntity(
    val datas: List<UTDMRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UTDMRecord(
    val communityGains: String,
    val createTime: Long,
    val id: Int,
    val levelGains: String,
    val shareAllGains: String,
    val shareGains: String,
    val staticGains: String,
    val totalGains: String,
    val uid: Int
)
