package com.kopernik.ui.mine.adapter


import android.R.attr.width
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.mine.adapter.viewHolder.PurchaseMiningMachineHolder
import com.kopernik.ui.mine.entity.Data
import java.math.BigDecimal


class RuntimeMiningMAdapter(
    data: List<Data>
) :BaseQuickAdapter<Data, PurchaseMiningMachineHolder>(R.layout.item_runtime_mining_m,data){
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

        helper?.tvMiningSpeed?.text="${mContext.getString(R.string.mine_speed)}：${item?.power}${mContext.getString(R.string.mining_machine_times)}"
        helper?.tvWaveCounts?.text="${mContext.getString(R.string.mining_wave)}：${item?.rate}%"
       var value= item?.amount?.toDouble()?.let { BigDecimalUtils.divide(it,
           item?.amountTotal.toDouble()
       ) }
        helper?.totalIncome?.text="${mContext.getText(R.string.mine_total_income)}${BigDecimalUtils.roundDOWN(item?.amountTotal,2) } UTDM"
        var process=BigDecimalUtils.multiply(BigDecimalUtils.roundDOWN(value.toString(),2),"100")
        helper?.progressBar?.progress=process.toInt()
        if (item?.status=="3"){
            helper?.clIneffect?.visibility=View.VISIBLE
            helper?.tvMiningType?.setTextColor(ContextCompat.getColor(mContext,R.color.color_A49CC2))
        }else{
            helper?.clIneffect?.visibility=View.GONE
            helper?.tvMiningType?.setTextColor(ContextCompat.getColor(mContext,R.color.color_FFCF32))
        }

    }


}