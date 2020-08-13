package com.kopernik.app.network.http

import com.kopernik.app.config.UserConfig
import com.kopernik.data.api.Api
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 所有接口添加公共参数 拦截器
 * */
class CommonParametersIntercepter : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var chain = chain
        //获取到request
        var request = chain.request()
        //获取url
        val url = request.url().toString()
        if (url.contains(Api.baseUrl)) {
            // 封装公共参数
            var token="8Db50d812b2568acfbc818d82d837551ae8d93b02ff8ef7b58c4b0f9289277b7"
            if (UserConfig.singleton!=null){
                UserConfig.singleton!!.token?.let {
                    token=it
                }
            }
            var publicMap = mapOf("apptype" to "android", "token" to  token)
            //判断请求 get还是post
            if (request.method() == "GET") {
                //非空判断
                val builder = StringBuilder(url)
                //拼接公共请求参数
                publicMap.entries.forEach {
                    builder.append("&" + it.key + "=" + it.value)
                }
                var surl = builder.toString()
                //判断地址有没有？没有则添加
                if (!surl.contains("?")) {
                    surl = surl.replaceFirst("&".toRegex(), "?")
                }
                //根据老的生成新的
                val request1 = request.newBuilder()
                        .url(surl)
                        .build()
                return chain.proceed(request1)
            } else {
                val body = request.body()
                if (body != null && body is FormBody) {
                    val formBody = body as FormBody
                    //把原来的body添加到新body里
                    val sbuilder = FormBody.Builder()
                    //为了防止添加重复的key 和 value
                    val hashMap = hashMapOf<String, Any>()
                    for (i in 0 until formBody.size()) {
                        sbuilder.add(formBody.encodedName(i), formBody.encodedValue(i))
                        hashMap[formBody.encodedName(i)] = formBody.encodedValue(i)
                    }
                    //把公共参数添加到新的body中
                    publicMap.entries.forEach {
                        if (!hashMap.containsKey(it.key)) {
                            sbuilder.add(it.key, it.value)
                        }
                    }
                    val sformBody = sbuilder.build()
                    //根据老的生成新的
                    val request1 = request.newBuilder()
                            .post(sformBody)
                            .build()
                    return chain.proceed(request1)
                }
            }
        }
        return chain.proceed(request)
    }
}
