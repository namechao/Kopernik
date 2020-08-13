package com.kopernik.app.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.kopernik.R
import java.util.*

object APPHelper {
    fun dp2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
    @JvmStatic
    fun copy(ac: Activity, text: String?) {
        val cm =
            ac.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("yut", text)
        cm.setPrimaryClip(mClipData)
        ToastUtils.showShort(ac, ac.getString(R.string.copy_done))
    }

    fun isInstallApp(context: Context, packName: String): Boolean {
        val packageManager = context.packageManager
        val pinfo =
            packageManager.getInstalledPackages(0)
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn =
                    pinfo[i].packageName.toLowerCase(Locale.ENGLISH)
                if (pn == packName) {
                    return true
                }
            }
        }
        return false
    }
}