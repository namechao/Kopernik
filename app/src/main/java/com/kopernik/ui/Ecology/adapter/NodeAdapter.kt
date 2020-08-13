package com.kopernik.ui.Ecology.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.Ecology.entity.NodeBean
import com.kopernik.ui.Ecology.viewHolder.NodeViewHolder
import com.kopernik.app.utils.BigDecimalUtils


class NodeAdapter(layoutResId: Int) :
    BaseQuickAdapter<NodeBean.DatasBean, NodeViewHolder>(layoutResId) {
    override fun convert(helper: NodeViewHolder, item: NodeBean.DatasBean) {
        helper?.logoIv?.context?.let { Glide.with(it).load(item.imgurl).into(helper.logoIv!!) }
        helper.nameTv?.text = item.name
        if (UserConfig.singleton?.getAccount() == null) {
            helper.voteSpb?.visibility = View.INVISIBLE
        } else {
            helper.voteSpb?.visibility = View.VISIBLE
        }
        if (item.isSelfNode) {
            helper.ownSpb?.visibility = View.VISIBLE
        } else {
            helper.ownSpb?.visibility = View.GONE
        }
        if (item.recommendNode == null || item.recommendNode!!.isEmpty()) {
            helper.statusSpb?.text = mContext.getString(R.string.creation)
            helper.statusSpb?.visibility = View.VISIBLE
            helper.rootView?.background = ContextCompat.getDrawable(mContext, R.drawable.verify_node_bg)
            helper.statusSpb?.setShapeSelectorDisableColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.super_node_status
                )
            )?.setUseShape()
            helper.statusSpb
                ?.setTextColor(mContext.resources.getColor(R.color.white_normal))
            helper.voteSpb
                ?.setTextColor(ContextCompat.getColor(mContext, R.color.super_node_vote_text))
            helper.voteSpb?.setShapeStrokeColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.super_node_btn_stroke
                )
            )
                ?.setShapeSelectorNormalColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_bg
                    )
                )?.setUseShape()
            helper.selfBondedSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.super_node_bg
                )
            )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_bg
                    )
                )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_content
                    )
                )?.useShape()
            helper.totalVoteSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.super_node_bg
                )
            )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_bg
                    )
                )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_content
                    )
                )?.useShape()
            helper.jackpotBalanceSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.super_node_bg
                )
            )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_bg
                    )
                )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.super_node_content
                    )
                )?.useShape()
        } else {
            if (item.nodeStatus?.toLowerCase().equals("quit")) {
                helper.statusSpb?.text = mContext.getString(R.string.quit)
                helper.voteSpb?.visibility = View.GONE
                helper.statusSpb?.visibility = View.VISIBLE
            } else {
                helper.voteSpb?.visibility = View.VISIBLE
                helper.statusSpb?.visibility = View.INVISIBLE
            }
            if (UserConfig.singleton?.getAccount() == null) {
                helper.voteSpb?.visibility = View.INVISIBLE
            } else {
                helper.voteSpb?.visibility = View.VISIBLE
            }
            //普通节点
            helper.rootView?.background = ContextCompat.getDrawable(mContext, R.drawable.normal_node_bg)
            helper.statusSpb?.setShapeSelectorDisableColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.node_quit_bg
                )
            )?.setUseShape()
            helper.statusSpb
                ?.setTextColor(ContextCompat.getColor(mContext, R.color.white_normal))
            helper.voteSpb
                ?.setTextColor(ContextCompat.getColor(mContext, R.color.normal_node_vote_text))
            helper.voteSpb?.setShapeStrokeColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.normal_node_btn_stroke
                )
            )
                ?.setShapeSelectorNormalColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_bg
                    )
                )?.setUseShape()
            helper.selfBondedSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.normal_node_content_bg
                )
            )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_title
                    )
                )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_content_bg
                    )
                )
                ?.useShape()
            helper.totalVoteSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.normal_node_content_bg
                )
            )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_title
                    )
                )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_content_bg
                    )
                )
                ?.useShape()
            helper.jackpotBalanceSpt?.setShapeSelectorNormalColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.normal_node_content_bg
                )
            )
                ?.setLeftTopTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_title
                    )
                )
                ?.setShapeSelectorPressedColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.normal_node_content_bg
                    )
                )
                ?.useShape()
        }
        helper.selfBondedSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.selfBonded, 4))
        helper.totalVoteSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.totalBonded, 4))
        helper.jackpotBalanceSpt
            ?.setLeftString(BigDecimalUtils.roundDOWN(item.jackpotBalance, 4))
        helper.addOnClickListener(R.id.node_vote_spb)
    }
}