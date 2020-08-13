package com.kopernik.ui.setting.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil

class AddContactViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val data = MutableLiveData<BaseResult<Any>>()

    fun addContact(map: Map<String, String>): MutableLiveData<BaseResult<Any>> {
        launchGo({
            data.value = homeRepository.addContact(map)
        })
        return data
    }
}