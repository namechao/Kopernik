package com.kopernik.ui.setting.entity

class InviteBean {
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 96
         * name : Maxiaoliao
         * nodeHash : 8de30f578641adb3bd2fcb1f755a2f1ddd8d5083ce5303f4f52eefc2563cfd62
         * website : Http://www.baodu.com
         * address : 8d8dae388294d5a7a9538407e449fa2ad6b0
         * selfBonded : 100.00000000
         * totalBonded : 100.00000000
         * jackpotBalance : 0.00000000
         * missedBlocks : 0
         * missedBlocksRate : 0.00
         * authoredBlocks : 0
         * type : NORMAL
         * typeZH : 正常
         * typeEN : normal
         * nodeStatus : NORMAL
         * statusZH : 正常
         * statusEN : Normal
         * voteWeightLastUpdate : 0
         * lastTotalVoteWeight : 0
         * blockAuthoringAddress : 8De92334a71cb706f74427b3f632e1c5678dac950c05cc7807bf4e5b01dda30b
         * jackpotAddress : 8D556026c8367b3f95fec689a250914dc58dd46a1d4a4f3172475e78487e7380
         * trusteeId : 2
         * recommendNode : 8d33b3a7bd96b86f2a2896b9943236a6368d5ae1e11a4f599bae3e84a6337ee2
         * imgurl : https://dns-xut.oss-cn-hangzhou.aliyuncs.com/dns/1565752274815.png
         * vate : null
         * issueGain : 0
         * formalNode : false
         * gains : null
         * effectiveVote : null
         */
        var id = 0
        var name: String? = null
        var nodeHash: String? = null
        var website: String? = null
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
        var recommendNode: String? = null
        var imgurl: String? = null
        var vate: Any? = null
        var issueGain = 0
        var isFormalNode = false
        var gains = 0.0
        var effectiveVote: Any? = null
        var acountHash: String? = null
        var acountLabel: String? = null

    }
}