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
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.mine.entity.AllConfigEntity
import java.io.Serializable

class UTKTransferDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {
    private var desc: TextView? = null
    private var passwordEt: EditText? = null
    private var transferCountsEt: EditText? = null
    private var transferUidEt: EditText? = null
    private var okBtn: Button? = null
    private var type = 0
    private var rate=""
    private var bean: AllConfigEntity?= null
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_utk_transfer)
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
        bundle?.getSerializable("bean")?.let {
            bean =  it as  AllConfigEntity
        }

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {


        desc = dialog.findViewById(R.id.tx_desc)
        passwordEt = dialog.findViewById(R.id.password_et)
        transferCountsEt = dialog.findViewById(R.id.transfer_counts)
        transferUidEt = dialog.findViewById(R.id.transfer_uid)
        passwordEt = dialog.findViewById(R.id.password_et)
        okBtn = dialog.findViewById(R.id.ok)
        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }
        if (bean?.rateList!=null) {
            for (i in bean?.rateList!!){
                if (i.type.contains("ROLL_OUT")) rate = BigDecimalUtils.roundDOWN(i.rate,2)
            }
        }
        desc?.text=rate+"UYT_TEST"
        transferCountsEt?.addTextChangedListener(passwordWatcher)
        transferUidEt?.addTextChangedListener(passwordWatcher)
        passwordEt?.addTextChangedListener(passwordWatcher)
        okBtn?.setOnClickListener(clickFastListener)
        KeyboardUtils.showKeyboard(transferCountsEt)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {
            if (BigDecimalUtils.compare(transferCountsEt?.text.toString().trim(),bean?.utk))
            {
                ToastUtils.showShort(activity,getString(R.string.asset_transfer_counts_tip))
                return
            }
            KeyboardUtils.hideSoftKeyboard(passwordEt)
            listener?.let { it.onRequest(transferCountsEt?.text.toString().trim(),transferUidEt?.text.toString().trim(),rate,passwordEt?.text.toString().trim()) }
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

    private var listener: RequestListener? = null
    open fun setOnRequestListener(requestListener: RequestListener) {
        listener = requestListener
    }

    interface RequestListener {
        fun onRequest(transferCounts: String, uid: String,rate:String,psw:String)
    }

    companion object {
        fun newInstance(bean: Any): UTKTransferDialog {
            val fragment = UTKTransferDialog()
            val bundle = Bundle()
            bundle.putSerializable("bean", bean as Serializable?)
            fragment.arguments = bundle
            return fragment
        }
    }
}