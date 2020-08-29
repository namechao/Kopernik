package com.kopernik.app.network.http

import android.content.Context
import com.kopernik.R
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import java.util.*

object ErrorCode {
    private val msg = HashMap<Int, String>()
    fun initErrorMsg(context: Context) {
        msg[400] = context.getString(R.string.error_400)
        msg[401] = context.getString(R.string.error_401)
        msg[402] = context.getString(R.string.error_402)
        msg[403] = context.getString(R.string.error_403)
        msg[404] = context.getString(R.string.error_404)
        msg[405] = context.getString(R.string.error_405)
        msg[406] = context.getString(R.string.error_406)
        msg[407] = context.getString(R.string.error_407)
        msg[408] = context.getString(R.string.error_408)
        msg[409] = context.getString(R.string.error_409)
        msg[410] = context.getString(R.string.error_410)
        msg[412] = context.getString(R.string.error_412)
        msg[413] = context.getString(R.string.error_413)
        msg[414] = context.getString(R.string.error_414)
        msg[415] = context.getString(R.string.error_415)
        msg[416] = context.getString(R.string.error_416)
        msg[417] = context.getString(R.string.error_417)
        msg[600] = context.getString(R.string.error_600)

    }

    fun showErrorMsg(context: Context?, code: Int) {
        if (context == null) return
        initErrorMsg(context)
        val text = msg[code]
        ToastUtils.showShort(
            context,
            if (StringUtils.isEmpty(text)) context.getString(R.string.error_4) else text
        )
    }
}