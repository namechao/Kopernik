package com.kopernik.ui.home.Entity

data class HomeEntity(
    val amountList: AmountList,
    val collectList: List<Collect>,
    val machList: List<Mach>,
    val price: Price,
    val priceConfig: PriceConfig,
    val priceTradingPair: PriceTradingPair
)

data class AmountList(
    val utcAmount: String,
    val utdmAmount: String,
    val utkAmount: String,
    val uytProAmount: String,
    val uytAmount: String
)

data class Collect(
    val amount: Double,
    val createTime: Long,
    val email: String,
    val id: Int,
    val phone: String,
    val uid: String
)

data class Mach(
    val amount: String,
    val amountTotal: String,
    val createTime: String,
    val email: String,
    val id: String,
    val phone: String,
    val power: String,
    val price: String,
    val rate: String,
    val status: String,
    val type: Int,
    val uid: String
)

data class Price(
    val btcPrice: String,
    val btcPricePercentage: String,
    val cnyPrice: String,
    val ethPrice: String,
    val ethPricePercentage: String,
    val htPrice: String,
    val htPricePercentage: String,
    val usdtPrice: String,
    val usdtPricePercentage: String,
    val uytPircePercentage:String,
    val uytPirce: String
)

data class PriceConfig(
    val id: String,
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

data class PriceTradingPair(
    val btcDollarPrice: String,
    val btcPercentage: String,
    val btcRmbrPrice: String,
    val ethDollarPrice: String,
    val ethPercentage: String,
    val ethRmbrPrice: String,
    val usdtDollarPrice: String,
    val usdtPercentage: String,
    val usdtRmbrPrice: String
)