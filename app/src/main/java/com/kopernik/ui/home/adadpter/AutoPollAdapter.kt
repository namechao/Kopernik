package com.kopernik.ui.home.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.ui.home.Entity.Collect

class AutoPollAdapter() :BaseQuickAdapter<Collect,AutoPollAdapter.BViewHolder>(R.layout.auto_list_item) {


    override fun convert(helper: BViewHolder?, item: Collect?) {
        item?.let {
            var  telephone=""
            if (it.phone.length>5){
                telephone="${it.phone.subSequence(0,3)}****${it.phone.subSequence(it.phone.length-4,it.phone.length)}"
            }
            helper?.content?.text="${mContext.getString(R.string.home_info)} ${telephone} ${mContext.getString(R.string.home_info1)}  ${item.amount} ${mContext.getString(R.string.home_info2)}UTK"
        }

    }





    inner class BViewHolder(itemView: View) :
       BaseViewHolder(itemView) {
        var content: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.content)
                }
                return field
            }
    }


}