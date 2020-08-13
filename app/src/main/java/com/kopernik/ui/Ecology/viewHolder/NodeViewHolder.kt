package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.allen.library.SuperButton
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class NodeViewHolder(view: View?) : BaseViewHolder(view) {
    var rootView: LinearLayout? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.root_view)
            }
            return field
        }
        private set
    var logoIv: ImageView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_icon_iv)
            }
            return field
        }
        private set
    var nameTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_name_tv)
            }
            return field
        }
        private set
    var voteSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_vote_spb)
            }
            return field
        }
        private set
    var statusSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_status_spb)
            }
            return field
        }
        private set
    var ownSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_own_spb)
            }
            return field
        }
        private set
    var selfBondedSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_self_bonded_spt)
            }
            return field
        }
        private set
    var totalVoteSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_total_votes_spt)
            }
            return field
        }
        private set
    var jackpotBalanceSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_jackpot_balance_spt)
            }
            return field
        }
        private set

}