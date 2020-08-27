package com.kopernik.ui.home.Entity

data class GetUtkEntity(
    val config: Config,
    val flag: Int,
    val switchReceive: Int,
    val time: String
)

data class Config(
    val end: Int,
    val id: String,
    val onceCount: String,
    val onceTime: Int,
    val parentCount: Int,
    val personCount: Int,
    val start: Int,
    val switchReceive: Int,
    val switchStatus: Int,
    val utcAmount: String,
    val uytAmount: String
)