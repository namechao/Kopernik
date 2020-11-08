package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.adapter.viewHolder.UTDMRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.UTDMRecord
import java.math.BigDecimal


class UDMTRevenueRecordsAdapter(
    data: List<UTDMRecord>
) :BaseQuickAdapter<UTDMRecord, UTDMRecordHolder>(R.layout.item_revenue_record,data){
    override fun convert(helper: UTDMRecordHolder?, item: UTDMRecord?) {
        helper?.assetUdmtTime?.text=TimeUtils.normalTimeStampMonthDay(item?.createTime?.toString())
        var total=BigDecimalUtils.roundDOWN(BigDecimal(item?.staticGains).
        add(BigDecimal(item?.shareGains).add(BigDecimal(item?.shareAllGains).
        add(BigDecimal(item?.communityGains).add(BigDecimal(item?.levelGains))))).toString(),2)
        helper?.assetMineOutput?.text=total+" UTDM"
    }


}