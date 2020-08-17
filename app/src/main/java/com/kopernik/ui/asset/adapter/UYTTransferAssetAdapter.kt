package com.kopernik.ui.asset.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper


class UYTTransferAssetAdapter(
    data: List<String>
) : BaseQuickAdapter<String, UYTAssetViewHolder>(R.layout.item_uyt_transfer_asset,data) {
    override fun convert(holder: UYTAssetViewHolder, item: String?) {

        holder.txHashSpt?.setRightImageViewClickListener { imageView ->
            APPHelper.copy(
                mContext as Activity,
               "1"
            )
        }
        holder.rechargeAddrSpt?.setRightImageViewClickListener { imageView ->
            APPHelper.copy(
                mContext as Activity,
                "1"
            )
        }
    }


}