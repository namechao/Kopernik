package com.kopernik.ui.asset.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.adapter.viewHolder.UYTAssetViewHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.entity.DepositWithdarwlRecord


class DepositHistoryAdapter(
    data: List<DepositWithdarwlRecord>
) : BaseQuickAdapter<DepositWithdarwlRecord, UYTAssetViewHolder>(R.layout.item_deposit_history,data) {
    override fun convert(holder: UYTAssetViewHolder, item: DepositWithdarwlRecord?) {
        item?.operate?.let {
            if (it.contains("Recharge"))
                holder.typeTv?.text =
                    mContext.getString(R.string.title_asset_deposit) else if (it.contains("Cash")) holder.typeTv?.text =
                mContext.getString(R.string.title_asset_withdrawl)
        }

        holder.valueSpt?.setLeftString(item?.amount)
//        0:待确认 1：审核通过  2：成功 3.确认失败 4.提现失败
            if (item?.flag!=null&&item.flag==0){
                holder.stausSpt?.setLeftString(mContext.getString(R.string.to_be_confirmed))
            }else if (item?.flag!=null&&item.flag==1){
                holder.stausSpt?.setLeftString(mContext.getString(R.string.examinations_passed))
            }else if ( item?.flag!=null&&item.flag==2) {
                holder.stausSpt?.setLeftString(mContext.getString(R.string.success))
            }else if(item?.flag!=null&&item.flag==3){
                holder.stausSpt?.setLeftString(mContext.getString(R.string.confirmation_failed))
            }else if(item?.flag!=null&&item.flag==4){
                holder.stausSpt?.setLeftString(mContext.getString(R.string.withdrawal_failed))
            }
        holder.timeSpt?.setRightString( TimeUtils.otherTimeStampToMinute(item?.createTime?.toString()))
    }


}