package com.kopernik.app.network.http

import android.content.Context
import com.kopernik.R
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import java.util.*

object ErrorCode {
    private val msg = HashMap<Int, String>()
    fun initErrorMsg(context: Context) {
        msg[401] = context.getString(R.string.error_401)
        msg[402] = context.getString(R.string.error_402)
        msg[404] = context.getString(R.string.error_404)
        msg[406] = context.getString(R.string.error_406)
        msg[407] = context.getString(R.string.error_407)
        msg[409] = context.getString(R.string.error_409)
        msg[410] = context.getString(R.string.error_410)
        msg[411] = context.getString(R.string.error_411)
        msg[412] = context.getString(R.string.error_412)
        msg[415] = context.getString(R.string.error_415)
        msg[416] = context.getString(R.string.error_416)
        msg[418] = context.getString(R.string.error_418)
        msg[419] = context.getString(R.string.error_419)
        msg[500] = context.getString(R.string.error_500)
        msg[501] = context.getString(R.string.error_501)
        msg[502] = context.getString(R.string.error_502)
        msg[510] = context.getString(R.string.error_510)
        msg[700] = context.getString(R.string.error_700)
        msg[701] = context.getString(R.string.error_701)
        msg[702] = context.getString(R.string.error_702)
        msg[703] = context.getString(R.string.error_703)
        msg[704] = context.getString(R.string.error_704)
        msg[705] = context.getString(R.string.error_705)
        msg[706] = context.getString(R.string.error_706)
        msg[707] = context.getString(R.string.error_707)
        msg[708] = context.getString(R.string.error_708)
        msg[709] = context.getString(R.string.error_709)
        msg[710] = context.getString(R.string.error_710)
        msg[711] = context.getString(R.string.error_711)
        msg[800] = context.getString(R.string.error_800)
        msg[801] = context.getString(R.string.error_801)
        msg[802] = context.getString(R.string.error_802)
        msg[803] = context.getString(R.string.error_803)
        msg[804] = context.getString(R.string.error_804)
        msg[805] = context.getString(R.string.error_805)
        msg[806] = context.getString(R.string.error_806)
        msg[807] = context.getString(R.string.error_807)
        msg[808] = context.getString(R.string.error_808)
        msg[809] = context.getString(R.string.error_809)
        msg[810] = context.getString(R.string.error_810)
        msg[811] = context.getString(R.string.error_811)
        msg[814] = context.getString(R.string.error_814)
        msg[815] = context.getString(R.string.error_815)
        msg[816] = context.getString(R.string.error_816)
        msg[888] = context.getString(R.string.error_888)
        msg[1] = context.getString(R.string.error_1)
        msg[2] = context.getString(R.string.error_2)
        msg[3] = context.getString(R.string.error_3)
        msg[4] = context.getString(R.string.error_4)
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