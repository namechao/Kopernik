package com.kopernik.ui.Ecology.entity

class NodeLeaderBoardBean {
    var pageNum = 0
    var pageSize = 0
    var total = 0
    var pages = 0
    var datas: List<DatasBean>? =
        null

    class DatasBean {
        /**
         * id : 62
         * name : owner1
         * nodeHash : 8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772
         * website : null
         * address : 8d8dc53ccbca95e94e3e65d02fc6e0b20c2c
         * selfBonded : 22739.31147668
         * totalBonded : null
         * jackpotBalance : null
         * missedBlocks : null
         * missedBlocksRate : null
         * authoredBlocks : null
         * type : null
         * typeZH : null
         * typeEN : null
         * nodeStatus : null
         * statusZH : null
         * statusEN : null
         * voteWeightLastUpdate : null
         * lastTotalVoteWeight : null
         * blockAuthoringAddress : null
         * jackpotAddress : null
         * trusteeId : null
         * recommendNode : null
         * imgurl : null
         * vate : null
         * issueGain : 0
         * formalNode : false
         * acountCount : 0
         * btcMiniingbalance : null
         * ethMiniingbalance : null
         * usdtMiniingbalance : null
         * score : 0
         * superNode : 1
         * nodePacket : null
         * voteFrozen : null
         * mappingFrozen : null
         * time : null
         * recommendCount : 0
         * htMiniingbalance : null
         * countMiniingbalance : null
         * gains : 0
         * effectiveVote : null
         * selfNode : false
         */
        var id = 0
        var name: String? = null
        var nodeHash: String? = null
        var website: Any? = null
        var address: String? = null
        var selfBonded: String? = null
        var totalBonded: Any? = null
        var jackpotBalance: Any? = null
        var missedBlocks: Any? = null
        var missedBlocksRate: Any? = null
        var authoredBlocks: Any? = null
        var type: Any? = null
        var typeZH: Any? = null
        var typeEN: Any? = null
        var nodeStatus: Any? = null
        var statusZH: Any? = null
        var statusEN: Any? = null
        var voteWeightLastUpdate: Any? = null
        var lastTotalVoteWeight: Any? = null
        var blockAuthoringAddress: Any? = null
        var jackpotAddress: Any? = null
        var trusteeId: Any? = null
        var recommendNode: String? = null
        var imgurl: Any? = null
        var vate: Any? = null
        var issueGain = 0
        var isFormalNode = false
        var acountCount = 0
        var btcMiniingbalance: Any? = null
        var ethMiniingbalance: Any? = null
        var usdtMiniingbalance: Any? = null
        var score = 0
        var superNode = 0
        var nodePacket: Any? = null
        var voteFrozen: Any? = null
        var mappingFrozen: Any? = null
        var time: Any? = null
        var recommendCount = 0
        var htMiniingbalance: Any? = null
        var countMiniingbalance: Any? = null
        var gains = 0
        var effectiveVote: Any? = null
        var isSelfNode = false

    }
}