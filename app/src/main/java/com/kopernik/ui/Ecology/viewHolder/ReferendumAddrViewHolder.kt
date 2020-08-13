package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class ReferendumAddrViewHolder(itemView: View?) :
    BaseViewHolder(itemView) {
    var addressTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.address_tv)
            }
            return field
        }
        private set
    var dnsTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.dns_tv)
            }
            return field
        }
        private set
    var resultTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.result_tv)
            }
            return field
        }
        private set

}