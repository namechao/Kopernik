package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyProperties
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.allen.library.SuperTextView
import com.kopernik.R
import com.kopernik.app.utils.fingerprint.FingerprintHelper
import com.kopernik.app.utils.fingerprint.FingerprintHelper.SimpleAuthenticationCallback

@RequiresApi(api = Build.VERSION_CODES.M)
class FingerprintDialog : DialogFragment(),
    SimpleAuthenticationCallback {
    private var iconIv: ImageView? = null
    private var titleTv: TextView? = null
    private var errorMsgTv: TextView? = null
    private var cancelBtn: SuperTextView? = null
    private var type:Int=-1 //1 开启指纹验证 2指纹验证 = 0
    private var helper: FingerprintHelper? = null
    private var authenticationCallback: AuthenticationCallback? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments!!.getInt("type", -1)
        helper = FingerprintHelper.getInstance()
        helper?.init(context)
        if (type == 1) {
            helper?.setPw(arguments!!.getString("pw"))
            helper?.generateKey()
            helper?.setPurpose(KeyProperties.PURPOSE_ENCRYPT)
        } else {
            helper?.setPurpose(KeyProperties.PURPOSE_DECRYPT)
        }
        helper?.setCallback(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_fingerprint)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        iconIv = dialog.findViewById(R.id.icon_iv)
        titleTv = dialog.findViewById(R.id.title_tv)
        errorMsgTv = dialog.findViewById(R.id.error_msg_tv)
        cancelBtn = dialog.findViewById(R.id.cancel_spt)
        if (type == 1) {
            titleTv?.setText(
                activity!!.resources.getString(R.string.use_fingerprint)
            )
        } else {
            titleTv?.setText(
                activity!!.resources.getString(R.string.verification_fingerprint)
            )
        }
        cancelBtn?.setOnClickListener(View.OnClickListener {
            helper!!.stopAuthenticate()
            dismiss()
        })
    }

    override fun onResume() {
        super.onResume()
        helper!!.authenticate()
    }

    override fun onPause() {
        super.onPause()
        helper!!.stopAuthenticate()
    }

    fun setAuthenticationCallback(authenticationCallback: AuthenticationCallback?) {
        this.authenticationCallback = authenticationCallback
    }

    override fun onAuthenticationSucceeded(purpose: Int, value: String) {
        if (purpose == KeyProperties.PURPOSE_ENCRYPT) {
            //加密成功
        } else {
        }
        if (authenticationCallback != null) {
            authenticationCallback!!.onAuthenticationSucceeded(purpose, value)
        }
        dismiss()
    }

    override fun onAuthenticationFail() {
        errorMsgTv?.text = activity!!.resources.getString(R.string.fingerprint_error)
    }

    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        errorMsgTv?.text = errString
    }

    override fun onAuthenticationHelp(
        helpCode: Int,
        helpString: CharSequence
    ) {
        errorMsgTv?.text = helpString
    }

    interface AuthenticationCallback {
        fun onAuthenticationSucceeded(purpose: Int, value: String)
    }

    companion object {
        fun newInstance(type: Int, pw: String?): FingerprintDialog {
            val fragment = FingerprintDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putString("pw", pw)
            fragment.arguments = bundle
            return fragment
        }
    }
}