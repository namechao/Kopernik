package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.CheckRegisterInfo

class VoteBetViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getNodeList = MutableLiveData<BaseResult<CheckRegisterInfo>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun checkVoteInfo(): MutableLiveData<BaseResult<CheckRegisterInfo>> {
        launchGo({
            getNodeList.value = homeRepository.checkVoteInfo()
        })
        return getNodeList
    }
}
