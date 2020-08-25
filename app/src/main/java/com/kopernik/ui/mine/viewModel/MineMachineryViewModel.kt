package com.kopernik.ui.mine.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.mine.entity.MineBean

class MineMachineryViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val getMachineList = SingleLiveEvent<BaseResult<MineBean>>()
    private val getMachine = SingleLiveEvent<BaseResult<Any>>()

    fun getMachineList(): SingleLiveEvent<BaseResult<MineBean>> {
        launchGo({
            getMachineList.value = homeRepository.getMachineList()
        },isShowDialog = false)
        return getMachineList
    }

    fun getMachine( map:Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            getMachine.value = homeRepository.getMachine(map)
        },isShowDialog = false)
        return getMachine
    }
}