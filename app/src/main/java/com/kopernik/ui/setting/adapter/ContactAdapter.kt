package com.kopernik.ui.setting.adapter


import android.app.Activity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kopernik.R
import com.kopernik.app.utils.APPHelper
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.viewholder.ContactViewHolder


class ContactAdapter(layoutResId: Int) :
    BaseQuickAdapter<ContactBean.AddresslistBean, ContactViewHolder>(layoutResId) {
    override fun convert(helper: ContactViewHolder, item: ContactBean.AddresslistBean) {
        helper.nameSpt.setLeftString(item.label)
        helper.chainSpt.setLeftString(item.chain)
        helper.addressTv.text = item.addressHash
        helper.addOnClickListener(R.id.modify_spb).addOnClickListener(R.id.del_spb)
        helper.copyIv.setOnClickListener {
            APPHelper.copy(
                mContext as Activity,
                item.addressHash
            )
        }
    }
}