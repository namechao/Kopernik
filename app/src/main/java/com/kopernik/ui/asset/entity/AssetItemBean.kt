package com.kopernik.ui.asset.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class AssetItemBean(private var itemType: Int) : MultiItemEntity {
    var tokenName: String? = null
    var balance: String? = null
    var freeze: String? = null
    var cny: String? = null
    var logoResId = 0
    var titleName: String? = null


    constructor(itemType: Int, titleName: String?) : this(itemType) {
        this.titleName = titleName
    }

    override fun getItemType(): Int {
        return itemType
    }

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_DATA = 2
    }

}
