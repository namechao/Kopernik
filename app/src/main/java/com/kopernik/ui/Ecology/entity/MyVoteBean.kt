package com.kopernik.ui.Ecology.entity

class MyVoteBean {
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? = null

    class DatasBean {
        /**
         * id : 62
         * name : owner1
         * nodeHash : 8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772
         * website : null
         * address : 8d8dc53ccbca95e94e3e65d02fc6e0b20c2c
         * selfBonded : 22739.31147668
         * totalBonded : 79405.84657220
         * jackpotBalance : 11118.95018655
         * missedBlocks : 87074
         * missedBlocksRate : 60.80
         * authoredBlocks : 58072
         * type : TRUST
         * typeZH : 正式节点
         * typeEN : Trustee
         * nodeStatus : NORMAL
         * statusZH : 正常
         * statusEN : Normal
         * voteWeightLastUpdate : 0
         * lastTotalVoteWeight : 0
         * blockAuthoringAddress : 8D9fc21a0514bc293be39a5b8dc88e18148d3d9fe082f6bfa6348f05f54e6e58
         * jackpotAddress : 8D6c5a65d38f63d0a4b0fc3f1a926c463b8d4647134daeead91588b53a9d8b33
         * trusteeId : 1
         * recommendNode : null
         * imgurl : https://dns-xut.oss-cn-hangzhou.aliyuncs.com/dns/1565753722244.png
         * vate : {"nodeHash":"8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772","accountHash":"8d8dc8d78d3bcf7caef7c5a8d7d2484b486f","myNominations":"1.00000000","unfreezeReserved":"0.00000000","unclaimed":"0.00000000","issueGain":0,"recommendGain":0,"accountRecommendGain":0}
         * issueGain : 0
         * formalNode : false
         * gains : 0
         * effectiveVote : 79405.84657220
         */
        var id = 0
        var name: String? = null
        var nodeHash: String? = null
        var website: Any? = null
        var address: String? = null
        var selfBonded: String? = null
        var totalBonded: String? = null
        var jackpotBalance: String? = null
        var missedBlocks = 0
        var missedBlocksRate: String? = null
        var authoredBlocks = 0
        var type: String? = null
        var typeZH: String? = null
        var typeEN: String? = null
        var nodeStatus: String? = null
        var statusZH: String? = null
        var statusEN: String? = null
        var voteWeightLastUpdate = 0
        var lastTotalVoteWeight = 0
        var blockAuthoringAddress: String? = null
        var jackpotAddress: String? = null
        var trusteeId = 0
        var recommendNode: Any? = null
        var imgurl: String? = null
        var vate: VateBean? = null
        var issueGain = 0
        var isFormalNode = false
        var gains = 0
        var effectiveVote: String? = null
        var isSelfNode = false

        class VateBean {
            /**
             * nodeHash : 8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772
             * accountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
             * myNominations : 1.00000000
             * unfreezeReserved : 0.00000000
             * unclaimed : 0.00000000
             * issueGain : 0
             * recommendGain : 0
             * accountRecommendGain : 0
             */
            var nodeHash: String? = null
            var accountHash: String? = null
            var myNominations: String? = null
            var unfreezeReserved: String? = null
            var unclaimed: String? = null
            var issueGain = 0
            var recommendGain = 0
            var accountRecommendGain = 0

        }
    }
}