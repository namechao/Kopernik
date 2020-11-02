package com.kopernik.ui.asset.adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.UTCRecordHolder
import com.kopernik.ui.asset.entity.CoinType
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.entity.UtcComRecord


class ChoseCoinAdapter(
    data: List<CoinType>
) :BaseQuickAdapter<CoinType, ChoseCoinHolder>(R.layout.item_chose_coin_type,data){
    override fun convert(helper: ChoseCoinHolder?, item: CoinType?) {
        helper?.assetName?.text=item?.coinType
        item?.headRes?.let { helper?.assetHead?.setImageResource(it) }
    }


}
 class ChoseCoinHolder(view: View): BaseViewHolder(view) {
     var assetHead: ImageView?=null
         get() {
             if (field == null) {
                 field = itemView.findViewById(R.id.asset_head)
             }
             return field
         }
         private set
     var assetName: TextView?=null
         get() {
             if (field == null) {
                 field = itemView.findViewById(R.id.asset_name)
             }
             return field
         }
         private set

}