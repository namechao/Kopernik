package com.kopernik.ui.Ecology.entity

class ReferendumDetailsBean {
    var favorAmount = 0.0
    var oppositionAmount = 0.0
    var uReferendum: UReferendumBean? = null
    var config: ConfigBean? = null
    var detailList: List<DetailListBean>? = null
    var checkVote:Int?=null // 0  未投票  1 赞成 2反对 = 0

    class UReferendumBean {
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

    class ConfigBean {
        /**
         * id : null
         * type : Referendum
         * typeZH : 公投
         * typeEN : Referendum
         * low : 5.00000000
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

    class DetailListBean {
        /**
         * id : 2
         * acountHash : 8d8dc08c009ee48eff9adee4a6207e31caff
         * dnsSycount : 34781.69802374
         * referendumOpinion : 1
         * opposingAddress : null
         * approvalAddress : 8d8db5404e57c487730c520fc8ed5d4145b5
         * uid : 2
         */
        var id = 0
        var acountHash: String? = null
        var dnsSycount = 0.0
        var referendumOpinion = 0
        var opposingAddress: Any? = null
        var approvalAddress: String? = null
        var uid = 0

    }
}