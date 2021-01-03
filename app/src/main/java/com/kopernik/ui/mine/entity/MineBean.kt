package com.kopernik.ui.mine.entity

import java.io.Serializable


data class MineBean(
    val MachineConfig: MachineConfig,
    val flag: Boolean,
    val nachineList: List<Machine>,
    val parentFlag: Boolean,
    val usdt: String,
    val usdtCaptial: UsdtCaptial,
    val usdtRatio: String,
    val usdtUytRatio: String,
    val user: User,
    val utcCaptial: UtcCaptial,
    val utcPrice: String,
    val utcToUsdt: String,
    val utdmCaptial: UtdmCaptial,
    val utdmPrice: String,
    val utdmRatio: String,
    val utdmToUsdt: String,
    val utdmUytRatio: String,
    val uytCaptial: UytCaptial,
    val uytPrice: String,
    val uytProCaptial: UytProCaptial,
    val uytProPrice: String,
    val uytProToUsdt: String,
    val uytRatio: String,
    val uytToUsdt: String,
    val uytproRatio: String
):Serializable

data class MachineConfig(
    val end: String,
    val id: String,
    val ipCount: String,
    val onceCount: String,
    val onceTime: String,
    val parentCount: String,
    val personCount: String,
    val start: String,
    val switchReceive: String,
    val switchStatus: String,
    val utcAmount: String,
    val uytAmount: String
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
    val rateMin: String,
    val startTime: Long,
    val status: String,
    val stock: Int,
    val switchStatus: Int,
    val type: Int,
    val url: String
):Serializable

data class UsdtCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val transactionFreeze: String,
    val type: String,
    val uid: String
):Serializable

data class User(
    val achievement: String,
    val cash: String,
    val compose: String,
    val createTime: String,
    val email: String,
    val exchange: String,
    val freeze: String,
    val ggPwd: String,
    val id: String,
    val idCard: String,
    val invitationCode: String,
    val ipAddress: String,
    val level: String,
    val loginPwd: String,
    val machineLevel: String,
    val machineTotal: String,
    val memo: String,
    val name: String,
    val parentUid: String,
    val phone: String,
    val recharge: String,
    val salePwd: String,
    val token: String,
    val transfer: String,
    val uid: String
):Serializable

data class UtcCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val transactionFreeze: String,
    val type: String,
    val uid: String
):Serializable

data class UtdmCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val transactionFreeze: String,
    val type: String,
    val uid: String
):Serializable

data class UytCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val transactionFreeze: String,
    val type: String,
    val uid: String
):Serializable

data class UytProCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val transactionFreeze: String,
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