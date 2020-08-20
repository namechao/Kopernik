package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class AssetViewHolder(view: View?) : BaseViewHolder(view) {
    var assetName: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.asset_name)
            }
            return field
        }
        private set



}