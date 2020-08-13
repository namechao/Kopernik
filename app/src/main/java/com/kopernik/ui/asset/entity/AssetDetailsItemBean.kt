package com.kopernik.ui.asset.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class AssetDetailsItemBean : MultiItemEntity {
    private var itemType: Int
    var titleName: String? = null
    var info: AssetBean? = null
    private var childBean: AssetDetailsChildBean.DatasBean? = null

    //资产信息
    constructor(itemType: Int, info: AssetBean?) {
        this.itemType = itemType
        this.info = info
    }

    //item信息
    constructor(itemType: Int, childBean: AssetDetailsChildBean.DatasBean?) {
        this.itemType = itemType
        this.childBean = childBean
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

    fun getChildBean(): AssetDetailsChildBean.DatasBean? {
        return childBean
    }

    fun setChildBean(childBean: AssetDetailsChildBean.DatasBean?) {
        this.childBean = childBean
    }

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_INFO = 2
        const val TYPE_ITEM = 3
        const val TYPE_EMPTY = 4
    }
}