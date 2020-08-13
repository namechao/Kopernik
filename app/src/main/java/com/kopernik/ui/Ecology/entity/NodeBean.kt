package com.kopernik.ui.Ecology.entity

class NodeBean {
    /**
     * pageNum : 1
     * pageSize : 10
     * total : 3
     * pages : 1
     * datas : [{"id":62,"name":"owner1","nodeHash":"8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772","website":null,"address":"8d8dc53ccbca95e94e3e65d02fc6e0b20c2c","selfBonded":"22739.31147668","totalBonded":"78226.84657220","jackpotBalance":"11077.95862232","missedBlocks":87074,"missedBlocksRate":"60.80","authoredBlocks":58072,"type":"TRUST","typeZH":"正式节点","typeEN":"Trustee","nodeStatus":"NORMAL","statusZH":"正常","statusEN":"Normal","voteWeightLastUpdate":0,"lastTotalVoteWeight":0,"blockAuthoringAddress":"8D9fc21a0514bc293be39a5b8dc88e18148d3d9fe082f6bfa6348f05f54e6e58","jackpotAddress":"8D6c5a65d38f63d0a4b0fc3f1a926c463b8d4647134daeead91588b53a9d8b33","trusteeId":1,"recommendNode":null,"imgurl":"https://dns-xut.oss-cn-hangzhou.aliyuncs.com/dns/1565753722244.png","vate":null,"issueGain":0,"formalNode":false,"gains":0,"effectiveVote":null},{"id":64,"name":"test002node","nodeHash":"8d6ad7c1c65dd6804212545769b98760f88df3267651dbc3beed89d18e846820","website":"","address":"8d8da61792590fa6ffc4ee31663fcacb80a7","selfBonded":"1000.00000000","totalBonded":"12427.12345678","jackpotBalance":"375.93270063","missedBlocks":92093,"missedBlocksRate":"61.78","authoredBlocks":58620,"type":"TRUST","typeZH":"正式节点","typeEN":"Trustee","nodeStatus":"NORMAL","statusZH":"正常","statusEN":"Normal","voteWeightLastUpdate":0,"lastTotalVoteWeight":0,"blockAuthoringAddress":"8D3999b662165af7464dc63bdaeb9dddfe8ddb0020c0aba0323ac7a0e90b5ae7","jackpotAddress":"8D86b6f37a2947662c88bb2a933dfe408d8d7d745fb15ae33d04eee64be7505c","trusteeId":1,"recommendNode":"8d07632358929d89db0310cfdae650ea8a8dd6edd8413fd3e5f22a538a138fdd","imgurl":"https://dns-xut.oss-cn-hangzhou.aliyuncs.com/dns/1565753722244.png","vate":null,"issueGain":0,"formalNode":false,"gains":0,"effectiveVote":null},{"id":63,"name":"test001node","nodeHash":"8d07632358929d89db0310cfdae650ea8a8dd6edd8413fd3e5f22a538a138fdd","website":"http://www.baidu.com","address":"8d8dd89ebdc0461383960ce465404b81d3cf","selfBonded":"1000.00000000","totalBonded":"10225.22035478","jackpotBalance":"48.06608418","missedBlocks":89156,"missedBlocksRate":"59.81","authoredBlocks":58463,"type":"TRUST","typeZH":"正式节点","typeEN":"Trustee","nodeStatus":"NORMAL","statusZH":"正常","statusEN":"Normal","voteWeightLastUpdate":0,"lastTotalVoteWeight":0,"blockAuthoringAddress":"8D3251aff2eb1e97361c50159be4ec91418d7ce8f5cc79d6dac6a70ea6e529f9","jackpotAddress":"8De4c427338d3fc4fd16bc68464d4304648dcd54631c30c0e2e616fe645c2187","trusteeId":2,"recommendNode":"8d7953dc298665f389037b077637278b698d4de1def0cb9dceef5192b9b1c772","imgurl":"https://dns-xut.oss-cn-hangzhou.aliyuncs.com/dns/1565753722244.png","vate":null,"issueGain":0,"formalNode":false,"gains":0,"effectiveVote":null}]
     */
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
         * totalBonded : 78226.84657220
         * jackpotBalance : 11077.95862232
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
         * vate : null
         * issueGain : 0
         * formalNode : false
         * gains : 0
         * effectiveVote : null
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
        var recommendNode: String? = null
        var imgurl: String? = null
        var vate: Any? = null
        var issueGain = 0
        var isFormalNode = false
        var gains = 0
        var effectiveVote: Any? = null
        var isSelfNode = false

    }
}