package com.kopernik.ui.account.bean

import java.util.*

class AccountListBean {
    var accounts: MutableList<AccountBean>? = null
        get() {
            if (field == null) field = ArrayList()
            return field
        }
    var behaviorAccounts: MutableList<AccountBean>? = null
        get() {
            if (field == null) field = ArrayList()
            return field
        }

}