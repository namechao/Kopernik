package com.kopernik.ui.home.adadpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.home.Entity.HomeCoinItem
import com.kopernik.ui.home.viewholder.HomeViewHolder

class HomeAdapter():BaseQuickAdapter<HomeCoinItem,HomeViewHolder>(R.layout.item_home) {
    override fun convert(helper: HomeViewHolder?, item: HomeCoinItem?) {
        helper?.coinName?.text=item?.coinName
        helper?.coinName1?.text=item?.coinName1
        helper?.coinPrice?.text=item?.coinPrice
        helper?.coinPrice1?.text=item?.coinPrice1
        helper?.coinRiseFall?.text=item?.coinRiseFall
        if (item?.coinRiseFall!=null) {
            if (item?.coinRiseFall.startsWith("+")) {
                helper?.coinRiseFall?.setBackgroundResource(R.drawable.coin_rise_bg)
            } else if (item?.coinRiseFall.startsWith("-")) {
                helper?.coinRiseFall?.setBackgroundResource(R.drawable.coin_fall_bg)
            }
        }
    }
}