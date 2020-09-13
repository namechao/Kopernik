package com.kopernik.app.network

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.kopernik.app.MyApplication
import com.kopernik.app.network.http.EncodeInterceptor
import com.kopernik.app.network.http.MyOkHttpRetryInterceptor
import com.kopernik.app.network.http.ProgressResponseBody
import com.kopernik.app.network.http.TrustAllCerts
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager


class DownLoadCilent {
    private var okHttpClient:OkHttpClient?=null
    private var cancelUpdate = false
    init {
        //log相关
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY //log打印级别，决定了log显示的详细程度
        okHttpClient= OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .sslSocketFactory(createSSLSocketFactory(), TrustAllCerts())
            .retryOnConnectionFailure(true)
            .addInterceptor(EncodeInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(MyOkHttpRetryInterceptor())
            .writeTimeout(20L, TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
    }
    companion object{
        fun getInstance()=SingletonHolder.INSTANCE
        private lateinit var retrofit: Retrofit;

    }
    private  object SingletonHolder{
        val  INSTANCE=DownLoadCilent()
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

    fun downLoad(url:String,callback:ProcessCallBack){
        var okHttpClient=okHttpClient?.newBuilder()?.addInterceptor(ProgressInterceptor(object :
            ProgressResponseBody.ProgressListener {
            override fun update(url: String?, bytesRead: Long, contentLength: Long, done: Boolean) {
                // 计算进度条位置
                val progress = (bytesRead.toFloat() / contentLength!! * 100).toInt()
                Log.e("downloadProcess","$progress")
                if (callback!=null) callback.CallBack(progress)
            }
        }))?.build()
     get(okHttpClient,url,object :Callback{
         override fun onFailure(call: Call, e: IOException) {

         }

         override fun onResponse(call: Call, response: Response) {
                 // 判断SD卡是否存在，并且是否具有读写权限
                 if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                     // 获得存储卡的路径
                     val sdpath = Environment.getExternalStorageDirectory().toString() + "/"
                     var mSavePath = sdpath + "download"
                     val file = File(mSavePath)
                     // 判断文件目录是否存在
                     if (!file.exists()) {
                         file.mkdir()
                     }
                     val apkFile = File(mSavePath, "kopernik.apk")
                     val fos = FileOutputStream(apkFile)
                     var `is`: InputStream?=null
                     try {
                         `is`= response.body()?.byteStream()
                         var total = response.body()?.contentLength()
                         if (`is` != null) {
                             // 缓存
                             val buf = ByteArray(1024)
                             var count = 0
                             while (!cancelUpdate){
                                 val numread = `is`?.read(buf)
                                 if (numread != null) {
                                     count += numread
                                 }

                                 if (numread != null) {
                                     if (numread <= 0) {
                                         // 下载完成
                                         installApk(File(mSavePath, "kopernik.apk"))
                                         break
                                     }
                                 }
                                 // 写入文件
                                 if (numread != null) {
                                     fos.write(buf, 0, numread)
                                 }

                             }

                         }


                     } catch (e: IOException) {
                         e.stackTrace
                     }finally {
                         try {
                             `is`?.close()
                         } catch (e: IOException) {
                         }
                         try {
                             fos.close()
                         } catch (e: IOException) {
                         }
                     }
                 }
         }
     })



    }

    fun installApk(apkFile: File) {
        val installApkIntent = Intent()
        installApkIntent.action = Intent.ACTION_VIEW
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT)
        installApkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(MyApplication.instance().applicationContext, "com.kopernik.file_provider", apkFile), "application/vnd.android.package-archive")
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }

        if ( MyApplication.instance().currentActivity?.packageManager?.queryIntentActivities(installApkIntent, 0)?.size!! > 0) {
            MyApplication.instance().currentActivity?.startActivity(installApkIntent)
        }

    }
    /**
     * get请求
     * @param address
     * @param callback
     */
    operator fun get(okClient:OkHttpClient?,address: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(address)
            .build()
        okClient?.newCall(request)?.enqueue(callback)
    }

    /**
     * post请求
     * @param address
     * @param callback
     * @param map
     */
    fun post(
        address: String?,
        callback: Callback?,
        map: Map<String?, String?>?
    ) {
        val client = OkHttpClient()
        val builder = FormBody.Builder()
        if (map != null) {
            for ((key, value) in map) {
                builder.add(key, value)
            }
        }
        val body = builder.build()
        val request: Request = Request.Builder()
            .url(address)
            .post(body)
            .build()
        client.newCall(request).enqueue(callback)
    }
      interface ProcessCallBack{
          fun CallBack(process:Int)
      }
}