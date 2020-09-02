import android.content.Intent
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.kopernik.R
import com.kopernik.app.MyApplication
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.login.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class SingleLoginInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val st = System.nanoTime()
        val response = chain.proceed(request)

        val responseBody = response.body()
        val contentType = responseBody!!.contentType()
        var subtype: String? = null
        val body: ResponseBody

        if (contentType != null) {
            subtype = contentType.subtype()
        }

        if (subtype != null && (subtype.contains("json")
                    || subtype.contains("xml")
                    || subtype.contains("plain")
                    || subtype.contains("html"))
        ) {
            val bodyString = responseBody.string()
            val jsonObject = JSONObject(bodyString)
            if ((jsonObject.has("status") && jsonObject.get("status") == 400)){
                MyApplication.instance().currentActivity?.let {
                    if (it.javaClass!=LoginActivity::class.java) {
                        ToastUtils.showShort(it.getString(R.string.error_400))
                        UserConfig.singleton?.accountString = null
                        UserConfig.singleton?.tradePassword = null
                        val intent =
                            Intent(it, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
            body = ResponseBody.create(contentType, bodyString)
        } else {
            return response
        }
        return response.newBuilder().body(body).build()
    }

}