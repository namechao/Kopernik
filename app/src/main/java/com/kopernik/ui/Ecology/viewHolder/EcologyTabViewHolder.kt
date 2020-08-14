package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class EcologyTabViewHolder(itemView: View?) :
    BaseViewHolder(itemView) {
    var tvTab: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_tab)
            }
            return field
        }
        private set


}