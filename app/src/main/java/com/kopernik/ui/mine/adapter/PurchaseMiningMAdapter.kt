package com.kopernik.ui.mine.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.mine.ViewHodler.MineMachineryViewHolder
import com.kopernik.ui.mine.entity.Machine


class PurchaseMiningMAdapter(
    data: List<Machine>
) :BaseQuickAdapter<Machine, MineMachineryViewHolder>(R.layout.item_purchase_mining_m,data){
    override fun convert(helper: MineMachineryViewHolder?, item: Machine?) {
       when(item?.type){
           1->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type1)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type1)
           }
           2->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type2)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type2)
           }
           3->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type3)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type3)
           }
           4->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type4)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type4)
           }
           5->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type5)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type5)
           }
           6->{
               helper?.ivMiningType?.setImageResource(R.mipmap.ic_mm_type6)
               helper?.tvMiningType?.text=mContext.getString(R.string.mining_machine_type6)
           }
       }
        helper?.tvMiningPrice?.text="${mContext.getString(R.string.mining_machine_price)}：${item?.price} USDT"
        helper?.tvMiningSpeed?.text="${mContext.getString(R.string.mining_power)}：${item?.power}${mContext.getString(R.string.mining_machine_times)}"
        helper?.tvWaveCounts?.text="${mContext.getString(R.string.mining_machine_speed)}：${item?.rateMin}%-${item?.rateMax}%"
        helper?.addOnClickListener(R.id.tv_purchase)
    }
    }

