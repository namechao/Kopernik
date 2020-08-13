package com.kopernik.ui.asset.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.asset.adapter.viewHolder.ToBeExtractedHolder
import com.kopernik.ui.asset.entity.ExtractItem


class ToBeExtractedAdapter(
    data: List<ExtractItem>
) :BaseQuickAdapter<ExtractItem, ToBeExtractedHolder>(R.layout.item_to_be_extract){
    override fun convert(helper: ToBeExtractedHolder?, item: ExtractItem?) {
        helper?.transformNum?.text = item?.btcvalue
        helper?.transfromTime?.text = TimeUtils.normalTimeStampToData(item?.createitme)
//        flag   0：待审核 1：审核通过 2：提现成功 4：uyt链交易中  5:审核失败 6：取消提现  8：提现失败
        when (item?.flag) {
            0 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.pending)
            }
            1 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.approved)
            }
            2 -> {
                helper?.transformStatus?.text =
                    mContext.getString(R.string.tip_asset_withdraw_success)
            }
            4 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.transaction)
            }
            5 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.audit_failures)
            }
            7 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.cancel_withdraw)
            }
            8 -> {
                helper?.transformStatus?.text = mContext.getString(R.string.cash_withdrawal_failure)
            }
        }
        helper?.addOnClickListener(R.id.cancel)
        helper?.addOnClickListener(R.id.withdrawCoin)
    }


}