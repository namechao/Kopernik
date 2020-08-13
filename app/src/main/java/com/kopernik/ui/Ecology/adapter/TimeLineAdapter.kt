package com.kopernik.ui.Ecology.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.Ecology.entity.TimeLineBean
import com.kopernik.ui.Ecology.entity.TimeLineItemBean
import com.kopernik.ui.Ecology.viewHolder.TimeLienViewHolder


class TimeLineAdapter(data: MutableList<TimeLineItemBean>, type: Int) :
    BaseMultiItemQuickAdapter<TimeLineItemBean, BaseViewHolder>(data) {
    private val type //1 赎回  2取消映射
            : Int

    protected override fun convert(holder: BaseViewHolder, item: TimeLineItemBean) {
        when (holder.itemViewType) {
            TimeLineItemBean.TYPE_HEADER -> holder.setText(R.id.head_title_tv, item?.titleName)
            TimeLineItemBean.TYPE_DATA -> {
                val itemBean: TimeLineBean.DatasBean? = item?.item
                val viewHolder = TimeLienViewHolder(holder.itemView)
                viewHolder.roundedImageView?.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.dot_bg
                    )
                )
                viewHolder.desc2Spt?.setLeftString(mContext.getString(R.string.freeze_time))
                    ?.setRightString(itemBean?.dayFree)
                viewHolder.desc3Spt?.setLeftString(mContext.getString(R.string.unlock_time))
                    ?.setRightString(itemBean?.unlocktime)
                if (type == 1) {
                    viewHolder.timeSpt?.setLeftString(
                        TimeUtils.timeStampToYear(
                            itemBean?.time?.toString() + ""
                        )
                    )
                        ?.setCenterString(
                            TimeUtils.timeStampToTime(
                                itemBean?.time?.toString() + ""
                            )
                        )
                    viewHolder.desc1Spt
                        ?.setLeftString(mContext.getString(R.string.title_vote_count))
                        ?.setRightString(
                            BigDecimalUtils.roundDOWN(itemBean?.myNominations, 4)
                                .toString() + "UYT"
                        )
                } else if (type == 2) {
                    val chainType: String? = itemBean?.assetsType?.toLowerCase()
                    if (chainType == "dns") {
                        viewHolder.timeSpt?.setLeftString(
                            TimeUtils.timeStampToYear(
                                itemBean.createTime.toString() + ""
                            )
                        )
                            ?.setCenterString(
                                TimeUtils.timeStampToTime(
                                    itemBean.createTime.toString() + ""
                                )
                            )
                    } else {
                        if (chainType == "kopernik") {
                            viewHolder.timeSpt?.setLeftString(
                                TimeUtils.timeStampToYear(
                                    itemBean.time.toString() + ""
                                )
                            )
                                ?.setCenterString(
                                    TimeUtils.timeStampToTime(
                                        itemBean.time.toString() + ""
                                    )
                                )
                        } else {
                            viewHolder.timeSpt?.setLeftString(
                                TimeUtils.timeStampToYear(
                                    itemBean?.createTime.toString() + ""
                                )
                            )
                                ?.setCenterString(
                                    TimeUtils.timeStampToTime(
                                        itemBean?.createTime.toString() + ""
                                    )
                                )
                        }
                    }
                    when (chainType) {
                        "btc" -> {
                            viewHolder.desc1Spt
                                ?.setLeftString(mContext.getString(R.string.title_asset_map_count))
                                ?.setRightString(
                                    BigDecimalUtils.roundDOWN(itemBean.amount, 8)
                                        .toString() + "U-BTC"
                                )
                        }
                        "eth" -> {
                            viewHolder.desc1Spt
                                ?.setLeftString(mContext.getString(R.string.title_asset_map_count))
                                ?.setRightString(
                                    BigDecimalUtils.roundDOWN(itemBean.amount, 8)
                                        .toString() + "U-ETH"
                                )
                        }
                        "usdt" -> {
                            viewHolder.desc1Spt
                                ?.setLeftString(mContext.getString(R.string.title_asset_map_count))
                                ?.setRightString(
                                    BigDecimalUtils.roundDOWN(itemBean.amount, 6)
                                        .toString() + "U-USDT"
                                )
                        }
                        "dns" -> {
                            viewHolder.desc1Spt
                                ?.setLeftString(mContext.getString(R.string.title_asset_map_count))
                                ?.setRightString(
                                    BigDecimalUtils.roundDOWN(
                                        itemBean.myNominations,
                                        4
                                    ).toString() + "UYT"
                                )
                        }
                        "ht" -> {
                            viewHolder.desc1Spt
                                ?.setLeftString(mContext.getString(R.string.title_asset_map_count))
                                ?.setRightString(
                                    BigDecimalUtils.roundDOWN(itemBean.amount, 4)
                                        .toString() + "U-HT"
                                )
                        }
                    }
                }
            }
        }
    }

    init {
        addItemType(TimeLineItemBean.TYPE_HEADER, R.layout.item_time_line_head)
        addItemType(TimeLineItemBean.TYPE_DATA, R.layout.item_time_line)
        this.type = type
    }
}