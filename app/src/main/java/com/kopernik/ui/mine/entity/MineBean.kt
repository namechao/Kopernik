package com.kopernik.ui.mine.entity

import java.io.Serializable


data class MineBean(
    val nachineList: List<Machine>,
    val parentFlag: Boolean,
    val flag: Boolean,
    val uytPrice:String,
    val uytProPrice:String,
    val utdmPrice:String,
    val user: User,
    val uytCaptial: UytCaptial,
    val uytProCaptial: UytProCaptial,
    val utdmCaptial: UtdmCaptial,
    val uytToUsdt: String,
    val utdmToUsdt: String,
    val uytProToUsdt: String,
    var uytRatio: String?,
    var uytproRatio: String?,
    var utdmRatio: String?,
    var utdmUytRatio: String?
):Serializable

data class Machine(
    val createTime: String,
    val endTime: Long,
    val id: String,
    val inOrder: String,
    val name: String,
    val power: String,
    val price: String,
    val rate: String,
    val rateMax: String,
    val rateMin: Double,
    val startTime: Long,
    val status: String,
    val stock: Int,
    val switchStatus: Int,
    val type: Int,
    val url: String
):Serializable

data class User(
    val createTime: String,
    val email: String,
    val ggPwd: String,
    val id: String,
    val idCard: String,
    val invitationCode: String,
    val level: String,
    val loginPwd: String,
    val memo: String,
    val name: String,
    val parentUid: Int,
    val phone: String,
    val salePwd: String,
    val token: String,
    val uid: String
):Serializable

data class UytCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val type: String,
    val uid: String
):Serializable
data class UytProCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val type: String,
    val uid: String
):Serializable
data class UtdmCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val type: String,
    val uid: String
):Serializable


data class OtherMineBean(
    val datas: List<Data>,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val total: Int
)

data class Data(
    val amount: String,
    val amountTotal: String,
    val createTime: String,
    val email: String,
    val id: Int,
    val phone: String,
    val power: String,
    val price: String,
    val rate: String,
    val status: String,
    val type: Int,
    val uid: String
)

data class Amounts (var amount: String )