package com.kopernik.ui.login

import android.content.Context
import com.google.android.flexbox.FlexboxLayoutManager


class NoMoveFlexboxLayoutManager(context: Context?) :
    FlexboxLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}
