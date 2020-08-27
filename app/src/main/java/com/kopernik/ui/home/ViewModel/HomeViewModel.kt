package com.kopernik.ui.home.ViewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.home.Entity.GetUtkEntity

import com.kopernik.ui.home.Entity.HomeEntity
import com.kopernik.ui.home.Entity.NoticeEntity
import com.pcl.mvvm.app.base.BaseResult

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }



    private val homeEntity = SingleLiveEvent<BaseResult<HomeEntity>>()
    private val noticeEntity = SingleLiveEvent<BaseResult<NoticeEntity>>()
    private val getUtkStatus = SingleLiveEvent<BaseResult<GetUtkEntity>>()
    private val getUtk = SingleLiveEvent<BaseResult<Any>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(): SingleLiveEvent<BaseResult<HomeEntity>> {
        launchGo({
            homeEntity.value=homeRepository.getHomeList()
        },isShowDialog = false)
        return homeEntity
    }
    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getNotice(): SingleLiveEvent<BaseResult<NoticeEntity>> {
        launchGo({
            noticeEntity.value=homeRepository.getNotice()
        },isShowDialog = false)
        return noticeEntity
    }
    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getUtkStatus(): SingleLiveEvent<BaseResult<GetUtkEntity>> {
        launchGo({
            getUtkStatus.value=homeRepository.getUtkStatus()
        },isShowDialog = false)
        return getUtkStatus
    }
    fun getUtk(map: Map<String, String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            getUtk.value=homeRepository.getUtk(map)
        },isShowDialog = false)
        return getUtk
    }
}
