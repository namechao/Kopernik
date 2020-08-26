package com.kopernik.ui.mine.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.mine.entity.MineBean
import com.kopernik.ui.mine.entity.OtherMineBean

class MineMachineryViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val getMachineList = SingleLiveEvent<BaseResult<MineBean>>()
    private val getMachine1= SingleLiveEvent<BaseResult<OtherMineBean>>()
    private val getMachine2 = SingleLiveEvent<BaseResult<OtherMineBean>>()
    private val buyMineMachine = SingleLiveEvent<BaseResult<Any>>()

    fun getMachineList(): SingleLiveEvent<BaseResult<MineBean>> {
        launchGo({
            getMachineList.value = homeRepository.getMachineList()
        },isShowDialog = false)
        return getMachineList
    }

    fun getMachine1( map:Map<String,String>): SingleLiveEvent<BaseResult<OtherMineBean>> {
        launchGo({
            getMachine1.value = homeRepository.getMachine(map)
        },isShowDialog = false)
        return getMachine1
    }
    fun getMachine2( map:Map<String,String>): SingleLiveEvent<BaseResult<OtherMineBean>> {
        launchGo({
            getMachine2.value = homeRepository.getMachine(map)
        },isShowDialog = false)
        return getMachine2
    }
    fun buyMineMachine( map:Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            buyMineMachine.value = homeRepository.buyMineMachine(map)
        },isShowDialog = true)
        return buyMineMachine
    }
    fun checkTradePassword( map:Map<String,String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            buyMineMachine.value = homeRepository.changeTradePsw(map)
        },isShowDialog = true)
        return buyMineMachine
    }
}