package com.kopernik.ui.asset.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.ui.asset.entity.AssetItemBean
import com.kopernik.ui.asset.adapter.viewHolder.AssetViewHolder

class AssetAdapter(data: List<AssetItemBean>) :
    BaseMultiItemQuickAdapter<AssetItemBean, BaseViewHolder>(data) {

    init {
        addItemType(AssetItemBean.TYPE_HEADER, R.layout.item_asset_head)
        addItemType(AssetItemBean.TYPE_DATA, R.layout.item_asset)
    }
    override fun convert(holder: BaseViewHolder?, item: AssetItemBean?) {
        when (holder?.itemViewType) {
            AssetItemBean.TYPE_HEADER -> holder?.setText(R.id.head_title_tv, item?.titleName)
            AssetItemBean.TYPE_DATA -> {
                item?.let {
                    val viewHolder =
                        AssetViewHolder(
                            holder?.itemView
                        )
                    viewHolder.logoIv?.setImageResource(it.logoResId)
                    viewHolder.nameTv?.text = it.tokenName
                    viewHolder.balanceSpt?.setLeftString(it.balance)
                    viewHolder.freezeSpt?.setLeftString(it.freeze)
                    viewHolder.cnyTv?.text = it?.cny
                    if (it.tokenName.equals("UYT") || it.tokenName.equals("HT")) {
                        viewHolder.split_line?.visibility = View.INVISIBLE
                    } else {
                        viewHolder.split_line?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

  


  
}