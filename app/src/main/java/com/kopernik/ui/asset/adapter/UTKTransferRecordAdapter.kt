package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.adapter.viewHolder.UTKRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.ReceiveRecordEntity
import com.kopernik.ui.asset.entity.UtkTransferRecord


class UTKTransferRecordAdapter(
    data: List<UtkTransferRecord>
) :BaseQuickAdapter<UtkTransferRecord, UTKRecordHolder>(R.layout.item_transfer_record,data){
    override fun convert(helper: UTKRecordHolder?, item: UtkTransferRecord?) {
        helper?.assetTransferTime?.text=TimeUtils.normalTimeStampToMinute(item?.createTime?.toString())
        helper?.assetUid?.text=item?.uidReceive
        helper?.assetTransferCounts?.text=item?.amount
        helper?.assetHandlingFee?.text=item?.rate
    }


}