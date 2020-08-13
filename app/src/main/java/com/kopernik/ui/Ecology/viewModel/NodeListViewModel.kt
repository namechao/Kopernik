package com.kopernik.ui.Ecology.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.NodeBean

class NodeListViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val getNodeList = MutableLiveData<BaseResult<NodeBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getNodeList(url: String, map: Map<String, String>): MutableLiveData<BaseResult<NodeBean>> {
        launchGo({
            getNodeList.value = homeRepository.getNodeList(url, map)
        })
        return getNodeList
    }
}
