package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.kopernik.R
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils

class UTCDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {



    private var desc: TextView? = null


    private var passwordEt: EditText? = null
    private var okBtn: Button? = null
    private var type = 0
    private var useFingerprint = false
    private var fingerprintDialog: FingerprintDialog? = null

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_utc)
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
        type = bundle!!.getInt("type")
        fingerprintDialog = FingerprintDialog.newInstance(2, null)
        fingerprintDialog?.setAuthenticationCallback(this)

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        desc = dialog.findViewById(R.id.tx_desc)
        passwordEt = dialog.findViewById(R.id.password_et)
        passwordEt?.addTextChangedListener(passwordWatcher)
        okBtn = dialog.findViewById(R.id.ok)
        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }

        KeyboardUtils.showKeyboard(passwordEt)
        okBtn?.setOnClickListener(clickFastListener)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            listener?.let { it.onRequest(type,passwordEt!!.text.toString().trim()) }
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
                if (passwordEt!!.text.toString().isNotEmpty()) {
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
        fun onRequest(type:Int,params:String)
    }

    companion object {
        fun newInstance(type: Int): UTCDialog {
            val fragment = UTCDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}