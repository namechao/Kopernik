package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.CheckRegisterInfo

class NodeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getRegisterInfo = MutableLiveData<BaseResult<CheckRegisterInfo>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getRegisterInfo(): MutableLiveData<BaseResult<CheckRegisterInfo>> {
        launchGo({
            getRegisterInfo.value = homeRepository.getRegisterInfo()
        })
        return getRegisterInfo
    }
}
