package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class UTKRecordHolder(view:View) :BaseViewHolder(view){
 var assetRecevieTime:TextView?=null
     get() {
         if (field == null) {
             field = itemView.findViewById(R.id.asset_recevie_time)
         }
         return field
     }
     private set
    var assetReceiveCounts:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_receive_counts)
            }
            return field
        }
        private set
    var assetTransferTime:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_transfer_time)
            }
            return field
        }
        private set
    var assetUid:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_uid)
            }
            return field
        }
        private set
    var assetTransferCounts:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_transfer_counts)
            }
            return field
        }
        private set
    var assetHandlingFee:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_handling_fee)
            }
            return field
        }
        private set
}