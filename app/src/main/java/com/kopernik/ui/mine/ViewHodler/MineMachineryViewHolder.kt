package com.kopernik.ui.mine.ViewHodler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class MineMachineryViewHolder (view: View): BaseViewHolder(view) {
    var ivMiningType: ImageView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.iv_mining_type)
            }
            return field
        }
        private set
    var tvMiningType: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_mining_type)
            }
            return field
        }
        private set
    var tvMiningPrice: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_mining_price)
            }
            return field
        }
        private set
    var tvMiningSpeed: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_mining_speed)
            }
            return field
        }
        private set
    var tvWaveCounts: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_wave_counts)
            }
            return field
        }
        private set
    var tvPurchase: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.tv_purchase)
            }
            return field
        }
        private set
}