package com.kopernik.app.network

import com.kopernik.app.network.http.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException

class ProgressInterceptor(progressListener: ProgressResponseBody.ProgressListener) : Interceptor {
    private val progressListener: ProgressResponseBody.ProgressListener = progressListener

    @NotNull
    @Throws(IOException::class)
    override fun intercept(@NotNull chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(
                originalResponse.body()?.let {
                    ProgressResponseBody(
                        chain.request().url().url().toString(),
                        it,
                        progressListener
                    )
                }
            )
            .build()
    }

}