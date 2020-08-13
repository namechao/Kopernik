package com.kopernik.ui.Ecology.entity

class TimeLineBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * total : 2
     * pages : 1
     * datas : [{"id":355,"nodeHash":"8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772","accountHash":"8d8dc8d78d3bcf7caef7c5a8d7d2484b486f","myNominations":"2.00000000","unfreezeReserved":"0.00000000","unclaimed":"0.00000000","time":1566113935000,"type":"VOTE","operaterTypeZn":"投票","operaterTypeEn":"vote","name":"owner1","dayFree":"6天","unlocktime":"2019-08-24 15:38:55","year":"2019"},{"id":351,"nodeHash":"8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772","accountHash":"8d8dc8d78d3bcf7caef7c5a8d7d2484b486f","myNominations":"1.00000000","unfreezeReserved":"0.00000000","unclaimed":"0.00000000","time":1566112323000,"type":"VOTE","operaterTypeZn":"投票","operaterTypeEn":"vote","name":"owner1","dayFree":"6天","unlocktime":"2019-08-24 15:12:03","year":"2019"}]
     */
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 355
         * nodeHash : 8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772
         * accountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
         * myNominations : 2.00000000
         * unfreezeReserved : 0.00000000
         * unclaimed : 0.00000000
         * time : 1566113935000
         * type : VOTE
         * operaterTypeZn : 投票
         * operaterTypeEn : vote
         * name : owner1
         * dayFree : 6天
         * unlocktime : 2019-08-24 15:38:55
         * year : 2019
         */
        var id = 0
        var nodeHash: String? = null
        var accountHash: String? = null
        var myNominations: String? = null
        var unfreezeReserved: String? = null
        var unclaimed: String? = null
        var time: Long = 0
        var type: String? = null
        var operaterTypeZn: String? = null
        var operaterTypeEn: String? = null
        var name: String? = null
        var dayFree: String? = null
        var unlocktime: String? = null
        var year: String? = null
        var assetsType = "UYT"
        var amount: String? = null
        var createTime: String? = null

    }
}