package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.*

class VoteOperateViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getNodeCheckInfo = SingleLiveEvent<BaseResult<VoteOperateCheckBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getNodeCheckInfo(map: Map<String, String>): MutableLiveData<BaseResult<VoteOperateCheckBean>> {
        launchGo({
            getNodeCheckInfo.value = homeRepository.getNodeCheckInfo(map)
        })
        return getNodeCheckInfo
    }
}
