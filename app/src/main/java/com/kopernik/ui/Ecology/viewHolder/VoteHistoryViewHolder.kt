package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R


class VoteHistoryViewHolder(view: View?) : BaseViewHolder(view) {
    var operatingSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.operating_spt)
            }
            return field
        }
        private set
    var valueSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.value_spt)
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
    var nodeAddressSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.node_address_spt)
            }
            return field
        }
        private set

}