package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.ReferendumDetailsBean

class ReferendumDetailsViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getReferendumDetails = SingleLiveEvent<BaseResult<ReferendumDetailsBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getReferendumDetails(url: String): MutableLiveData<BaseResult<ReferendumDetailsBean>> {
        launchGo({
            getReferendumDetails.value = homeRepository.getReferendumDetails(url)
        })
        return getReferendumDetails
    }
}
