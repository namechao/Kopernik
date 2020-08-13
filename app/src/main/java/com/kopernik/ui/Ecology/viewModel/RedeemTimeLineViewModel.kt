package com.kopernik.ui.Ecology.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.*

class RedeemTimeLineViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getFreeRecord = SingleLiveEvent<BaseResult<TimeLineBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getFreeRecord(map: Map<String, String>): SingleLiveEvent<BaseResult<TimeLineBean>> {
        launchGo({
            getFreeRecord.value = homeRepository.getFreeRecord(map)
        })
        return getFreeRecord
    }
}
