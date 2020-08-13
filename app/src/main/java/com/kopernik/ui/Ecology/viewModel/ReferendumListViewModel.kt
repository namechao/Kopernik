package com.kopernik.ui.Ecology.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.ReferendumListBean

class ReferendumListViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getReferendumList = SingleLiveEvent<BaseResult<ReferendumListBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getReferendumList( map: Map<String, String>): SingleLiveEvent<BaseResult<ReferendumListBean>> {
        launchGo({
            getReferendumList.value = homeRepository.getReferendumList(map)
        })
        return getReferendumList
    }
}
