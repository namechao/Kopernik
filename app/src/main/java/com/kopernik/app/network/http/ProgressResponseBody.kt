package com.kopernik.app.network.http

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException


class ProgressResponseBody internal constructor(
        url: String,
        private val responseBody: ResponseBody,
        progressListener: ProgressListener
    ) :
        ResponseBody() {
        private val progressListener: ProgressListener
        private var bufferedSource: BufferedSource? = null
        private val url: String
        override fun contentType(): MediaType? {
            return responseBody.contentType()
        }

        override fun contentLength(): Long {
            return responseBody.contentLength()
        }

        override fun source(): BufferedSource? {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()))
            }
            return bufferedSource
        }

        private fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                var totalBytesRead = 0L

                @Throws(IOException::class)
               override fun read(sink: Buffer?, byteCount: Long): Long {
                    val bytesRead: Long = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                    progressListener.update(
                        url,
                        totalBytesRead,
                        responseBody.contentLength(),
                        bytesRead == -1L
                    )
                    return bytesRead
                }
            }
        }

        init {
            this.progressListener = progressListener
            this.url = url
        }

        interface ProgressListener {
            fun update(
                url: String?,
                bytesRead: Long,
                contentLength: Long,
                done: Boolean
            )
        }
    }
