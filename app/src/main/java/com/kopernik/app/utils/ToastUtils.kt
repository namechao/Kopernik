package com.kopernik.app.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    fun showShort(context: Context?, msg: String?) {
        if (context == null) return
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}