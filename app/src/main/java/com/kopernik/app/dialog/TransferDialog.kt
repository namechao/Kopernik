package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.kopernik.R
import com.kopernik.app.config.UserConfig
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils

class TransferDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {
    private var verfiyCode: EditText? = null
    private var okBtn: Button? = null
    private var bean:TransferCoinBean?=null
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_transfer)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =  getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.attributes = lp
        val bundle = arguments
        bundle?.getParcelable<TransferCoinBean>("bean")?.let{
            bean=it
        }

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {

        verfiyCode = dialog.findViewById(R.id.etInputCode)
        verfiyCode?.addTextChangedListener(passwordWatcher)
        okBtn = dialog.findViewById(R.id.ok)
        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }
        KeyboardUtils.showKeyboard(verfiyCode)
        okBtn?.setOnClickListener(clickFastListener)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {

            KeyboardUtils.hideSoftKeyboard(verfiyCode)
            listener?.let { it.onRequest(verfiyCode!!.text.toString().trim(),1) }
            dismiss()
        }
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
            if (verfiyCode!!.text.toString().isNotEmpty()) {
                enableBtn()
            } else {
                disableBtn()
            }
        }
    }

    private fun disableBtn() {
        okBtn!!.isEnabled = false
    }

    private fun enableBtn() {
        okBtn!!.isEnabled = true
    }


    override fun onAuthenticationSucceeded(purpose: Int, value: String) {

    }
    private var listener:RequestListener?=null
    open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(params:String,type: Int)
    }

    companion object {
        fun newInstance(bean: TransferCoinBean,type:String): TransferDialog {
            val fragment = TransferDialog()
            val bundle = Bundle()
            bundle.putParcelable("bean", bean)
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}