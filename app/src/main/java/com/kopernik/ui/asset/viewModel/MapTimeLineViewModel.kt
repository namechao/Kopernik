package com.kopernik.ui.asset.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.asset.entity.ExtractBean

class MapTimeLineViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getAssetRepository() }
    private val timeLineBean = MutableLiveData<BaseResult<TimeLineBean>>()
    private val getGains = MutableLiveData<BaseResult<ExtractBean>>()
    private val saveGains = MutableLiveData<BaseResult<Any>>()
    fun getUnMapRecord(params: Map<String, String>): MutableLiveData<BaseResult<TimeLineBean>> {
        launchGo({
            timeLineBean.value = homeRepository.getUnMapRecord(params)
        }, {}, isShowDialog = false)
        return timeLineBean
    }
}