package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.UtcComRecord


class UTCSynthesisRecordAdapter(
    data: List<UtcComRecord>
) :BaseQuickAdapter<UtcComRecord, UTCRecordHolder>(R.layout.item_synthesis_record,data){
    override fun convert(helper: UTCRecordHolder?, item: UtcComRecord?) {
        helper?.synthesisTime?.text=TimeUtils.normalTimeStampMonthDay(item?.createTime?.toString())
        helper?.utdmConsume?.text=BigDecimalUtils.getRound(item?.utdmAmount).toString()
        helper?.utkConsume?.text=BigDecimalUtils.getRound(item?.utkAmount).toString()
        helper?.handlingfee?.text=BigDecimalUtils.roundDOWN(item?.rate,2)
        helper?.synthesisCounts?.text=BigDecimalUtils.getRound(item?.amount).toString()
    }


}