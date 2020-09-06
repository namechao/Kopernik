package com.kopernik.app.network

import SingleLoginInterceptor
import com.aleyn.mvvm.network.interceptor.Level
import com.aleyn.mvvm.network.interceptor.LoggingInterceptor
import com.kopernik.app.config.AppConfig
import com.kopernik.app.network.http.CommonParametersIntercepter
import com.kopernik.data.api.Api
import com.kopernik.app.network.http.EncodeInterceptor
import com.kopernik.app.network.http.TrustAllCerts
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

/**
 *
 * @ProjectName:    Uyt
 * @Package:        com.kopernik.app.network
 * @ClassName:      RetrofitClient
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/15 3:50 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/15 3:50 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class RetrofitClient {
    companion object{
        fun getInstance()=SingletonHolder.INSTANCE
        private lateinit var retrofit:Retrofit;

    }
    private  object SingletonHolder{
      val  INSTANCE=RetrofitClient()
    }
    init {
             retrofit=Retrofit.Builder()
                 .baseUrl(Api.baseUrl)
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(getOkHttpClient())
                 .build()
    }
    open fun getOkHttpClient(): OkHttpClient {
        //log相关
        //log相关
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //log打印级别，决定了log显示的详细程度

        return OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addNetworkInterceptor(LoggingInterceptor().apply {
                isDebug = AppConfig.logEnable
                level = Level.BASIC
                type = Platform.INFO
                requestTag = "Request"
                requestTag = "Response"
            })
//            .addInterceptor(CommonParametersIntercepter())
            .sslSocketFactory(createSSLSocketFactory(),TrustAllCerts())
            .addInterceptor(EncodeInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(SingleLoginInterceptor())
            .writeTimeout(20L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
    }
    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ssfFactory
    }
    fun <T> create(service: Class<T>?): T =
        retrofit.create(service!!) ?: throw RuntimeException("Api service is null!")
}