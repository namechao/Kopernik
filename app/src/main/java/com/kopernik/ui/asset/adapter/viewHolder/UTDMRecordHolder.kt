package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class UTDMRecordHolder(view:View) :BaseViewHolder(view){
 var assetUdmtTime:TextView?=null
     get() {
         if (field == null) {
             field = itemView.findViewById(R.id.asset_udmt_time)
         }
         return field
     }
     private set
    var assetMineType:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_mine_type)
            }
            return field
        }
        private set
    var assetMineOutput:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_mine_output)
            }
            return field
        }
        private set

}