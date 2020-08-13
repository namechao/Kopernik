package com.kopernik.ui.setting.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.ui.setting.entity.InviteBean
import com.kopernik.ui.setting.viewholder.InviteAddressViewHolder

class InviteAddressAdapter(private val type: Int) :
    BaseQuickAdapter<InviteBean.DatasBean, InviteAddressViewHolder>(R.layout.item_invite_address) {
    private val ACCOUNT_INVITE_ACCOUNT = 1
    private val NODE_INVITE_ACCOUNT = 2
    private val NODE_INVITE_NODE = 3
    override fun convert(
        helper: InviteAddressViewHolder,
        item: InviteBean.DatasBean
    ) {
        if (type == NODE_INVITE_NODE) {
            helper.accountNameSpt?.setLeftString(item.name)
            helper.accountAddrSpt?.setLeftString(item.nodeHash)
        } else {
            helper.accountNameSpt?.setLeftString(item.acountLabel)
            helper.accountAddrSpt?.setLeftString(item.acountHash)
        }
        helper.gainSpt?.setLeftString(item.gains.toString() + "")
    }

}