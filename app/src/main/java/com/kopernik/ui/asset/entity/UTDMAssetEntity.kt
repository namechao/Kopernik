package com.kopernik.ui.asset.entity

data class UTDMAssetEntity(
    val datas: List<UTDMRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UTDMRecord(
    val amount: String,
    val createTime: Long,
    val id: Int,
    val rate: String,
    val remark: String,
    val uid: Int,
    val utdmAmount: String,
    val utkAmount: String
)
