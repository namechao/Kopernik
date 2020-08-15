package com.kopernik.ui.home.adadpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.home.viewholder.HomeViewHolder

class HomeAdapter():BaseQuickAdapter<String,HomeViewHolder>(R.layout.item_home) {
    override fun convert(helper: HomeViewHolder?, item: String?) {

    }
}