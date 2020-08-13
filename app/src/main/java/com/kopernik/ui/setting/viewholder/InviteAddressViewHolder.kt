package com.kopernik.ui.setting.viewholder

import android.view.View
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class InviteAddressViewHolder(view: View?) : BaseViewHolder(view) {
    var accountNameSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.account_name_spt)
            }
            return field
        }
        private set
    var gainSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.gain_spt)
            }
            return field
        }
        private set
    var accountAddrSpt: SuperTextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.account_address_spt)
            }
            return field
        }
        private set

}