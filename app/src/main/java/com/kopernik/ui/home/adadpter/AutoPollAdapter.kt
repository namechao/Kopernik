package com.kopernik.ui.home.adadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kopernik.R
 class AutoPollAdapter(
    private val mContext: Context,
    private val mData: List<String>
) : RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder>() {




    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        val content = mData[position % mData.size]
        holder?.content?.text = content
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