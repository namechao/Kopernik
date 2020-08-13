package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class OtherAssetDetailsChildItemHolder(view: View?) : BaseViewHolder(view) {
    var typeTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.type_tv)
            }
            return field
        }
        private set
    var txHashSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tx_hash_spt)
            }
            return field
        }
        private set
    var rechargeAddrSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.recharge_address_spt)
            }
            return field
        }
        private set
    var timeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.time_spt)
            }
            return field
        }
        private set
    var valueSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.amount_spt)
            }
            return field
        }
        private set
    var stausSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.status_spt)
            }
            return field
        }
        private set
    var remarkSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.remark_spt)
            }
            return field
        }
        private set
    var extractionMiningincome: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.extraction_mining_income)
            }
            return field
        }
        private set
    var minerFee: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.miner_fee)
            }
            return field
        }
        private set

}