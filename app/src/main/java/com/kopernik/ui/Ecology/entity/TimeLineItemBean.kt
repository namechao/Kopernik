package com.kopernik.ui.Ecology.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class TimeLineItemBean : MultiItemEntity {
    private var itemType: Int
    var titleName: String? = null
    var item: TimeLineBean.DatasBean? = null

    constructor(itemType: Int, item: TimeLineBean.DatasBean?) {
        this.itemType = itemType
        this.item = item
    }

    constructor(itemType: Int) {
        this.itemType = itemType
    }

    constructor(itemType: Int, titleName: String?) : this(itemType) {
        this.titleName = titleName
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_DATA = 2
    }
}