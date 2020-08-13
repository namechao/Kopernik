package com.kopernik.ui.asset.adapter

import android.app.Activity
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.asset.adapter.viewHolder.AssetDetailsChildItemHolder
import com.kopernik.ui.asset.adapter.viewHolder.AssetDetailsInfoHolder
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.AssetDetailsChildBean
import com.kopernik.ui.asset.entity.AssetDetailsItemBean
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils


class AssetDetailsAdapter(
    data: MutableList<AssetDetailsItemBean>
) : BaseMultiItemQuickAdapter<AssetDetailsItemBean, BaseViewHolder>(data) {
    private var assetShow = true
    init {
        addItemType(AssetDetailsItemBean.TYPE_INFO, R.layout.item_asset_details_info)
        addItemType(AssetDetailsItemBean.TYPE_HEADER, R.layout.item_asset_details_head)
        addItemType(AssetDetailsItemBean.TYPE_ITEM, R.layout.item_asset_details_child_item)
        addItemType(AssetDetailsItemBean.TYPE_EMPTY, R.layout.item_empty_data)
        assetShow = UserConfig.singleton?.assetShow!!
    }

    override fun convert(holder: BaseViewHolder?, item: AssetDetailsItemBean?) {
        when (holder?.itemViewType) {
            AssetDetailsItemBean.TYPE_INFO -> {
                val assetBean: AssetBean = item?.info!!
                val infoHolder = AssetDetailsInfoHolder(holder?.itemView)
                    infoHolder.voteFreezeSpt
                        ?.setLeftTopString(mContext.getString(R.string.title_vote_block))
                    infoHolder.dnsTotalBalanceDiv?.visibility = View.VISIBLE
                    infoHolder.dnsTotalBalanceSpt?.visibility = View.VISIBLE
                    if (assetShow) {
                        //可用余额
                        infoHolder.amountTv?.text = BigDecimalUtils.roundDOWN(
                            assetBean.capitalUyt?.balance, 4
                        )
                       //投票冻结
                        infoHolder.voteFreezeSpt?.setLeftString(
                            BigDecimalUtils.roundDOWN(
                                assetBean.capitalUyt?.voteFreeze, 4
                            )
                        )
                        //交易冻结
                        infoHolder.txFreezeSpt?.setLeftString(
                            BigDecimalUtils.roundDOWN(
                                assetBean.capitalUyt?.transactionFreeze, 4
                            )
                        )
                        //总余额
                        infoHolder.dnsTotalBalanceSpt?.setLeftString(
                            BigDecimalUtils.roundDOWN(
                                assetBean.dnsCountTotal,
                                4
                            )
                        )
                        //节点收益
                        infoHolder.dnsNodeGainSpt
                            ?.setLeftString(BigDecimalUtils.roundDOWN(assetBean.nodeRevenue, 4))
                    } else {
                        infoHolder.amountTv?.setText("***")
                        infoHolder.voteFreezeSpt?.setLeftString("*****")
                        infoHolder.txFreezeSpt?.setLeftString("*****")
                        infoHolder.dnsTotalBalanceSpt?.setLeftString("*****")
                        infoHolder.dnsNodeGainSpt?.setLeftString("*****")
                    }
                infoHolder.eyeCb?.isChecked = assetShow
                infoHolder.eyeCb?.setOnCheckedChangeListener { buttonView, isChecked ->
                    assetShow = isChecked
                    UserConfig.singleton?.assetShow = isChecked
                    notifyDataSetChanged()
                }
                holder.addOnClickListener(R.id.extraction_recommend_income)
            }
            AssetDetailsItemBean.TYPE_HEADER ->
                holder.setVisible(R.id.choose_tv, false)
            AssetDetailsItemBean.TYPE_ITEM -> {
                val childItemHolder =
                    AssetDetailsChildItemHolder(holder.itemView)
                val childBean: AssetDetailsChildBean.DatasBean? = item?.getChildBean()
                if (UserConfig.singleton?.languageTag === 1) {
                    childItemHolder.typeTv?.text = childBean?.operaterTypeZn
                } else {
                    childItemHolder.typeTv?.text = childBean?.operaterTypeEn
                }
                var txHash: String? = childBean?.extrinsicHash
                if (txHash != null && txHash.length > 35) txHash = txHash.substring(0, 35) + "..."
                childItemHolder.txHashSpt?.setLeftString(txHash ?: "")
                var rechargeAddr: String? = childBean?.investAddress
                if (rechargeAddr != null && rechargeAddr.length > 35) rechargeAddr =
                    rechargeAddr.substring(0, 35) + "..."
                childItemHolder.rechargeAddrSpt
                    ?.setLeftString(rechargeAddr ?: "")
                childItemHolder.timeSpt?.setLeftString(
                    TimeUtils.normalTimeStampToData(
                        childBean?.createTime.toString() + ""
                    )
                )
                    childItemHolder.valueSpt
                        ?.setLeftString(BigDecimalUtils.roundDOWN(childBean?.amount, 4))
                //TODO 设置矿工费用
//                childItemHolder.minerFee
//                    ?.setLeftString(BigDecimalUtils.roundDOWN(childBean?.amount, 4))
                //设置下方操作类型
                if (UserConfig.singleton?.languageTag === 1) {
                    childItemHolder.stausSpt?.setLeftString (childBean?.operaterTypeZn)
                } else {
                    childItemHolder.stausSpt?.setLeftString ( childBean?.operaterTypeEn)
                }
               // 是显示备注信息
//                if (childBean?.operaterTypeZn.equals("转入") || childBean?.operaterTypeZn
//                        .equals("转出")
//                ) {
                    childItemHolder.remarkSpt?.visibility = View.VISIBLE
                    childItemHolder.remarkSpt?.setLeftString(childBean?.memo)
//                } else {
//                    childItemHolder.remarkSpt?.visibility = View.GONE
//                }
                childItemHolder.txHashSpt?.setRightImageViewClickListener { imageView ->
                    APPHelper.copy(
                        mContext as Activity,
                        childBean?.extrinsicHash
                    )
                }
                childItemHolder.rechargeAddrSpt?.setRightImageViewClickListener { imageView ->
                    APPHelper.copy(
                        mContext as Activity,
                        childBean?.investAddress
                    )
                }
            }
        }
    }
}