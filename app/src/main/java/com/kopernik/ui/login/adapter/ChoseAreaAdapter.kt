package com.kopernik.ui.login.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.ui.login.bean.LoginCountryBean

class ChoseAreaAdapter(data:List<LoginCountryBean>) : BaseQuickAdapter<LoginCountryBean, ChoseAreaAdapter.ChoseAreaViewHolder>(R.layout.dialog_choose_area_item,data){

    override fun convert(helper: ChoseAreaViewHolder?, item: LoginCountryBean?) {
        helper?.tvHead?.text=item?.header
        item?.resId?.let { helper?.ivHead?.setBackgroundResource(it) }
    }
  inner  class ChoseAreaViewHolder(itemView: View?) : BaseViewHolder(itemView) {
        var tvHead: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.tvHead)
                }
                return field
            }
      var ivHead: ImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.ivHead)
                }
                return field
            }
    }

}