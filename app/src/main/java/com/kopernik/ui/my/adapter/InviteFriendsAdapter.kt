package com.kopernik.ui.my.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.login.bean.User
import com.kopernik.ui.my.bean.InviteFriendsItem
import com.kopernik.ui.my.viewHoloder.InviteFriendsHolder


class InviteFriendsAdapter(
    data: List<InviteFriendsItem>
) : BaseQuickAdapter<InviteFriendsItem, InviteFriendsHolder>(R.layout.item_invite_friends,data) {
    override fun convert(holder: InviteFriendsHolder, item: InviteFriendsItem?) {
        holder.inviteUid?.text=""+item?.uid
        holder.inviteTime?.text=""+TimeUtils.TimeStampYearToData(item?.createTime)
        when(item?.level){
            0 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade0)
            }
            1 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade1)
            }
            2 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade2)
            }
            3 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade3)
            }
            4 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade4)
            }
            5 ->{
                 holder.inviteGrade?.text=mContext.getString(R.string.invite_grade5)
            }

        }


    }


}