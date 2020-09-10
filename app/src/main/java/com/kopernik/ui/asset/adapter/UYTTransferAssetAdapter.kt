package com.kopernik.ui.asset.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.entity.UYTTransferRecord
import com.kopernik.ui.asset.entity.UtkTransferRecord


class UYTTransferAssetAdapter(
    data: List<UYTTransferRecord>
) : BaseQuickAdapter<UYTTransferRecord, UYTAssetViewHolder>(R.layout.item_uyt_transfer_asset,data) {
    override fun convert(holder: UYTAssetViewHolder, item: UYTTransferRecord?) {

        item?.operate?.let {
            if (it.contains("ROLL_IN"))
                holder.typeTv?.text=mContext.getString(R.string.transfer_in)  else if (it.contains("ROLL_OUT")) holder.typeTv?.text=mContext.getString(R.string.transfer_out)
        }
        holder.txHashSpt?.setLeftString(item?.tradingHash)
        holder.txHashSpt?.setRightImageViewClickListener {
            item?.tradingHash?.let {
                APPHelper.copy(
                    mContext as Activity,
                    it
                )
            }

        }
        holder.rechargeAddrSpt?.setLeftString(item?.uidReceive)
        holder.rechargeAddrSpt?.setRightImageViewClickListener {
            item?.uidReceive?.let {
                APPHelper.copy(
                    mContext as Activity,
                    it
                )
            }
        }
        holder.valueSpt?.setLeftString(item?.amount)
        holder.stausSpt?.setLeftString(mContext.getString(R.string.success))
        holder.timeSpt?.setLeftString( TimeUtils.normalTimeStampToMinute(item?.createTime?.toString()))
    }
    }
