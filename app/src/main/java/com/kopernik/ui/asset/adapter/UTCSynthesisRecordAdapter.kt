package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.UtcComRecord


class UTCSynthesisRecordAdapter(
    data: List<UtcComRecord>
) :BaseQuickAdapter<UtcComRecord, UTCRecordHolder>(R.layout.item_synthesis_record,data){
    override fun convert(helper: UTCRecordHolder?, item: UtcComRecord?) {
        helper?.synthesisTime?.text=TimeUtils.normalTimeStampToMinute(item?.createTime?.toString())
        helper?.utdmConsume?.text=item?.utdmAmount
        helper?.utkConsume?.text=item?.utkAmount
        helper?.handlingfee?.text=item?.rate
        helper?.synthesisCounts?.text=item?.amount
    }


}