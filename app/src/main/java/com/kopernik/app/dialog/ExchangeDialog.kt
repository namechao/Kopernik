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
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.ui.mine.entity.AllConfigEntity
import kotlinx.android.synthetic.main.activity_snythetise_utc.*
import java.io.Serializable

class ExchangeDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {



    private var desc: TextView? = null


    private var passwordEt: EditText? = null
    private var exchangeCounts: EditText? = null
    private var exchangeProportion: TextView? = null
    private var okBtn: Button? = null
    private var bean: AllConfigEntity ?= null
    private var useFingerprint = false
    private var fingerprintDialog: FingerprintDialog? = null
    private var uytCounts=0
    private var rate=""
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_exchange)
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
        fingerprintDialog = FingerprintDialog.newInstance(2, null)
        fingerprintDialog?.setAuthenticationCallback(this)

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        desc = dialog.findViewById(R.id.tx_desc)
        passwordEt = dialog.findViewById(R.id.password_et)
        passwordEt?.addTextChangedListener(passwordWatcher)
        exchangeCounts = dialog.findViewById(R.id.exchange_counts)
        exchangeProportion = dialog.findViewById(R.id.tv_exchange_proportion)
        passwordEt?.addTextChangedListener(passwordWatcher)
        exchangeCounts?.addTextChangedListener(uytCountsWatcher)
        okBtn = dialog.findViewById(R.id.ok)
        var utcTouyt= BigDecimalUtils.divideDown(bean?.config?.utcPrice,bean?.uytPrice,2)

        exchangeProportion?.text=getString(R.string.asset_exchange_proportion)+"UTC:UYT= 1：${utcTouyt}"
        if (bean?.rateList!=null) {
            for (i in bean?.rateList!!){
              if (i.type.contains("Exchange")) rate =BigDecimalUtils.roundDOWN(i.rate,2)
            }
        }


        desc?.text=rate+"UYT"
         uytCounts=BigDecimalUtils.getRound(BigDecimalUtils.divideDown(BigDecimalUtils.multiply(bean?.utc,bean?.config?.utcPrice).toString(),bean?.uytPrice,2))?.toInt()
        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }

        KeyboardUtils.showKeyboard(exchangeCounts)
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
            listener?.let {
                dismiss()
                var utcCounts=BigDecimalUtils.divide( BigDecimalUtils.multiply(bean?.uytPrice,exchangeCounts?.text.toString().trim()).toString(),bean?.config?.utcPrice,8)
                it.onRequest(utcCounts,exchangeCounts?.text.toString().trim(),passwordEt!!.text.toString().trim(),rate) }
        }
    }


    var uytCountsWatcher: TextWatcher = object : TextWatcher {
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
            okBtn?.isEnabled=passwordEt?.text.toString().trim().isNotEmpty()&&!exchangeCounts?.text?.toString()?.trim().isNullOrEmpty()
            exchangeCounts?.setSelection(exchangeCounts?.text.toString().length)
            if (!exchangeCounts?.text.toString().trim().isBlank()&&exchangeCounts?.text.toString().trim().toInt()>uytCounts) exchangeCounts?.setText(""+uytCounts)
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
            okBtn?.isEnabled= passwordEt?.text.toString().trim().isNotEmpty() && !exchangeCounts?.text?.toString()?.trim().isNullOrEmpty()
        }
    }




    override fun onAuthenticationSucceeded(purpose: Int, value: String) {

    }
    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(utccounts:String,exchangeCounts:String,params:String,rate:String)
    }

    companion object {
        fun newInstance(bean: Any): ExchangeDialog {
            val fragment = ExchangeDialog()
            val bundle = Bundle()
            bundle.putSerializable("bean", bean as Serializable?)
            fragment.arguments = bundle
            return fragment
        }
    }
}