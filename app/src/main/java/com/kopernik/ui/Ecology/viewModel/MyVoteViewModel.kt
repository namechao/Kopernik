package com.kopernik.ui.Ecology.viewModel

import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.Ecology.entity.MyVoteBean

import com.kopernik.ui.asset.entity.OneKeyExtractSignatureBean

class MyVoteViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getNodeRepository() }


    private val checkextract = SingleLiveEvent<BaseResult<OneKeyExtractSignatureBean>>()
    private val getvotelist = SingleLiveEvent<BaseResult<MyVoteBean>>()


    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun checkextract(): SingleLiveEvent<BaseResult<OneKeyExtractSignatureBean>> {
        launchGo({
            checkextract.value = homeRepository.checkextract()
        })
        return checkextract
    }

    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getvotelist(map: Map<String, String>): SingleLiveEvent<BaseResult<MyVoteBean>> {
        launchGo({
            getvotelist.value = homeRepository.getvotelist(map)
        })
        return getvotelist
    }
}
