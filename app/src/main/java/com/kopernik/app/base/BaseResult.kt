package com.pcl.mvvm.app.base

import com.aleyn.mvvm.base.IBaseResponse

/**
 *   @auther : Aleyn
 *   time   : 2019/11/01
 */
data class BaseResult<T>(
    val errorMsg: String,
    val status: Int,
    val data: T
) : IBaseResponse<T> {

    override fun code() = status

    override fun msg() = errorMsg

    override fun data() = data

    override fun isSuccess() = status == 0

}