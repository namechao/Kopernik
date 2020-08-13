package com.kopernik.ui.Ecology.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.Ecology.entity.MyVoteBean
import com.kopernik.ui.Ecology.viewHolder.MyVoteViewHolder


class MyVoteAdapter(layoutResId: Int) :
    BaseQuickAdapter<MyVoteBean.DatasBean, MyVoteViewHolder>(layoutResId) {
    override fun convert(
        helper: MyVoteViewHolder,
        item: MyVoteBean.DatasBean
    ) {
        helper.logoIv?.context?.let { Glide.with(it).load(item.imgurl).into(helper?.logoIv!!) }
        helper.nameTv?.text = item.name
        helper.userAddressSpt?.setLeftString(item.address)
        helper.selfBondedSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.selfBonded, 4))
        helper.totalVoteSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.totalBonded, 4))
        helper.jackpotBalanceSpt
            ?.setLeftString(BigDecimalUtils.roundDOWN(item.jackpotBalance, 4))
        helper.myVoteSpt
            ?.setLeftString(BigDecimalUtils.roundDOWN(item.vate?.myNominations, 4))
        helper.voteAccrualSpt
            ?.setLeftString(BigDecimalUtils.roundDOWN(item.vate?.unclaimed, 4))
        if (item.isSelfNode) {
            helper.ownSpb?.visibility = View.VISIBLE
        } else {
            helper.ownSpb?.visibility = View.GONE
        }
        if (item.nodeStatus?.toLowerCase().equals("quit")) {
            helper.voteSpb?.visibility = View.GONE
            helper.statusSpb?.visibility = View.VISIBLE
        } else {
            helper.voteSpb?.visibility = View.VISIBLE
            helper.statusSpb?.visibility = View.GONE
        }
        helper.addOnClickListener(R.id.node_vote_spb)
            .addOnClickListener(R.id.vote_redeem_spb).addOnClickListener(R.id.vote_turn_spb)
            .addOnClickListener(R.id.vote_deduct_spb)
    }
}