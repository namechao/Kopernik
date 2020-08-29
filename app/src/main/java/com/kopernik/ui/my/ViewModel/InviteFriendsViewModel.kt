package com.kopernik.ui.my.ViewModel

import androidx.lifecycle.MutableLiveData
import com.aleyn.mvvm.base.BaseViewModel
import com.aleyn.mvvm.event.SingleLiveEvent
import com.pcl.mvvm.app.base.BaseResult
import com.kopernik.data.InjectorUtil
import com.kopernik.ui.login.bean.User
import com.kopernik.ui.my.bean.InviteFriendsEntity
import com.kopernik.ui.my.bean.InviteFriendsItem

class InviteFriendsViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val accountBean = SingleLiveEvent<BaseResult<InviteFriendsEntity>>()
    private val inviteFriendsSecond = SingleLiveEvent<BaseResult<InviteFriendsEntity>>()

    fun inviteFriends(map: Map<String, String>): SingleLiveEvent<BaseResult<InviteFriendsEntity>> {
        launchGo({
            accountBean.value = homeRepository.inviteFriends(map)
        },isShowDialog = false)
        return accountBean
    }
    fun inviteFriendsSecond(map: Map<String, String>): SingleLiveEvent<BaseResult<InviteFriendsEntity>> {
        launchGo({
            inviteFriendsSecond.value = homeRepository.inviteFriendsSecond(map)
        },isShowDialog = false)
        return inviteFriendsSecond
    }
}