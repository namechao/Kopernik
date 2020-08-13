package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.widget.roundedimageview.RoundedImageView


class TimeLienViewHolder(view: View?) : BaseViewHolder(view) {
    var timeSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.time_spt)
            }
            return field
        }
        private set
    var desc1Spt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.desc1_spt)
            }
            return field
        }
        private set
    var desc2Spt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.desc2_spt)
            }
            return field
        }
        private set
    var desc3Spt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.desc3_spt)
            }
            return field
        }
        private set
    var roundedImageView: RoundedImageView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.rounded_iv)
            }
            return field
        }
        private set

}