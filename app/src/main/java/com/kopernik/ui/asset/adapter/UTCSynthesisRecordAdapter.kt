package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.ExtractItem


class UTCSynthesisRecordAdapter(
    data: List<String>
) :BaseQuickAdapter<String, UTCRecordHolder>(R.layout.item_synthesis_record,data){
    override fun convert(helper: UTCRecordHolder?, item: String?) {

    }


}