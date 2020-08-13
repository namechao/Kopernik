package com.kopernik.ui.setting.viewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.setting.entity.ContactBean

class ContactsViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = MutableLiveData<BaseResult<ContactBean>>()
    private val delContact = MutableLiveData<BaseResult<Any>>()

    //获取联系人列表
    fun getContacts(): MutableLiveData<BaseResult<ContactBean>> {
        launchGo({
            accountBean.value = homeRepository.getContacts()
        })
        return accountBean
    }

    //删除联系人
    fun delContact(id: Int): MutableLiveData<BaseResult<Any>> {
        launchGo({
            delContact.value = homeRepository.delContact(id)
        })
        return delContact
    }
}