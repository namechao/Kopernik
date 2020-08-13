package com.kopernik.ui.Ecology.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.ui.Ecology.entity.NodeLeaderBoardBean
import com.kopernik.ui.Ecology.viewHolder.NodeLeaderBoardHolder

class NodeLeaderBoardAdapter(layoutResId: Int) :
    BaseQuickAdapter<NodeLeaderBoardBean.DatasBean, NodeLeaderBoardHolder>(layoutResId) {
    override fun convert(
        helper: NodeLeaderBoardHolder,
        item: NodeLeaderBoardBean.DatasBean
    ) {
        helper.rankingTv?.text = (helper.adapterPosition + 1).toString()
        helper.nameTv?.text = item.name
        helper.accumulatedPointsTv?.text = item.score.toString()
        if (item.superNode == 1) {
            helper.superSpb?.visibility = View.VISIBLE
        } else {
            helper.superSpb?.visibility = View.GONE
        }
        if (item.recommendNode == null || item.recommendNode!!.isEmpty()) {
            helper.genesisSpb?.visibility = View.VISIBLE
        } else {
            helper.genesisSpb?.visibility = View.GONE
        }
    }
}