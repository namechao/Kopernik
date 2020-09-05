package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExchangeRecord
import com.kopernik.ui.asset.entity.ExtractItem


class UTCExchangeRecordAdapter(
    data: List<ExchangeRecord>
) :BaseQuickAdapter<ExchangeRecord, UTCRecordHolder>(R.layout.item_exchange_record,data){
    override fun convert(helper: UTCRecordHolder?, item: ExchangeRecord?) {
        helper?.synthesisTime?.text=TimeUtils.normalTimeStampMonthDay(item?.createTime?.toString())
        helper?.utcConsume?.text= BigDecimalUtils.roundDOWN(item?.utcAmount,2).toString()
        helper?.handlingfee?.text=BigDecimalUtils.roundDOWN(item?.rate,2)
        helper?.synthesisCounts?.text=BigDecimalUtils.roundDOWN(item?.uytAmount,2)
    }


}