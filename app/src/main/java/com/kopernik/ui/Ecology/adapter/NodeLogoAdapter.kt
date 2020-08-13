package com.kopernik.ui.Ecology.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.ui.Ecology.entity.NodeLogoListBean

/**
 *
 * @ProjectName:    Uyt
 * @Package:        com.kopernik.ui.Node.adapter
 * @ClassName:      NodeLogoAdapter
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/21 1:30 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/21 1:30 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

class NodeLogoAdapter(
        layoutResId: Int,
        @Nullable data: List<NodeLogoListBean?>?
    ) :
        BaseQuickAdapter<NodeLogoListBean, BaseViewHolder>(layoutResId, data) {
        override fun convert(
            helper: BaseViewHolder,
            item: NodeLogoListBean
        ) {
//            Glide.with(mContext).load(item.img)
//                .into(helper.getView<View>(R.id.node_logo_iv) as RoundedImageView)
//            if (choosePosition == helper.adapterPosition) {
//                helper.getView<View>(R.id.node_logo_select_iv)
//                    .setVisibility(View.VISIBLE)
//            } else {
//                helper.getView<View>(R.id.node_logo_select_iv)
//                    .setVisibility(View.INVISIBLE)
//            }
        }
    }
