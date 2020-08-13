package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import android.widget.TextView
import com.allen.library.SuperButton
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R


class ReferendumViewHolder(itemView: View?) : BaseViewHolder(itemView) {
    var titleTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.title_tv)
            }
            return field
        }
        private set
    var validityPeriodSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.validity_period_spt)
            }
            return field
        }
        private set
    var statusSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.status_spb)
            }
            return field
        }
        private set

}