package com.kopernik.ui.account.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R

class MnemonicInputAdapter(data:List<String>) :BaseQuickAdapter<String, MnemonicInputAdapter.MnemonicViewHolder>(R.layout.item_mnemonic,data) {
    override fun convert(helper: MnemonicViewHolder?, item: String?) {
        helper?.mnemonicTv?.setText(item)
        helper?.addOnClickListener(R.id.item_mnemonic_tv)
    }
    inner  class MnemonicViewHolder(itemView: View?) : BaseViewHolder(itemView) {
        var mnemonicTv: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.item_mnemonic_tv)
                }
                return field
            }
            set
    }

}