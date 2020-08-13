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
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils

class WithdrawEarningsDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {
    private var titleRootView1: LinearLayout? = null
    private var titleRootView2: LinearLayout? = null
    private var useFingerprintIv: ImageView? = null
    private var titleTv: TextView? = null
    private var usePwTv: TextView? = null
    private var titleName1: TextView? = null
    private var desc1: TextView? = null
    private var titleName2: TextView? = null
    private var desc2: TextView? = null
    private var passwordEt: EditText? = null
    private var okBtn: Button? = null
    private var type = 0
    private var useFingerprint = false
    private var fingerprintDialog: FingerprintDialog? = null
    private var wdbean:WithdrawEarningsBean?=null

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        wdbean=arguments?.getParcelable("withdraw")
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_withdraw_earnings)
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
        usePwTv = dialog.findViewById(R.id.use_pw_tv)
        useFingerprintIv =
            dialog.findViewById(R.id.use_fingerprint_iv)
        titleTv = dialog.findViewById(R.id.title_tv)
        titleRootView1 = dialog.findViewById(R.id.tx_title1_ll)
        titleRootView2 = dialog.findViewById(R.id.tx_title2_ll)
        titleName1 = dialog.findViewById(R.id.tx_title1_name_tv)
        titleName2 = dialog.findViewById(R.id.tx_title2_name_tv)
        desc1 = dialog.findViewById(R.id.tx_desc1)
        desc2 = dialog.findViewById(R.id.tx_desc2)
        passwordEt = dialog.findViewById(R.id.password_et)
        okBtn = dialog.findViewById(R.id.ok)
        wdbean?.let {
            titleTv?.text=it.title
            titleName1?.text=it.oneLineLeft
            desc1?.text=it.oneLineRight
            titleName2?.text=it.twoLineLeft
            desc2?.text=it.twoLineRight
        }

        //获取是否使用指纹
        UserConfig.singleton?.isUseFingerprint?.let { useFingerprint =it  }
        if (!useFingerprint) {
            useFingerprintIv?.visibility = View.GONE
            usePwTv?.visibility = View.GONE
        } else {
            initVerifyType(true)
        }
        useFingerprintIv?.setOnClickListener(View.OnClickListener { initVerifyType(true) })
        usePwTv?.setOnClickListener(View.OnClickListener { initVerifyType(false) })
        passwordEt?.addTextChangedListener(passwordWatcher)
        okBtn?.setOnClickListener(clickFastListener)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {
            if (useFingerprint) {
                fingerprintDialog!!.show(fragmentManager!!, "fingerprint")
                return
            }
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            listener?.let { it.onRequest(type,passwordEt!!.text.toString().trim()) }
        }
    }

    private fun initVerifyType(useFingerprint: Boolean) {
        this.useFingerprint = useFingerprint
        if (useFingerprint) {
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            passwordEt!!.setText("")
            usePwTv!!.visibility = View.VISIBLE
            useFingerprintIv!!.visibility = View.GONE
            passwordEt!!.visibility = View.GONE
            enableBtn()
        } else {
            usePwTv!!.visibility = View.GONE
            useFingerprintIv!!.visibility = View.VISIBLE
            passwordEt!!.visibility = View.VISIBLE
            disableBtn()
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
            if (!useFingerprint) {
                if (passwordEt!!.text.toString().isNotEmpty()) {
                    enableBtn()
                } else {
                    disableBtn()
                }
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
        listener?.let { it.onRequest(type,value) }
    }
    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(type:Int,params:String)
    }

    companion object {
        fun newInstance(type: Int, bean: Any?): WithdrawEarningsDialog {
            val fragment = WithdrawEarningsDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putParcelable("withdraw", bean as Parcelable?)
            fragment.arguments = bundle
            return fragment
        }
    }
}