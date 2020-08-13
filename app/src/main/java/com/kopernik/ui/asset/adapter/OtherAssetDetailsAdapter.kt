package com.kopernik.ui.asset.adapter

import android.app.Activity
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.asset.adapter.viewHolder.OtherAssetDetailsChildItemHolder
import com.kopernik.ui.asset.adapter.viewHolder.OtherAssetDetailsInfoHolder
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.AssetDetailsChildBean
import com.kopernik.ui.asset.entity.AssetDetailsItemBean
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils


class OtherAssetDetailsAdapter(
    data: MutableList<AssetDetailsItemBean>,
    private val type: Int
) : BaseMultiItemQuickAdapter<AssetDetailsItemBean, BaseViewHolder>(data) {
    private val DNS = 1
    private val BTC = 2
    private val ETH = 3
    private val USDT = 4
    private val HT = 5
    private var assetShow = true
    init {
        addItemType(AssetDetailsItemBean.TYPE_INFO, R.layout.item_other_asset_details_info)
        addItemType(AssetDetailsItemBean.TYPE_HEADER, R.layout.item_asset_details_head)
        addItemType(AssetDetailsItemBean.TYPE_ITEM, R.layout.item_other_asset_details_child_item)
        addItemType(AssetDetailsItemBean.TYPE_EMPTY, R.layout.item_empty_data)
        assetShow = UserConfig.singleton?.assetShow!!
    }

    override fun convert(holder: BaseViewHolder?, item: AssetDetailsItemBean?) {
        when (holder?.itemViewType) {
            //头部信息
            AssetDetailsItemBean.TYPE_INFO -> {
                val assetBean: AssetBean = item?.info!!
                val infoHolder = OtherAssetDetailsInfoHolder(holder?.itemView)
                    infoHolder.dnsTotalBalanceDiv?.visibility = View.GONE
                    infoHolder.otherViewLL?.visibility = View.VISIBLE
                    if (assetShow) {
                        if (type == BTC) {
                            //可用余额
                            infoHolder.amountTv?.setText(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalBtc?.balance, 8
                                )
                            )
                            //交易冻结
                            infoHolder.txFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalBtc?.transactionFreeze, 8
                                )
                            )
                            //挖矿冻结
                            infoHolder.otherMiningFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.btcmining,
                                    8
                                )
                            )
                            //TODO 待提利息
                            infoHolder.interestDrawn?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.btcminingGains,
                                    8
                                )
                            )
                            //资产算例
                            infoHolder.otherAssetCaclSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalBtc.miniingbalance,
                                    8
                                )
                            )
                        } else if (type == ETH) {
                            infoHolder.amountTv?.setText(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalEth?.balance, 8
                                )
                            )

                            infoHolder.txFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalEth?.transactionFreeze, 8
                                )
                            )
                            infoHolder.otherMiningFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.ethmining,
                                    8
                                )
                            )
                            infoHolder.interestDrawn?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.ethminingGains,
                                    8
                                )
                            )
                            infoHolder.otherAssetCaclSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalEth.miniingbalance,
                                    8
                                )
                            )
                        } else if (type == USDT) {
                            infoHolder.amountTv?.setText(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalUsdt?.balance, 6
                                )
                            )

                            infoHolder.txFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalUsdt?.transactionFreeze, 6
                                )
                            )
                            infoHolder.otherMiningFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.usdtmining,
                                    6
                                )
                            )
                            infoHolder.interestDrawn?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.usdtminingGains,
                                    6
                                )
                            )
                            infoHolder.otherAssetCaclSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalUsdt.miniingbalance,
                                    6
                                )
                            )
                        } else if (type == HT) {
                            infoHolder.amountTv?.setText(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalHt?.balance, 6
                                )
                            )

                            infoHolder.txFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalHt?.transactionFreeze, 6
                                )
                            )
                            infoHolder.otherMiningFreezeSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.htmining,
                                    6
                                )
                            )
                            infoHolder.interestDrawn?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.htminingGains,
                                    6
                                )
                            )
                            infoHolder.otherAssetCaclSpt?.setLeftString(
                                BigDecimalUtils.roundDOWN(
                                    assetBean.capitalHt.miniingbalance,
                                    6
                                )
                            )
                        }
                    } else {
                        infoHolder.amountTv?.setText("***")
                        infoHolder.txFreezeSpt?.setLeftString("*****")
                        infoHolder.otherMiningFreezeSpt?.setLeftString("*****")
                        infoHolder.interestDrawn?.setLeftString("*****")
                        infoHolder.otherAssetCaclSpt?.setLeftString("*****")
                    }

                infoHolder.eyeCb?.isChecked = assetShow
                infoHolder.eyeCb?.setOnCheckedChangeListener { buttonView, isChecked ->
                    assetShow = isChecked
                    UserConfig.singleton?.assetShow=isChecked
                    notifyDataSetChanged()
                }
                holder.addOnClickListener(R.id.extraction_mining_income)
            }
            AssetDetailsItemBean.TYPE_HEADER -> if (type == DNS) {
                holder.setVisible(R.id.choose_tv, false)
            } else {
                holder.setVisible(R.id.choose_tv, true)
                holder.setText(R.id.choose_tv, item?.titleName)
                holder.addOnClickListener(R.id.choose_tv)
            }
            //转账记录
            AssetDetailsItemBean.TYPE_ITEM -> {
                val childItemHolder =
                    OtherAssetDetailsChildItemHolder(holder.itemView)
                val childBean: AssetDetailsChildBean.DatasBean? = item?.getChildBean()
                if (UserConfig.singleton?.languageTag == 1) {
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

             if (type == BTC || type == ETH) {
                    childItemHolder.valueSpt
                        ?.setLeftString(BigDecimalUtils.roundDOWN(childBean?.amount, 8))
                } else {
                    childItemHolder.valueSpt
                        ?.setLeftString(BigDecimalUtils.roundDOWN(childBean?.amount, 6))
                }
                //状态控制 TODO 和接口改
                childItemHolder.stausSpt
                    ?.setLeftString(mContext.resources.getString(R.string.success))
                //控制备注显隐
                if (childBean?.operaterTypeZn.equals("转入") || childBean?.operaterTypeZn
                        .equals("转出")
                ) {
                    childItemHolder.remarkSpt?.visibility = View.VISIBLE
                    childItemHolder.remarkSpt?.setLeftString(childBean?.memo)
                } else {
                    childItemHolder.remarkSpt?.visibility = View.GONE
                }
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