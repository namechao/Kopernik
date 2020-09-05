package com.kopernik.app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.LocalServerSocket
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.lang.reflect.Proxy


/**
 *
 * @ProjectName:    Kopernik
 * @Package:        com.kopernik.app.utils
 * @ClassName:      ApplicationCheck
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/8/19 1:25 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/8/19 1:25 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class ApplicationCheck(val context: Context) {

     fun checkPkg(): Boolean {
        try {
            if (context == null) {
                return false
            }
            var count = 0
            val packageName: String = context.packageName
            val pm: PackageManager = context.packageManager
            val pkgs =
                pm.getInstalledPackages(0)
            for (info in pkgs) {
                if (packageName == info.packageName) {
                    count++
                }
            }
            return count > 1
        } catch (ignore: Exception) {
        }
        return false
    }
    fun checkFileDir(context: Context):Boolean{
        return !context.filesDir.path.contains("com.kopernik")
    }


    fun checkProxy():Boolean{
        try {
            var v0_2 = Class.forName("android.app.ActivityThread")
            var v13 = v0_2.getDeclaredMethod("currentActivityThread").invoke(null)
            var v0_3 = v0_2.getDeclaredField("sPackageManager")
            v0_3.isAccessible = true
            if(Proxy.isProxyClass(v0_3.get(v13).javaClass)) {
                return true
            }

        }catch (e:Exception){
            e.stackTrace
        }
        return false
    }

     fun check(): Boolean {
         var virtualPkgs= hashSetOf("com.excelliance.dualaid","com.ft.mapp","com.droi.adocker.pro","com.qihoo.magic","com.lbe.parallel")
         var bufr: BufferedReader? = null
        try {
            bufr = BufferedReader(FileReader("/proc/self/maps"))
            var line: String
            while (bufr.readLine().also { line = it } != null) {
                for (pkg in virtualPkgs!!) {
                    if (line.contains(pkg)) {
                        return true
                    }
                }
            }
        } catch (ignore: java.lang.Exception) {
        } finally {
            if (bufr != null) {
                try {
                    bufr.close()
                } catch (e: IOException) {
                }
            }
        }
        return false
    }

}