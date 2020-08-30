package com.kopernik.app.utils

import com.kopernik.app.config.UserConfig
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun currentTime(): String {
        return (System.currentTimeMillis() / 1000).toString() + ""
    }

    fun currentTimeLong(): Long {
        return System.currentTimeMillis() / 1000
    }
    fun currentTimeMillisecond(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 10 位
     * @param timeStamp
     * @return
     */
    fun simpletTimeStampToData(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0") return "无"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(Date(java.lang.Long.valueOf(timeStamp + "000")))
    }

    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun normalTimeStampToData(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }
    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun normalTimeStampToMinute(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }
    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun normalTimeStampMonthDay(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("MM/dd HH:mm")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }
    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun TimeStampYearToData(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }
    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun getSpecialTime(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("yy/MM/dd")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }
    /**
     * 13 位
     * @param timeStamp
     * @return
     */
    fun getHour(): String {

        val format = SimpleDateFormat("HH")
        return format.format(Date())
    }
    /**
     * 13 位
     * @param timeStamp
     * @return x月x日
     */
    fun timeStampToYear(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format: SimpleDateFormat = if (UserConfig.singleton?.languageTag === 1) {
            SimpleDateFormat("MM月dd日")
        } else {
            SimpleDateFormat("MM-dd")
        }
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }

    /**
     * 13 位
     * @param timeStamp
     * @return x:x:x
     */
    fun timeStampToTime(timeStamp: String?): String {
        if (timeStamp == null || timeStamp == "0" || timeStamp.isEmpty()) return "无"
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(Date(java.lang.Long.valueOf(timeStamp)))
    }

    fun isPingTimeOut(pingTime: Long): Boolean {
        if (pingTime == 0L) return false
        return currentTimeLong() - pingTime > 40
    }

    fun currentDate(): String {
        var temp_str = ""
        val dt = Date()
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        temp_str = sdf.format(dt)
        return temp_str
    }

    /**
     * 返回 1小时23分钟32秒 格式
     * @param ms
     * @return
     */
    fun timeParse(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24
        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss
        val sb = StringBuffer()
        if (day > 0) {
            sb.append("$day 天 ")
        }
        if (hour > 0) {
            sb.append("$hour 小时 ")
        }
        if (minute > 0) {
            sb.append("$minute 分钟 ")
        }
        if (second > 0) {
            sb.append("$second 秒")
        }
        return sb.toString()
    }

    /**
     * 返回 1:23:32 格式
     * @param ms
     * @return
     */
    fun timeParse2(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24
        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss
        val sb = StringBuffer()
        if (day > 0) {
            sb.append("$day : ")
        }
        if (hour > 0) {
            sb.append("$hour : ")
        }
        if (minute > 0) {
            sb.append("$minute : ")
        }
        if (second > 0) {
            sb.append(second)
        }
        return sb.toString()
    }
}