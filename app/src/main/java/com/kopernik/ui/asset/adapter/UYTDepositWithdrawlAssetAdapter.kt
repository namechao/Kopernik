package com.kopernik.ui.asset.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.entity.DepositWithdarwlRecord


class UYTDepositWithdrawlAssetAdapter(
    data: List<DepositWithdarwlRecord>
) : BaseQuickAdapter<DepositWithdarwlRecord, UYTAssetViewHolder>(R.layout.item_uyt_d_w_asset,data) {
    override fun convert(holder: UYTAssetViewHolder, item: DepositWithdarwlRecord?) {
        item?.operate?.let {
            if (it.contains("Recharge"))
            holder.typeTv?.text=mContext.getString(R.string.title_asset_deposit)  else if (it.contains("Cash"))  holder.typeTv?.text=mContext.getString(R.string.title_asset_withdrawl)
        }
        holder.txHashSpt?.setLeftString(item?.extrinsicHash)
        holder.txHashSpt?.setRightImageViewClickListener {
            item?.extrinsicHash?.let {
                APPHelper.copy(
                    mContext as Activity,
                    it
                )
            }

        }
        holder.rechargeAddrSpt?.setLeftString(item?.addressHash)
        holder.rechargeAddrSpt?.setRightImageViewClickListener {
            item?.addressHash?.let {
                APPHelper.copy(
                    mContext as Activity,
                    it
                )
            }
        }
        holder.valueSpt?.setLeftString(item?.amount)
            if ( item?.flag!=null&&item.flag==2) {
                holder.stausSpt?.setLeftString(mContext.getString(R.string.success))
            }
        holder.timeSpt?.setLeftString( TimeUtils.normalTimeStampToMinute(item?.createTime?.toString()))
    }


}