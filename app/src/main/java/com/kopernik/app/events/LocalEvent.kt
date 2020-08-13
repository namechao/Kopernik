package com.kopernik.app.events

class LocalEvent<T> {
    constructor() {}
    constructor(status_type: String?) {
        this.status_type = status_type
    }

    constructor(status_type: String?, data: T) {
        this.status_type = status_type
        this.data = data
    }

    constructor(status_type: String?, data: T, data2: T) {
        this.status_type = status_type
        this.data = data
        this.data2 = data2
    }

    //模型
    var data //数据
            : T? = null
    var status_type //类型
            : String? = null
    var data2: T? = null

    companion object {
        //信息类型
        const val closeSplashAc = "closeSplashAc"
        const val openSetting = "openSetting"
        const val restartApp = "restartApp"
        const val reloadMyVoteList = "reloadMyVoteList"
        const val reloadAsset = "reloadAsset"
        const val switchFragment = "switchFragment"
        const val reloadVoteList = "reloadVoteList"
        const val reLogin = "reLogin"
        const val reLoadContact = "reLoadContact"
        const val reLoadWeb = "reLoadWebw"
        const val switchNodeFragment = "switchNodeFragment"
        const val referendumVote = "referendumVote"
        const val inviteIntegral = "inviteIntegral"
        const val applySkin = "applySkin"
    }
}