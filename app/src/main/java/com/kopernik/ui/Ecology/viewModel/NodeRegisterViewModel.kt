package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

class NodeRegisterViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val checkNodeName = SingleLiveEvent<BaseResult<Any>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun checkNodeName(nodeName: String): MutableLiveData<BaseResult<Any>> {
        launchGo({
            checkNodeName.value = homeRepository.checkNodeName(nodeName)
        })
        return checkNodeName
    }
}
