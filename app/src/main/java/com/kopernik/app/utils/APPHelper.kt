package com.kopernik.app.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
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

    /**
     * 获取当前本地apk的版本号
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        try {
            // 获取apk版本号
            versionCode =
                context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取版本号名称
     *
     * @param context
     * @return
     */
    fun getVerName(context: Context): String? {
        var verName: String? = ""
        try {
            verName =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verName
    }
}