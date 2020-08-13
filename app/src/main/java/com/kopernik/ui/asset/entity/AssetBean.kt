package com.kopernik.ui.asset.entity
//
//class AssetBean {
//    var ethmining: String? = null
//    var ucapital: UcapitalBean? = null
//    var usdtmining: String? = null
//    var dnsCny: String? = null
//    var usdtCny: String? = null
//    var countCny: String? = null
//    var nodeRevenue: String? = null
//    var voteRevenue: String? = null
//    var btcminingGains: String? = null
//    var ethCalculating: String? = null
//    var usdtminingGains: String? = null
//    var btcCny: String? = null
//    var ethCny: String? = null
//    var btcmining: String? = null
//    var btcCalculating: String? = null
//    var miningRevenue: String? = null
//    var ethminingGains: String? = null
//    var usdtCalculating: String? = null
//    var dnsCountTotal: String? = null
//    var remmondRevenue: String? = null
//    var btcRecharge = -1
//    var ethRecharge = -1
//    var usdtRecharge = -1
//    var htRecharge = -1
//    var btcShow = -1
//    var ethShow = -1
//    var usdtShow = -1
//    var htShow = -1
//    var dnsmining: String? = null
//    var htmining: String? = null
//    var htCalculating: String? = null
//    var htminingGains: String? = null
//    var htCny: String? = null
//    var btcCash = 0
//    var ethCash = 0
//    var usdtCash = 0
//    var htCash = 0
//
//    class UcapitalBean {
//        var id = 0
//        var bitcoinCount: String? = null
//        var bitcoinKycount: String? = null
//        var bitcoinTxfreeze: String? = null
//        var bitcoinJyfreeze: String? = null
//        var dnsCount: String? = null
//        var dnsSycount: String? = null
//        var dnsVotefreeze: String? = null
//        var dnsRmfreeze: String? = null
//        var dnsTcfreeze: String? = null
//        var acountHash: String? = null
//        var addressHash: String? = null
//        var transactionAmount: Any? = null
//        var ethCount: String? = null
//        var ethKycount: String? = null
//        var ethTxfreeze: String? = null
//        var ethJyfreeze: String? = null
//        var ethAddresshash: String? = null
//        var btcMiniingbalance: String? = null
//        var ethMiniingbalance: String? = null
//        var usdtCount: String? = null
//        var usdtKycount: String? = null
//        var usdtTxfreeze: String? = null
//        var usdtJyfreeze: String? = null
//        var usdtMiniingbalance: String? = null
//        var usdtAddresshash: String? = null
//        var issueGain = 0
//        var issueGainFinal = 0
//        var recommendGain = 0
//        var htCount: String? = null
//        var htKycount: String? = null
//        var htTxfreeze: String? = null
//        var htJyfreeze: String? = null
//        var htMiniingbalance: String? = null
//        var htAddresshash: String? = null
//
//    }
//}


data class AssetBean(
    val btcCash: Int,
    val btcCny: String,
    val btcRecharge: String,
    val btcRevenue: String,
    val btcmining: String,
    val btcminingGains: String,
    val capitalBtc: CapitalBtc,
    val capitalEth: CapitalEth,
    val capitalHt: CapitalHt,
    val capitalUsdt: CapitalUsdt,
    val capitalUyt: CapitalUyt,
    val countCny: String,
    val dnsCny: String,
    val dnsCountTotal: String,
    val ethCalculating: String,
    val ethCash: String,
    val ethCny: String,
    val ethRecharge: Int,
    val ethRevenue: String,
    val ethmining: String,
    val ethminingGains: String,
    val htCalculating: String,
    val htCash: String,
    val htCny: String,
    val htRecharge: Int,
    val htRevenue: String,
    val htmining: String,
    val htminingGains: String,
    val miningRevenue: String,
    val nodeRevenue: String,
    val remmondRevenue: String,
    val usdtCalculating: String,
    val usdtCash: String,
    val usdtCny: String,
    val usdtRecharge: Int,
    val usdtRevenue: String,
    val usdtmining: String,
    val usdtminingGains: String,
    val uytRevenue: String,
    val voteRevenue: String
)

data class CapitalBtc(
    val acountHash: String,
    val addressHash: String,
    val balance: String,
    val gradeGains: Int,
    val iconType: String,
    val id: Int,
    val miniingbalance: String,
    val recommendGains: String,
    val transactionFreeze: String,
    val voteFreeze: String = "",
    val withdrawalFreeze: String
)

data class CapitalEth(
    val acountHash: String,
    val addressHash: String,
    val balance: String,
    val gradeGains: Int,
    val iconType: String,
    val id: Int,
    val miniingbalance: String,
    val recommendGains: String,
    val transactionFreeze: String,
    val voteFreeze: String,
    val withdrawalFreeze: String
)

data class CapitalHt(
    val acountHash: String,
    val addressHash: String,
    val balance: String,
    val gradeGains: Int,
    val iconType: String,
    val id: Int,
    val miniingbalance: String,
    val recommendGains: String,
    val transactionFreeze: String,
    val voteFreeze: String,
    val withdrawalFreeze: String
)

data class CapitalUsdt(
    val acountHash: String,
    val addressHash: String,
    val balance: String,
    val gradeGains: Int,
    val iconType: String,
    val id: Int,
    val miniingbalance: String,
    val recommendGains: String,
    val transactionFreeze: String,
    val voteFreeze: String,
    val withdrawalFreeze: String
)

data class CapitalUyt(
    val acountHash: String,
    val addressHash: String,
    val balance: String,
    val gradeGains: Int,
    val iconType: String,
    val id: Int,
    val miniingbalance: String,
    val recommendGains: String,
    val transactionFreeze: String = "",
    val voteFreeze: String = "",
    val withdrawalFreeze: String = ""
)
data class AddressHash(var addressHash:String="")
data class AvailableAmount(var availableAmount:String="")
data class TransferAccount(var address:String="",var uCapital:UCapital)
data class UCapital(var id:String="",var acountHash:String="",var addressHash:String="",var balance:String="",var withdrawalFreeze:String="",var transactionFreeze:String="",var voteFreeze:String="",var miniingbalance:String="",var gradeGains:String="",var recommendGains:String="",var iconType:String="")
