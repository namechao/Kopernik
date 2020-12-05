package com.kopernik.ui.asset.entity

data class UTCAssetEntity(
    val datas: List<UtcComRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UtcComRecord(
    val amount: String,
    val createTime: Long,
    val id: Int,
    val rate: String,
    val remark: String,
    val uid: Int,
    val utdmAmount: String,
    val utkAmount: String
)
data class ExchangeRecordEntity(
    val datas: List<ExchangeRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class ExchangeRecord(
    val createTime: Long,
    val id: Int,
    val rate: String,
    val remark: Any,
    val uid: Int,
    val utcAmount: String,
    val uytAmount: String
)
data class UytTestExchangeRecordEntity(
    val datas: List<UytTestExchangeRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UytTestExchangeRecord(
    val createTime: Long,
    val id: Int,
    val rate: String,
    val remark: Any,
    val uid: Int,
    val utcAmount: String,
    val uytTestAmount: String
)