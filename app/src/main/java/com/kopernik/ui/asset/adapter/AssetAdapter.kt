package com.kopernik.ui.asset.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.ui.asset.entity.AssetItemBean
import com.kopernik.ui.asset.adapter.viewHolder.AssetViewHolder

class AssetAdapter() : BaseQuickAdapter<String,AssetViewHolder>(R.layout.item_asset) {
    override fun convert(helper: AssetViewHolder?, item: String?) {

    }


}

  
