package com.kopernik.ui.my.viewHoloder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class InviteFriendsHolder(view:View) :BaseViewHolder(view){
 var inviteUid:TextView?=null
     get() {
         if (field == null) {
             field = itemView.findViewById(R.id.invite_uid)
         }
         return field
     }
     private set
    var inviteMineMac:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.invite_mine_mac)
            }
            return field
        }
        private set
    var inviteTeamMineMac:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.invite_team_mine_mac)
            }
            return field
        }
        private set
    var inviteTeamAchievement:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.invite_team_achievement)
            }
            return field
        }
        private set
    var inviteTime:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.invite_time)
            }
            return field
        }
        private set

}