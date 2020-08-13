package com.kopernik.ui.asset.entity

class CrossChainTxBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * total : 1
     * pages : 1
     * datas : [{"id":58,"type":1,"flag":0,"btcvalue":"55.00000000","addresHash":null,"btcHeight":null,"btcHash":null,"btcTime":null,"nonce":null,"chainNumber":null,"acountHash":"8d8de123264d7afcd2cf04597e2d5314ccb4","createitme":1571061009000,"memo":null,"qdnodeName":null,"btcJyhash":"8Df0b92e84bb11f69788a494461a0f03598d91f1d138b4029ad3d85662f8d93a","withdrawalAddress":"0xf1e6ba0b02533b0da07449059f23eceb57532266","btcAddress":null,"iconAddress":"0xFDB970A269D0b2b1cf3e2974aa549fb6CEeEfB95","iconType":"ETH","self":false}]
     */
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 58
         * type : 1
         * flag : 0
         * btcvalue : 55.00000000
         * addresHash : null
         * btcHeight : null
         * btcHash : null
         * btcTime : null
         * nonce : null
         * chainNumber : null
         * acountHash : 8d8de123264d7afcd2cf04597e2d5314ccb4
         * createitme : 1571061009000
         * memo : null
         * qdnodeName : null
         * btcJyhash : 8Df0b92e84bb11f69788a494461a0f03598d91f1d138b4029ad3d85662f8d93a
         * withdrawalAddress : 0xf1e6ba0b02533b0da07449059f23eceb57532266
         * btcAddress : null
         * iconAddress : 0xFDB970A269D0b2b1cf3e2974aa549fb6CEeEfB95
         * iconType : ETH
         * self : false
         */
        var id = 0
        var type = 0
        var flag = 0
        var btcvalue: String? = null
        var addresHash: Any? = null
        var btcHeight: Any? = null
        var btcHash: Any? = null
        var btcTime: Any? = null
        var nonce: Any? = null
        var chainNumber: Any? = null
        var acountHash: String? = null
        var createitme: Long = 0
        var memo: Any? = null
        var qdnodeName: Any? = null
        var btcJyhash: String? = null
        var withdrawalAddress: String? = null
        var btcAddress: Any? = null
        var iconAddress: String? = null
        var iconType: String? = null
        var isSelf = false

    }
}