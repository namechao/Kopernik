package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
class AssetDetailsInfoHolder(view: View?) : BaseViewHolder(view) {
    var eyeCb: CheckBox? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.eye_cb)
            }
            return field
        }
        private set
    var amountTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.balance_of_tv)
            }
            return field
        }
        private set
    var dnsTotalBalanceDiv: View? = null
        get() {
            if (field == null) {
                field =
                    itemView.findViewById(R.id.dns_total_balance_div)
            }
            return field
        }
        private set


    var voteFreezeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.vote_freeze_spt)
            }
            return field
        }
        private set
    var txFreezeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tx_freeze_spt)
            }
            return field
        }
        private set
    var dnsTotalBalanceSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.dns_total_balance_spt)
            }
            return field
        }
        private set



    var dnsNodeGainSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.dns_node_gain_spt)
            }
            return field
        }
        private set


}