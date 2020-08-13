package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class ToBeExtractedHolder(view:View) :BaseViewHolder(view){
 var transformNum:TextView?=null
     get() {
         if (field == null) {
             field = itemView.findViewById(R.id.transformNum)
         }
         return field
     }
     private set
    var transfromTime:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.transfromTime)
            }
            return field
        }
        private set
    var transformStatus:TextView?=null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.transformStatus)
            }
            return field
        }
        private set
}