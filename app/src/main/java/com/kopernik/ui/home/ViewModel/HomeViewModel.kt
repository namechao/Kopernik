package com.kopernik.ui.home.ViewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.kopernik.data.InjectorUtil

import com.kopernik.data.entity.HomeEntity

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }



    private val projectData = SingleLiveEvent<HomeEntity>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(): SingleLiveEvent<HomeEntity> {
        launchGo({

        })
        return projectData
    }
}
