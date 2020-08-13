package com.kopernik.ui.Ecology.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

import com.kopernik.ui.Ecology.entity.NodeLogoListBean

class ChooseNodeLogoViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val modifyNodeLogo = SingleLiveEvent<BaseResult<Any>>()
    private val getNodeLogo = SingleLiveEvent<BaseResult<List<NodeLogoListBean>>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun modifyNodeLogo(map: Map<String, String>): SingleLiveEvent<BaseResult<Any>> {
        launchGo({
            modifyNodeLogo.value = homeRepository.modifyNodeLogo(map)
        })
        return modifyNodeLogo
    }

    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getNodeLogo(): SingleLiveEvent<BaseResult<List<NodeLogoListBean>>> {
        launchGo({
            getNodeLogo.value = homeRepository.getNodeLogo()
        })
        return getNodeLogo
    }
}
