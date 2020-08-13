package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.VoteHistoryBean

class MyVoteHistoryViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getVoteHistory = SingleLiveEvent<BaseResult<VoteHistoryBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getVoteHistory(map: Map<String, String>): MutableLiveData<BaseResult<VoteHistoryBean>> {
        launchGo({
            getVoteHistory.value = homeRepository.getVoteHistory(map)
        })
        return getVoteHistory
    }
}
