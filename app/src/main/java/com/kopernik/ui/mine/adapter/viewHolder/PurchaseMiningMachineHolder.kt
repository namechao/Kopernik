package com.kopernik.ui.mine.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class PurchaseMiningMachineHolder(view: View?) : BaseViewHolder(view) {
    var progressBar:ProgressBar?=null
      get() {
          if (field==null) field=itemView.findViewById(R.id.progressBar)
          return field
      }
   var tvPrecent:TextView?=null
      get() {
          if (field==null) field=itemView.findViewById(R.id.tv_precent)
          return field
      }




}