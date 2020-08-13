package com.kopernik.data.network

import com.kopernik.app.network.RetrofitClient
import com.kopernik.data.service.NodeService

class NodeNetWork {
    private val mService by lazy { RetrofitClient.getInstance().create(NodeService::class.java) }


    suspend fun getNodeList(url: String, map: Map<String, String>) = mService.getNodeList(url, map)
    suspend fun checkVoteInfo() = mService.checkVoteInfo()
    suspend fun getRegisterInfo() = mService.getRegisterInfo()
    suspend fun checkNodeName(nodeName: String) = mService.checkNodeName(nodeName)
    suspend fun getReferendumDetails(url: String) = mService.getReferendumDetails(url)
    suspend fun modifyNodeLogo(map: Map<String, String>) = mService.modifyNodeLogo(map)
    suspend fun getNodeLogo() = mService.getNodeLogo()
    suspend fun getVoteHistory(map: Map<String, String>) = mService.getVoteHistory(map)
    suspend fun getNodeCheckInfo(map: Map<String, String>) = mService.getNodeCheckInfo(map)
    suspend fun getRankNodeList(map: Map<String, String>) = mService.getRankNodeList(map)
    suspend fun getFreeRecord(map: Map<String, String>) = mService.getFreeRecord(map)
    suspend fun getReferendumList(map: Map<String, String>) = mService.getReferendumList(map)
    suspend fun getvotelist(map: Map<String, String>) = mService.getvotelist(map)
    suspend fun checkextract() = mService.checkextract()


    companion object {
        @Volatile
        private var netWork: NodeNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: NodeNetWork().also { netWork = it }
        }
    }

}