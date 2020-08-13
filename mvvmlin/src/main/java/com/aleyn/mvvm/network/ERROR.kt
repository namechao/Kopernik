package com.aleyn.mvvm.network

import com.aleyn.mvvm.R
import com.blankj.utilcode.util.Utils

/**
 *   @auther : Aleyn
 *   time   : 2019/08/12
 */
enum class ERROR(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, Utils.getApp().getString(R.string.error_4)),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, Utils.getApp().getString(R.string.error_5)),

    /**
     * 网络错误
     */
    NETWORD_ERROR(1002, Utils.getApp().getString(R.string.error_1)),

    /**
     * 协议出错
     */
    HTTP_ERROR(1003, Utils.getApp().getString(R.string.error_3)),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, Utils.getApp().getString(R.string.error_2));

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}