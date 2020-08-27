package com.kopernik.ui.mine.entity

data class SynthetiseUtcEntity(
    val utc: String,
    val utdm: String,
    val utk: String,
    val uyt: String
)
data class AssetConfitEntity(
    val config: Config
)

data class Config(
    val id: Int,
    val utcExchange: String,
    val utcPrice: String,
    val utcPricePercentage: String,
    val utdmCompose: String,
    val utdmPrice: String,
    val utdmPricePercentage: String,
    val utkCompose: String,
    val utkPrice: String,
    val utkPricePercentage: String,
    val uytExchange: String
)
data class RateEntity(
    val rateList: List<Rate>
)

data class Rate(
    val id: Int,
    val rate: String,
    val remark: String,
    val type: String
)
data class AllConfigEntity(
    val config: Config,
    val rateList: List<Rate>,
    val utc: String,
    val utdm: String,
    val utk: String,
    val uyt: String
)
