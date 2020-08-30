package com.kopernik.ui.asset.adapter

import android.annotation.SuppressLint
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.entity.AssetItemBean
import com.kopernik.ui.asset.adapter.viewHolder.AssetViewHolder
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.asset.entity.AssetItemEntity

class AssetAdapter(data:List<AssetItemEntity>) : BaseQuickAdapter<AssetItemEntity,AssetViewHolder>(R.layout.item_asset) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: AssetViewHolder?, item: AssetItemEntity?) {
        helper?.assetName?.text=item?.coinType
        item?.headRes?.let { helper?.assetHead?.setImageResource(it) }
        helper?.assetCounts?.text=BigDecimalUtils.roundDOWN(item?.coinCounts,2)
        helper?.assetCny?.text="≈¥ "+BigDecimalUtils.roundDOWN(item?.coinCny,2)
    }


}

  
