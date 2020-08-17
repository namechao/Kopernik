package com.kopernik.ui.my.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.ui.my.viewHoloder.InviteFriendsHolder


class InviteFriendsAdapter(
    data: List<String>
) : BaseQuickAdapter<String, InviteFriendsHolder>(R.layout.item_invite_friends,data) {
    override fun convert(holder: InviteFriendsHolder, item: String?) {

    }


}