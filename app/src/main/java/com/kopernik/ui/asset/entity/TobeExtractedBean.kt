package com.kopernik.ui.asset.entity

data class TobeExtractedBean(
    val pageNum: Int,
    val pageSize: Int,
    val total: Int,
    val pages: Int,
    val datas: List<ExtractItem>
)

data class ExtractItem(
    val acountHash: String,
    val addresHash: String,
    val btcAddress: String,
    val btcHash: String,
    val btcHeight: String,
    val btcJyhash: String,
    val btcTime: String,
    val btcvalue: String,
    val chainNumber: String,
    val createitme: String,
    val flag: Int,
    val iconAddress: String,
    val iconType: String,
    val id: Int,
    val memo: String,
    val nonce: String,
    val qdnodeName: String,
    val self: Boolean,
    val type: Int,
    val withdrawalAddress: String
)