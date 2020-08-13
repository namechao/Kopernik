package com.kopernik.ui.asset.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class AssetViewHolder(view: View?) : BaseViewHolder(view) {
    var logoIv: ImageView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.logo_iv)
            }
            return field
        }
        private set
    var nameTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.token_name_tv)
            }
            return field
        }
        private set
        var balanceSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.balance_of_spt)
            }
            return field
        }
        private set
    var freezeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.freeze_spt)
            }
            return field
        }
        private set
    var cnyTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.cnyc_tv)
            }
            return field
        }
        private set
    var split_line: View? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.split_line)
            }
            return field
        }
        private set

}