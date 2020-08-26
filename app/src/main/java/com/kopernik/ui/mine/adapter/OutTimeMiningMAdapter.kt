package com.kopernik.ui.mine.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.mine.adapter.viewHolder.PurchaseMiningMachineHolder
import com.kopernik.ui.mine.entity.Data


class OutTimeMiningMAdapter(
    data: List<Data>
) :BaseQuickAdapter<Data, PurchaseMiningMachineHolder>(R.layout.item_out_time_mining_m,data){
    override fun convert(helper: PurchaseMiningMachineHolder?, item: Data?) {
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
        helper?.tvWaveCounts?.text="${mContext.getString(R.string.mining_machine_speed)}：${item?.rate}%"
        helper?.tvMningTime?.text=TimeUtils.normalTimeStampToMinute(item?.createTime)
        helper?.tvMiningCounts?.text= BigDecimalUtils.roundDOWN(item?.amount,4)
        var value= item?.amount?.toDouble()?.let { BigDecimalUtils.divide(it,
            item?.amountTotal.toDouble()
        ) }
        var process= BigDecimalUtils.multiply(BigDecimalUtils.roundDOWN(value.toString(),2),"100")
        helper?.progressBar?.progress=process.toInt()
        helper?.tvPrecent?.text="${process.toInt()}%"
    }


}