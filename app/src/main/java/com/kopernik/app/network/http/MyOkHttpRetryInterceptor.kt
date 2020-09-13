package com.kopernik.app.network.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.InterruptedIOException

class MyOkHttpRetryInterceptor : Interceptor {
    var executionCount = 3//最大重试次数
    private val retryInterval: Long = 1000 //重试的间隔
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request: Request = chain.request()
        var response = doRequest(chain, request)
        var retryNum = 0
        while ((response == null || !response.isSuccessful) && retryNum <= executionCount) {
            val nextInterval: Long = getRetryInterval()
            try {

                Thread.sleep(nextInterval)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                throw InterruptedIOException()
            }
            retryNum++
            // retry the request
            response = doRequest(chain, request)
        }
        return response;
    }
    private fun doRequest(
        chain: Interceptor.Chain,
        request: Request
    ): Response? {
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
        }
        return response
    }

    /**
     * retry间隔时间
     */
    fun getRetryInterval(): Long {
        return retryInterval
    }

}