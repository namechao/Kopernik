package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.text.Editable
import android.text.TextWatcher
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.allen.library.SuperButton
import com.kopernik.R
import com.kopernik.app.network.RetrofitClient
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.data.api.Api
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal


class VerifyCodeAlertDialog(private val context: Context,var phone:String) {
    private var dialog: Dialog? = null
    private var negBtn: SuperButton? = null
    private var posBtn: SuperButton? = null
    private var etResult: EditText? = null
    private var ivImageCode: ImageView? = null
    private val display: Display
    private var container: LinearLayout? = null
    private fun init(): VerifyCodeAlertDialog {
        // 获取Dialog布局
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.verify_code_alertdialog, null)
        container = view.findViewById(R.id.container)
        negBtn = view.findViewById(R.id.btn_neg)
        posBtn = view.findViewById(R.id.btn_pos)
        etResult = view.findViewById(R.id.etResult)
        etResult?.addTextChangedListener(passwordWatcher)
        ivImageCode = view.findViewById(R.id.ivImageCode)


        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.setLayoutParams(
            FrameLayout.LayoutParams(
                (display.width * 0.85).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        getImage()
        KeyboardUtils.showKeyboard(etResult)
        return this
    }

    fun getImage(){
        DownloadAsyncTask().execute()
    }

    fun setCancelable(cancel: Boolean): VerifyCodeAlertDialog {
        dialog!!.setCancelable(cancel)
        return this
    }

    fun setPositiveButton(
        listener: RequestListener?
    ): VerifyCodeAlertDialog {
        posBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onRequest(etResult?.text.toString().trim())
            KeyboardUtils.hideSoftKeyboard(etResult)
            dialog!!.dismiss()
        })
        return this
    }

    fun setNegativeButton(
        text: String,
        listener: RequestListener?
    ): VerifyCodeAlertDialog {
        negBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onRequest(etResult?.text.toString().trim())
            dialog!!.dismiss()
        })
        return this
    }

    private fun setLayout() {
        negBtn?.setOnClickListener(View.OnClickListener {
            KeyboardUtils.hideSoftKeyboard(etResult)
            dialog!!.dismiss() })
    }

    fun show() {
        setLayout()
        dialog!!.show()
    }
    private var listener: VerifyCodeAlertDialog.RequestListener?=null
    open fun setOnRequestListener(requestListener: VerifyCodeAlertDialog.RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(imageVerifyCode:String)
    }
    var passwordWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {

            posBtn?.isEnabled = etResult?.text.toString().trim().isNotEmpty()

        }
    }


 inner  class DownloadAsyncTask: AsyncTask<String, Void, Bitmap>() {
     var bitmap: Bitmap? = null
     override fun doInBackground(vararg params: String?): Bitmap? {
       try {
           val okHttpClient = RetrofitClient.getInstance().getOkHttpClient() //建立客户端
           val request= Request.Builder().url("${Api.baseUrl}user/getImageCode?phone=${phone}").build() //封装请求
           val responseBody = okHttpClient.newCall(request).execute().body()
           val inputStream = responseBody?.byteStream()
           bitmap = BitmapFactory.decodeStream(inputStream)

       }catch (e:IOException){
           e.stackTrace
       }
         return bitmap
     }

     override fun onPostExecute(result: Bitmap?) {
         super.onPostExecute(result)
         result?.let {
             ivImageCode?.setImageBitmap(it)
         }

     }
 }


    init {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
        init()
    }
}