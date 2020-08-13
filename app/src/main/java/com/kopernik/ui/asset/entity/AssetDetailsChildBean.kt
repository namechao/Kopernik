package com.kopernik.ui.asset.entity

class AssetDetailsChildBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * total : 1
     * pages : 1
     * datas : [{"id":1328976,"operaterType":"ROLL_IN","operaterTypeZn":"转入","operaterTypeEn":"roll-in","acountHash":"8d8dc8d78d3bcf7caef7c5a8d7d2484b486f","assetsType":"UYT","amount":"100000.00000000","blockid":null,"nodeid":null,"createTime":1566204177000,"extrinsicHash":"8D748dd434165ee88ca8ca6856d3521fcb8d629f7fbb763e4afd37a1eec9a471","investAddress":"8d8d193f4f7fa2744b085b6c9355e9fb87de","dayFree":null,"unlocktime":null,"year":null}]
     */
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? = null

    class DatasBean {
        /**
         * id : 1328976
         * operaterType : ROLL_IN
         * operaterTypeZn : 转入
         * operaterTypeEn : roll-in
         * acountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
         * assetsType : UYT
         * amount : 100000.00000000
         * blockid : null
         * nodeid : null
         * createTime : 1566204177000
         * extrinsicHash : 8D748dd434165ee88ca8ca6856d3521fcb8d629f7fbb763e4afd37a1eec9a471
         * investAddress : 8d8d193f4f7fa2744b085b6c9355e9fb87de
         * dayFree : null
         * unlocktime : null
         * year : null
         */
        var id = 0
        var operaterType: String? = null
        var operaterTypeZn: String? = null
        var operaterTypeEn: String? = null
        var acountHash: String? = null
        var assetsType: String? = null
        var amount: String? = null
        var blockid: Any? = null
        var nodeid: Any? = null
        var createTime: Long = 0
        var extrinsicHash: String? = null
        var investAddress: String? = null
        var dayFree: Any? = null
        var unlocktime: Any? = null
        var year: Any? = null
        var memo: String? = null

    }
}