package com.kopernik.ui.asset.entity

class AssetOptBean {
    var btcWithdrawable: String? = null
    var ethWithdrawable: String? = null
    var usdtWithdrawable: String? = null
    var htWithdrawable: String? = null
    var mappableNumber: String? = null
    var config: ConfigBean? = null
    var uCapital: UCapitalBean? = null

    fun getuCapital(): UCapitalBean? {
        return uCapital
    }

    fun setuCapital(uCapital: UCapitalBean?) {
        this.uCapital = uCapital
    }

    class ConfigBean {
        /**
         * id : null
         * type : ROLL_OUT
         * typeZH : 转出
         * typeEN : roll-out
         * low : 1.00000000
         * height : 10.00000000
         * remark : null
         * creater : null
         * createTime : null
         * updater : null
         * updateTime : null
         */
        var id: Any? = null
        var type: String? = null
        var typeZH: String? = null
        var typeEN: String? = null
        var low: String? = null
        var height: String? = null
        var remark: Any? = null
        var creater: Any? = null
        var createTime: Any? = null
        var updater: Any? = null
        var updateTime: Any? = null

    }

    class UCapitalBean {
        /**
         * id : 131
         * bitcoinCount : 1000000.00000000
         * bitcoinKycount : 1000000.00000000
         * bitcoinTxfreeze : 0.00000000
         * bitcoinJyfreeze : 0.00000000
         * dnsCount : 3651555.00000000
         * dnsSycount : 3651418.00000000
         * dnsVotefreeze : 137.00000000
         * dnsRmfreeze : 0.00000000
         * dnsTcfreeze : 0.00000000
         * acountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
         * addressHash : 17r7uJHnD7mZUx4VaT4HwJJZDAHJ9FRzmM
         * transactionAmount : null
         * ethCount : 1000000.00000000
         * ethKycount : 1000000.00000000
         * ethTxfreeze : 0.00000000
         * ethJyfreeze : 0.00000000
         * ethAddresshash : 0x1B4308372a17DA86edd2CdB0D4fAf668547eF99F
         * btcMiniingbalance : 0.00000000
         * ethMiniingbalance : 0.00000000
         * usdtCount : 1000000.00000000
         * usdtKycount : 1000000.00000000
         * usdtTxfreeze : 0.00000000
         * usdtJyfreeze : 0.00000000
         * usdtMiniingbalance : 0.00000000
         * usdtAddresshash : 0xfA35dB383744c5E426E105a483aC921F4f231415
         * issueGain : 0
         * issueGainFinal : 0
         * recommendGain : 0
         */
        var id = 0
        var bitcoinCount: String? = null
        var bitcoinKycount: String? = null
        var bitcoinTxfreeze: String? = null
        var bitcoinJyfreeze: String? = null
        var dnsCount: String? = null
        var dnsSycount: String? = null
        var dnsVotefreeze: String? = null
        var dnsRmfreeze: String? = null
        var dnsTcfreeze: String? = null
        var acountHash: String? = null
        var addressHash: String? = null
        var transactionAmount: Any? = null
        var ethCount: String? = null
        var ethKycount: String? = null
        var ethTxfreeze: String? = null
        var ethJyfreeze: String? = null
        var ethAddresshash: String? = null
        var btcMiniingbalance: String? = null
        var ethMiniingbalance: String? = null
        var usdtCount: String? = null
        var usdtKycount: String? = null
        var usdtTxfreeze: String? = null
        var usdtJyfreeze: String? = null
        var usdtMiniingbalance: String? = null
        var usdtAddresshash: String? = null
        var issueGain = 0
        var issueGainFinal = 0
        var recommendGain = 0
        var htCount: String? = null
        var htKycount: String? = null
        var htTxfreeze: String? = null
        var htJyfreeze: String? = null
        var htMiniingbalance: String? = null
        var htAddresshash: String? = null

    }
}