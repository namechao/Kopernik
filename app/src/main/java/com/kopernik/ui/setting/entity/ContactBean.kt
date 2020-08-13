package com.kopernik.ui.setting.entity

class ContactBean {
    var addresslist: List<AddresslistBean>? = null

    class AddresslistBean {
        /**
         * id : 49
         * acountHash : 8d8dc8d78d3bcf7caef7c5a8d7d2484b486f
         * addressHash : 8d8dae388294d5a7a9538407e449fa2ad6b0
         * label :
         * chain : XUT
         */
        var id = 0
        var acountHash: String? = null
        var addressHash: String? = null
        var label: String? = null
        var chain: String? = null

    }
}