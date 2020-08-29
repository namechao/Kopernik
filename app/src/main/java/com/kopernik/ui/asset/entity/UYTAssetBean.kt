package com.kopernik.ui.asset.entity

data class WithDrawlCoinEntity(
    val acountHash: String,
    val memo: String
)

data class UYTDepositWithdarwlAssetBean(
    val datas: List<DepositWithdarwlRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class DepositWithdarwlRecord(
    val addressHash: String,
    val amount: String,
    val createTime: Long,
    val extrinsicHash: String,
    val flag: Int,
    val id: Int,
    val operate: String,
    val rate: String,
    val remark: String,
    val type: String,
    val uid: Int,
    val uidReceive: Int
)
data class UYTTransferEntity(
    val datas: List<UYTTransferRecord>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class UYTTransferRecord(
    val amount: String,
    val createTime: Long,
    val id: String,
    val operate: String,
    val rate: String,
    val remark: String,
    val tradingHash: String,
    val type: String,
    val uid: String,
    val uidReceive: String,
    val flag: Int
)