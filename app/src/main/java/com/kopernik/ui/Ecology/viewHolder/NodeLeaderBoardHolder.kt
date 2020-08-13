package com.kopernik.ui.Ecology.viewHolder

import android.view.View
import android.widget.TextView
import com.allen.library.SuperButton
import com.chad.library.adapter.base.BaseViewHolder
import com.kopernik.R


class NodeLeaderBoardHolder(view: View?) : BaseViewHolder(view) {
    var rankingTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.ranking_tv)
            }
            return field
        }
        private set
    var nameTv: TextView? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.name_tv)
            }
            return field
        }
        private set
    var accumulatedPointsTv: TextView? = null
        get() {
            if (field == null) {
                field =
                    itemView.findViewById(R.id.accumulated_points_tv)
            }
            return field
        }
        private set
    var superSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.super_spb)
            }
            return field
        }
        private set
    var genesisSpb: SuperButton? = null
        get() {
            if (field == null) {
                field = itemView.findViewById(R.id.genesis_spb)
            }
            return field
        }
        private set

}