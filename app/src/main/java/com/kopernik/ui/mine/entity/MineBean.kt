package com.kopernik.ui.mine.entity

data class MineBean(
    val nachineList: List<Machine>,
    val parentFlag: Boolean,
    val stockCount1: String,
    val stockCount2: String,
    val stockCount3: String,
    val stockCount4: String,
    val stockCount5: String,
    val stockCount6: String,
    val user: User,
    val uytCaptial: UytCaptial,
    val uytToUsdt: String
)

data class Machine(
    val createTime: String,
    val endTime: String,
    val id: String,
    val inOrder: String,
    val name: String,
    val power: String,
    val price: String,
    val rate: String,
    val rateMax: String,
    val rateMin: Double,
    val startTime: String,
    val status: String,
    val stock: String,
    val switchStatus: String,
    val type: Int,
    val url: String
)

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
)

data class UytCaptial(
    val amount: String,
    val createTime: String,
    val freeze: String,
    val id: String,
    val type: String,
    val uid: String
)
