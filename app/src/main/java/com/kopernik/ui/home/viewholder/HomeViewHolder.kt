package com.kopernik.ui.home.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class HomeViewHolder(view:View): BaseViewHolder(view) {
    var coinName: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.coinName)
            }
            return field
        }
        private set
    var coinName1: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.coinName1)
            }
            return field
        }
        private set
    var coinPrice: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.coinPrice)
            }
            return field
        }
        private set
    var coinPrice1: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.coinPrice1)
            }
            return field
        }
        private set
    var coinRiseFall: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.coinRiseFall)
            }
            return field
        }
        private set

}