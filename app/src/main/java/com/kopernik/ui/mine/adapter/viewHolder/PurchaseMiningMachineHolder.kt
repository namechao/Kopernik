package com.kopernik.ui.mine.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class PurchaseMiningMachineHolder(view: View?) : BaseViewHolder(view) {
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


    var totalIncome: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.total_income)
            }
            return field
        }
        private set
    var progressBar:ProgressBar?=null
      get() {
          if (field==null) field=itemView.findViewById(R.id.progressBar)
          return field
      }

    var clIneffect: ConstraintLayout?=null
      get() {
          if (field==null) field=itemView.findViewById(R.id.cl_ineffect)
          return field
      }
}