package com.kopernik.ui.home.Entity

data class GetUtkEntity(
    val config: Config,
    val flag: Int,
    val switchReceive: Int,
    val time: Any
)

data class Config(
    val end: Int,
    val id: Int,
    val onceCount: Int,
    val onceTime: Int,
    val parentCount: Int,
    val personCount: Int,
    val start: Int,
    val switchReceive: Int,
    val switchStatus: Int,
    val utcAmount: String,
    val uytAmount: String
)