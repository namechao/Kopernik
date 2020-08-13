package com.kopernik.ui.Ecology.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.Ecology.entity.ReferendumDetailsBean
import com.kopernik.ui.Ecology.viewHolder.ReferendumAddrViewHolder


class ReferendumAddrAdapter(layoutResId: Int) :
    BaseQuickAdapter<ReferendumDetailsBean.DetailListBean, ReferendumAddrViewHolder>(layoutResId) {
    override fun convert(
        helper: ReferendumAddrViewHolder,
        item: ReferendumDetailsBean.DetailListBean
    ) {
        helper.addressTv?.text = item.acountHash
        helper.dnsTv?.text = BigDecimalUtils.roundDOWN(item.dnsSycount.toString() + "", 4).toString() + " UYT"
        if (item.referendumOpinion == 2) {
            helper.resultTv?.setText(R.string.oppose)
            helper.resultTv?.setTextColor(ContextCompat.getColor(mContext,R.color.red))
        } else {
            helper.resultTv?.setText(R.string.agree)
            helper.resultTv?.setTextColor(ContextCompat.getColor(mContext,R.color.referendum_normal_bg))
        }
    }
}