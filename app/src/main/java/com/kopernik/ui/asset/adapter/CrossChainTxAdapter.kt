package com.kopernik.ui.asset.adapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.asset.entity.CrossChainTxBean
import com.kopernik.ui.asset.adapter.viewHolder.CrossChainTxHolder
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils

class CrossChainTxAdapter( private val type: Int) :
    BaseQuickAdapter<CrossChainTxBean.DatasBean, CrossChainTxHolder>(
        R.layout.item_cross_chain_tx
    ) {
    private val DNS = 1
    private val BTC = 2
    private val ETH = 3
    private val USDT = 4
    private val HT = 5
    override fun convert(
        helper: CrossChainTxHolder,
        item: CrossChainTxBean.DatasBean
    ) {
        if (item.type == 1) {
            if (item.flag == 0) {
                helper.cancelWithdrawSpb?.visibility = View.VISIBLE
            } else {
                helper.cancelWithdrawSpb?.visibility = View.GONE
            }
            helper.typeTv?.text = mContext.resources.getString(R.string.title_asset_withdraw)
            helper.rechargeAddrSpt
                ?.setLeftTopString(mContext.resources.getString(R.string.withdraw_address))
        } else {
            helper.typeTv?.text = mContext.resources.getString(R.string.title_asset_recharge)
            helper.rechargeAddrSpt?.setLeftTopString(
                mContext.resources.getString(R.string.title_recharge_address)
            )
            helper.cancelWithdrawSpb?.visibility = View.GONE
        }
        var txHash = item.btcJyhash
        if (txHash != null && txHash.length > 35) txHash = txHash.substring(0, 35) + "..."
        helper.txHashSpt?.setLeftString(txHash ?: "")
        var rechargeAddr = item.withdrawalAddress
        if (rechargeAddr != null && rechargeAddr.length > 35) rechargeAddr =
            rechargeAddr.substring(0, 35) + "..."
        helper.rechargeAddrSpt?.setLeftString(rechargeAddr ?: "")
        helper.timeSpt
            ?.setLeftString(TimeUtils.normalTimeStampToData(item.createitme.toString() + ""))
        if (type == DNS) {
            helper.valueSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.btcvalue, 4))
        } else if (type == BTC || type == ETH) {
            helper.valueSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.btcvalue, 8))
        } else {
            helper.valueSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.btcvalue, 6))
        }
        when (item.flag) {
            0 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.pending_review))
            1 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.examination_passed))
            2 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.success))
            3 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.cash_withdrawal_failure))
            5 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.audit_failure))
            6 -> helper.stausSpt
                ?.setLeftString(mContext.resources.getString(R.string.cancel_withdraw))
        }
        helper.txHashSpt
            ?.setRightImageViewClickListener { imageView: ImageView? ->
                APPHelper.copy(
                    mContext as Activity,
                    item.btcJyhash
                )
            }
        helper.rechargeAddrSpt
            ?.setRightImageViewClickListener { imageView: ImageView? ->
                APPHelper.copy(
                    mContext as Activity,
                    item.withdrawalAddress
                )
            }
        helper.addOnClickListener(R.id.cancel_withdraw_spb)
    }

}