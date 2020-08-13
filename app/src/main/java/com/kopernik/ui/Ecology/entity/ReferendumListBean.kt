package com.kopernik.ui.Ecology.entity

class ReferendumListBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * total : 2
     * pages : 1
     * datas : [{"id":2,"title":"社区公投1号提案","threshold":234,"startHeight":111,"endHeight":11345,"opposingAddress":"8d8df77d82de8dafe75b739f23ca8cf3e4b6","approvalAddress":"8d8db5404e57c487730c520fc8ed5d4145b5","memo":null,"createTime":1568973837000,"status":"0","content":"社区公投1号提案社区公投1号提案社区公投1号提案","rule":"社区公投1号提案社区公投1号提案社区公投1号提案社区公投1号提案","participationMode":"社区公投1号提案社区公投1号提案社区公投1号提案社区公投1号提案","favorAmount":null,"oppositionAmount":null,"zPercentage":null,"fPercentage":null},{"id":3,"title":"2号公投提案","threshold":3333,"startHeight":123,"endHeight":34324235,"opposingAddress":"8d8df3f8a8ac96d8a78d3b2fa2500b36a62a","approvalAddress":"8d8d69ff0b0e87c53b8456d7d9a69cbfcc08","memo":null,"createTime":1568975143000,"status":"0","content":"2号公投提案2号公投提案2号公投提案2号公投提案2号公投提案","rule":"2号公投提案2号公投提案2号公投提案","participationMode":"2号公投提案2号公投提案2号公投提案2号公投提案","favorAmount":null,"oppositionAmount":null,"zPercentage":null,"fPercentage":null}]
     */
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 2
         * title : 社区公投1号提案
         * threshold : 234.0
         * startHeight : 111
         * endHeight : 11345
         * opposingAddress : 8d8df77d82de8dafe75b739f23ca8cf3e4b6
         * approvalAddress : 8d8db5404e57c487730c520fc8ed5d4145b5
         * memo : null
         * createTime : 1568973837000
         * status : 0
         * content : 社区公投1号提案社区公投1号提案社区公投1号提案
         * rule : 社区公投1号提案社区公投1号提案社区公投1号提案社区公投1号提案
         * participationMode : 社区公投1号提案社区公投1号提案社区公投1号提案社区公投1号提案
         * favorAmount : null
         * oppositionAmount : null
         * zPercentage : null
         * fPercentage : null
         */
        var id = 0
        var title: String? = null
        var threshold = 0.0
        var startHeight = 0
        var endHeight = 0
        var opposingAddress: String? = null
        var approvalAddress: String? = null
        var memo: Any? = null
        var createTime: Long = 0
        var status: String? = null
        var content: String? = null
        var rule: String? = null
        var participationMode: String? = null
        var favorAmount: Any? = null
        var oppositionAmount: Any? = null
        var zPercentage: Any? = null
        var fPercentage: Any? = null

    }
}