package com.kopernik.ui.asset.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.ui.asset.entity.AssetItemBean
import com.kopernik.ui.asset.adapter.viewHolder.AssetViewHolder

class AssetAdapter() : BaseQuickAdapter<String,AssetViewHolder>(R.layout.item_asset) {
    override fun convert(helper: AssetViewHolder?, item: String?) {
        when(helper?.adapterPosition){
            0-> helper.assetName?.text="UTC"
            1-> helper.assetName?.text="UTK"
            2-> helper.assetName?.text="UTDM"
            3->helper.assetName?.text="UYT"
        }
    }


}

  
