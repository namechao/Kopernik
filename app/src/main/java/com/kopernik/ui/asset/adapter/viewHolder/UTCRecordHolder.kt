package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class UTCRecordHolder(view:View) :BaseViewHolder(view){
 var synthesisTime:TextView?=null
     get() {
         if (field == null) {
             field = itemView.findViewById(R.id.asset_synthesis_time)
         }
         return field
     }
     private set
    var utdmConsume:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_utdm_consume)
            }
            return field
        }
        private set
    var utkConsume:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_utk_consume)
            }
            return field
        }
        private set
    var handlingfee:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_handling_fee)
            }
            return field
        }
        private set
    var synthesisCounts:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_synthesis_counts)
            }
            return field
        }
        private set
    var utcConsume:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_utc_consume)
            }
            return field
        }
        private set
}