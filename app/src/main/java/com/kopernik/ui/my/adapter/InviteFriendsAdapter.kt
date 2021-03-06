package com.kopernik.ui.my.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.login.bean.User
import com.kopernik.ui.my.bean.InviteFriendsItem
import com.kopernik.ui.my.viewHoloder.InviteFriendsHolder


class InviteFriendsAdapter(
    data: List<InviteFriendsItem>
) : BaseQuickAdapter<InviteFriendsItem, InviteFriendsHolder>(R.layout.item_invite_friends,data) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: InviteFriendsHolder, item: InviteFriendsItem?) {
        holder.inviteUid?.text=""+item?.uid
        when(item?.type){
            1->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type1)
            }
            2->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type2)
            }
            3->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type3)
            }
            4->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type4)
            }
            5->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type5)
            }
            6->{
                holder.inviteMineMac?.text=mContext.getString(R.string.mining_machine_type6)
            }
        }
        item?.totalOfficialCount?.let {  holder.inviteTeamMineMac?.text=it+mContext.getString(R.string.mining_machine_unit)}
        item?.totalGains?.let {  holder.inviteTeamAchievement?.text="${BigDecimalUtils.roundDOWN(it,2)} USDT"}
        item?.createTime?.let {  holder.inviteTime?.text=""+TimeUtils.getSpecialTime(it)}

    }


}