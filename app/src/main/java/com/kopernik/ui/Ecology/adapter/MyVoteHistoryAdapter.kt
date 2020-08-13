package com.kopernik.ui.Ecology.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.app.config.UserConfig
import com.kopernik.app.utils.TimeUtils
import com.kopernik.ui.Ecology.entity.VoteHistoryBean
import com.kopernik.ui.Ecology.viewHolder.VoteHistoryViewHolder
import com.kopernik.app.utils.BigDecimalUtils


class MyVoteHistoryAdapter(layoutResId: Int) :
    BaseQuickAdapter<VoteHistoryBean.DatasBean, VoteHistoryViewHolder>(layoutResId) {
    override fun convert(
        helper: VoteHistoryViewHolder,
        item: VoteHistoryBean.DatasBean
    ) {
        if (UserConfig.singleton?.languageTag == 1) {
            helper.operatingSpt?.setLeftString(item.operaterTypeZn)
        } else {
            helper.operatingSpt?.setLeftString(item.operaterTypeEn)
        }
        helper.timeSpt
            ?.setLeftString(TimeUtils.normalTimeStampToData(item.time.toString() + ""))
        helper.nodeAddressSpt?.setLeftString(item.nodeHash)
        if (item.type?.toUpperCase().equals("CLAIM")) {
            //提息
            helper.valueSpt?.setLeftString(BigDecimalUtils.roundDOWN(item.unclaimed, 4))
        } else if (item.type?.toUpperCase().equals("VOTE") || item.type?.toUpperCase()
                .equals("SWITCH_TO")
        ) {
            //投票
            helper.valueSpt
                ?.setLeftString(BigDecimalUtils.roundDOWN(item.myNominations, 4))
        } else if (item.type?.toUpperCase() == "REDEEM") {
            helper.valueSpt
                ?.setLeftString(BigDecimalUtils.roundDOWN(item.unfreezeReserved, 4))
        }
    }
}