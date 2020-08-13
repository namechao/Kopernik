package com.kopernik.ui.Ecology.entity

class VoteHistoryBean {
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 356
         * nodeHash : 8d7b24847433edf571886f334d3f27a4618d545dc88cfa225959cc5776d525f4
         * accountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
         * myNominations : 100.00000000
         * unfreezeReserved : 0.00000000
         * unclaimed : 0.00000000
         * time : 1566113979000
         * type : VOTE
         * operaterTypeZn : 投票
         * operaterTypeEn : vote
         * name : kooTest
         * dayFree : null
         * unlocktime : null
         * year : null
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
        var dayFree: Any? = null
        var unlocktime: Any? = null
        var year: Any? = null

    }
}