package com.kopernik.app.utils

import android.util.Log
import com.kopernik.app.config.AppConfig


object DBLog {
    var TAG = "ln"
    fun ln(message: String) {
        if (AppConfig.logEnable) print(message)
    }

    fun spiltInfo(message: String) {
        if (AppConfig.logEnable) print("===>>>$message")
    }

    fun error(message: String) {
        if (AppConfig.logEnable) print("error=>$message")
    }

    private fun print(bodyMsg: String) {
        if (bodyMsg.length > 4000) {
            var i = 0
            while (i < bodyMsg.length) {
                if (i + 4000 < bodyMsg.length) {
                    Log.i(TAG, bodyMsg.substring(i, i + 4000))
                } else {
                    Log.i(TAG, bodyMsg.substring(i, bodyMsg.length))
                }
                i += 4000
            }
        } else {
            Log.i(TAG, bodyMsg)
        }
    }
}