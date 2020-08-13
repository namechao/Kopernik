package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
class OtherAssetDetailsInfoHolder(view: View?) : BaseViewHolder(view) {
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

    var otherViewLL: LinearLayout? = null
        get() {
            if (field == null) {
                field =
                    itemView.findViewById(R.id.other_chain_view_ll)
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


    var otherMiningFreezeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.other_mining_freeze_spt)
            }
            return field
        }
        private set
    var interestDrawn: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.interest_to_be_drawn)
            }
            return field
        }
        private set
    var otherAssetCaclSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.other_asset_calc_spt)
            }
            return field
        }
        private set

}