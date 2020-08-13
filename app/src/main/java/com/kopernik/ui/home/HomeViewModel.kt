package com.kopernik.ui.home

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.kopernik.data.InjectorUtil

import com.kopernik.data.entity.HomeEntity

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }



    private val projectData = MutableLiveData<HomeEntity>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(page: Int, refresh: Boolean = false): MutableLiveData<HomeEntity> {
        launchGo({

        })
        return projectData
    }
}
