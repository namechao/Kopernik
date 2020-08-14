package com.kopernik.ui.Ecology.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.Ecology.entity.ReferendumDetailsBean
import com.kopernik.ui.Ecology.viewHolder.EcologyTabViewHolder


class EcologyTabAddrAdapter(list: List<String>) :
    BaseQuickAdapter<String,EcologyTabViewHolder>(R.layout.item_ecology_tab,list) {
      var positon=0
      set
    override fun convert(helper: EcologyTabViewHolder?, item: String?) {
        helper?.tvTab?.text = item

        if (helper?.adapterPosition==positon){
            helper?.tvTab?.isSelected =true
            helper?.tvTab?.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent))
        }else{
            helper?.tvTab?.isSelected =false
            helper?.tvTab?.setTextColor(ContextCompat.getColor(mContext,R.color.color_F4C41B))
        }
    }
}