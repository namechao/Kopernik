package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.adapter.viewHolder.UTKRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.ReceiveRecordEntity
import com.kopernik.ui.asset.entity.UtkReceiveRecord


class UTKReceiveRecordAdapter(
    data: List<UtkReceiveRecord>
) :BaseQuickAdapter<UtkReceiveRecord, UTKRecordHolder>(R.layout.item_receive_record,data){
    override fun convert(helper: UTKRecordHolder?, item: UtkReceiveRecord?) {
        helper?.assetRecevieTime?.text=TimeUtils.normalTimeStampMonthDay(item?.createTime?.toString())
        helper?.assetReceiveCounts?.text=BigDecimalUtils.roundDOWN(item?.amount,2)
    }


}