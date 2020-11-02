package com.kopernik.ui.asset.entity

import java.io.Serializable

//"totalUsdt": "650566.95524699604640420000",  //总资产折合usdt
//"uytProAmountFreeze": "0.000000000000",//uyt冻结
//"utcAmount": "0.00000000",//utc可用
//"utkAmount": "28.24492000",//utk 可用
//"utdmAmountFreeze": "0.000000000000",// utdm冻结
//"usdtCny": "0.000000000000000",//usdt 折合人民币
//"utdmAmount": "396.24492000",//utdm可用
//"uytAmountFreeze": "0.000000000000",//uyt_test 冻结
//"usdtAmountFreeze": "0.000000000000",//usdt 冻结
//"uytAmount": "1025515.09234689",//uyt可用
//"usdtAmount": "0.00000000",//usdt可用
//"uytProCny": "21561.88776116126400000000000",// uyt 折合人民币
//"utkCny": "18.89020249600000000000000",//utk折合人民币
//"utdmCny": "2650.08602496000000000000000",//utdm折合人民币
//"utcAmountFreeze": "0.000000000000",//utc冻结
//"utkAmountFreeze": "0.000000000000",//utk冻结
//"uytCny": "4326760.93270329229435128960000",//uyt_test折合人民币
//"utcCny": "0.00000000000000000000000",//utc折合人民币
//"uytProAmount": "470.65203963",//uyt可用
//"totalUsdtCny": "4350991.79669190955835128960000" //总资产 这个人民币

data class AssetEntity(
    val totalUsdt: String,
    val totalUsdtCny: String,
    val usdtAmount: String,
    val usdtAmountFreeze: String,
    val usdtCny: String,
    val utcAmount: String,
    val utcAmountFreeze: String,
    val utcCny: String,
    val utdmAmount: String,
    val utdmAmountFreeze: String,
    val utdmCny: String,
    val utkAmount: String,
    val utkAmountFreeze: String,
    val utkCny: String,
    val uytAmount: String,
    val uytAmountFreeze: String,
    val uytCny: String,
    val uytProAmount: String,
    val uytProAmountFreeze: String,
    val uytProCny: String
): Serializable
data class CoinType(var headRes:Int, var coinType:String)