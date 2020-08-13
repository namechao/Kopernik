package com.kopernik.data.repository

import com.aleyn.mvvm.base.BaseModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.network.NodeNetWork
import com.kopernik.ui.Ecology.entity.*
import com.kopernik.ui.asset.entity.*

class NodeRepository private constructor(private val newWork: NodeNetWork) : BaseModel() {

    suspend fun getNodeList(url: String, map: Map<String, String>): BaseResult<NodeBean> {
        return newWork.getNodeList(url, map)
    }
    suspend fun getRankNodeList(map: Map<String, String>): BaseResult<NodeLeaderBoardBean> {
        return newWork.getRankNodeList(map)
    }
    suspend fun getReferendumList(map: Map<String, String>): BaseResult<ReferendumListBean> {
        return newWork.getReferendumList(map)
    }

    suspend fun checkVoteInfo(): BaseResult<CheckRegisterInfo> {
        return newWork.checkVoteInfo()
    }
    suspend fun getReferendumDetails(url: String): BaseResult<ReferendumDetailsBean> {
        return newWork.getReferendumDetails(url)
    }

    suspend fun getRegisterInfo(): BaseResult<CheckRegisterInfo> {
        return newWork.getRegisterInfo()
    }

    suspend fun checkNodeName(nodeName: String): BaseResult<Any> {
        return newWork.checkNodeName(nodeName)
    }

    suspend fun modifyNodeLogo(map: Map<String, String>): BaseResult<Any> {
        return newWork.modifyNodeLogo(map)
    }

    suspend fun getNodeLogo(): BaseResult<List<NodeLogoListBean>> {
        return newWork.getNodeLogo()
    }

    suspend fun getVoteHistory(map: Map<String, String>): BaseResult<VoteHistoryBean> {
        return newWork.getVoteHistory(map)
    }
    suspend fun getvotelist(map: Map<String, String>): BaseResult<MyVoteBean> {
        return newWork.getvotelist(map)
    }
    suspend fun checkextract(): BaseResult<OneKeyExtractSignatureBean> {
        return newWork.checkextract()
    }
    suspend fun getNodeCheckInfo(map: Map<String, String>): BaseResult<VoteOperateCheckBean> {
        return newWork.getNodeCheckInfo(map)
    }
    suspend fun getFreeRecord(map: Map<String, String>): BaseResult<TimeLineBean> {
        return newWork.getFreeRecord(map)
    }


    companion object {
        private var INSTANCE: NodeRepository? = null

        fun getInstance(netWork: NodeNetWork) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NodeRepository(netWork).also { INSTANCE = it }
        }
    }
}

