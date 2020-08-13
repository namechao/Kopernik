package com.kopernik.ui.Ecology.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.Ecology.entity.ReferendumListBean
import com.kopernik.ui.Ecology.viewHolder.ReferendumViewHolder


class ReferendumAdapter(layoutResId: Int) :
    BaseQuickAdapter<ReferendumListBean.DatasBean, ReferendumViewHolder>(layoutResId) {
    override fun convert(
        helper: ReferendumViewHolder,
        item: ReferendumListBean.DatasBean
    ) {
        helper.titleTv?.text = item.title
        helper.validityPeriodSpt?.setLeftString(
            String.format(
                mContext.getString(R.string.ending_hint),
                item.endHeight.toString() + ""
            )
        )
        when (Integer.valueOf(item.status!!)) {
            0 -> {
                helper.statusSpb?.text = mContext.getString(R.string.processing)
                helper.statusSpb
                    ?.setTextColor(ContextCompat.getColor(mContext, R.color.processing_text))
                helper.statusSpb?.setShapeSelectorDisableColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.processing_bg
                    )
                )?.setUseShape()
                helper.titleTv?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_normal_title
                    )
                )
                helper.validityPeriodSpt?.setLeftTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_normal_validity_period
                    )
                )
            }
            1 -> {
                helper.statusSpb?.text = mContext.getString(R.string.in_force)
                helper.statusSpb
                    ?.setTextColor(ContextCompat.getColor(mContext, R.color.in_force_text))
                helper.statusSpb?.setShapeSelectorDisableColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.in_force_bg
                    )
                )?.setUseShape()
                helper.titleTv?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
                helper.validityPeriodSpt?.setLeftTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
            }
            2 -> {
                //议案失败
                helper.statusSpb?.text = mContext.resources.getString(R.string.failure)
                helper.statusSpb
                    ?.setTextColor(ContextCompat.getColor(mContext, R.color.fail_text))
                helper.statusSpb?.setShapeSelectorDisableColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.fail_bg
                    )
                )?.setUseShape()
                helper.titleTv?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
                helper.validityPeriodSpt?.setLeftTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
            }
            3 -> {
                //公投结束
                helper.statusSpb?.text = mContext.resources.getString(R.string.over)
                helper.statusSpb
                    ?.setTextColor(ContextCompat.getColor(mContext, R.color.fail_text))
                helper.statusSpb?.setShapeSelectorDisableColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.fail_bg
                    )
                )?.setUseShape()
                helper.titleTv?.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
                helper.validityPeriodSpt?.setLeftTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.referendum_fail_normal_title
                    )
                )
            }
        }
    }
}