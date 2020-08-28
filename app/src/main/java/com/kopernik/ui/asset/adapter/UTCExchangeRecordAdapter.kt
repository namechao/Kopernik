package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExchangeRecord
import com.kopernik.ui.asset.entity.ExtractItem


class UTCExchangeRecordAdapter(
    data: List<ExchangeRecord>
) :BaseQuickAdapter<ExchangeRecord, UTCRecordHolder>(R.layout.item_exchange_record,data){
    override fun convert(helper: UTCRecordHolder?, item: ExchangeRecord?) {
        helper?.synthesisTime?.text=TimeUtils.normalTimeStampToMinute(item?.createTime?.toString())
        helper?.utcConsume?.text=item?.utcAmount
        helper?.handlingfee?.text=item?.rate
        helper?.synthesisCounts?.text=item?.uytAmount
    }


}