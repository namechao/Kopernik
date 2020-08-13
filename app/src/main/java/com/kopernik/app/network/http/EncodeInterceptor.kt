package com.kopernik.app.network.http

import com.kopernik.app.config.UserConfig
import okhttp3.Interceptor
import okhttp3.Response


class EncodeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token =if (UserConfig.singleton?.token==null) "" else UserConfig.singleton?.token
        val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("apptype", "android")
                .addHeader("token", token)
                .build()
        return chain.proceed(request)
    }
}