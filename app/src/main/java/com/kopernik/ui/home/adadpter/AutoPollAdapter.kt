package com.kopernik.ui.home.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kopernik.R
import com.kopernik.ui.home.Entity.Collect

class AutoPollAdapter(
    private val mContext: Context,
    private val mData: List<Collect>
) : RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder>() {




    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        val item = mData[position % mData.size]
        item?.let {
            var  telephone=""
            if (it.phone!=null&&it.phone.length>5){
                telephone="${it.phone.subSequence(0,3)}****${it.phone.subSequence(it.phone.length-4,it.phone.length)}"
            }
            holder?.content?.text="${mContext.getString(R.string.home_info)} ${telephone} ${mContext.getString(R.string.home_info1)}  ${item.amount} ${mContext.getString(R.string.home_info2)}UTK"
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var content: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.content)
                }
                return field
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.auto_list_item, parent, false)
        return BaseViewHolder(view)

    }

}