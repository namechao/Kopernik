package com.kopernik.ui.Ecology.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.NodeLeaderBoardBean

class NodeLeaderBoardViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getRankNodeList = SingleLiveEvent<BaseResult<NodeLeaderBoardBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getRankNodeList( map: Map<String, String>): SingleLiveEvent<BaseResult<NodeLeaderBoardBean>> {
        launchGo({
            getRankNodeList.value = homeRepository.getRankNodeList(map)
        })
        return getRankNodeList
    }
}
