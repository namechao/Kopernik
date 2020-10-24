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
import java.math.BigDecimal

class ExchangeDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {



    private var desc: TextView? = null


    private var passwordEt: EditText? = null
    private var etUtcCounts: EditText? = null
    private var etUytCounts: EditText? = null
    private var exchangeProportion: TextView? = null
    private var okBtn: Button? = null
    private var bean: AllConfigEntity ?= null
    private var useFingerprint = false
    private var fingerprintDialog: FingerprintDialog? = null
    private var maxUtcCounts="0"
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
        etUtcCounts = dialog.findViewById(R.id.exchange_counts)
        exchangeProportion = dialog.findViewById(R.id.tv_exchange_proportion)
        etUytCounts = dialog.findViewById(R.id.exchange_uyt_counts)
        passwordEt?.addTextChangedListener(passwordWatcher)
        etUtcCounts?.addTextChangedListener(uytCountsWatcher)
        okBtn = dialog.findViewById(R.id.ok)
        var utcTouyt= BigDecimalUtils.divideDown(bean?.config?.utcPrice,bean?.uytProPrice,2)
        etUytCounts?.isEnabled=false
        exchangeProportion?.text=getString(R.string.asset_exchange_proportion)+"UTC:UYT= 1：${utcTouyt}"
        if (bean?.rateList!=null) {
            for (i in bean?.rateList!!){
              if (i.type.contains("Exchange")) rate =BigDecimalUtils.roundDOWN(i.rate,2)
            }
        }


        desc?.text=rate+"UYT_TEST"
        maxUtcCounts=BigDecimalUtils.roundDOWN(bean?.utc,2)
        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }

        KeyboardUtils.showKeyboard(etUtcCounts)
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
                var uytCounts=BigDecimalUtils.divideDown( BigDecimalUtils.multiply(bean?.config?.utcPrice,etUtcCounts?.text.toString().trim()).toString(),bean?.uytProPrice,8)
                it.onRequest(etUtcCounts?.text.toString().trim(),uytCounts,passwordEt!!.text.toString().trim(),rate) }
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
            text: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
           var  s=text
            if (s.toString().contains(".")) {
                if (s.length - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                    etUtcCounts?.setText(s);
                    etUtcCounts?.setSelection(s.length);
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                etUtcCounts?.setText(s);
                etUtcCounts?.setSelection(2);
            }
            if (s.toString().startsWith("0")
                && s.toString().trim().length > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    etUtcCounts?.setText(s.subSequence(0, 1));
                    etUtcCounts?.setSelection(1);
                    return;
                }
            }
        }

        override fun afterTextChanged(s: Editable) {
            okBtn?.isEnabled=passwordEt?.text.toString().trim().isNotEmpty()&&!etUtcCounts?.text?.toString()?.trim().isNullOrEmpty()&&etUtcCounts?.text?.toString()?.trim()!="0"&&etUtcCounts?.text?.toString()?.trim()!="0."&&etUtcCounts?.text?.toString()?.trim()!="0.0"&&etUtcCounts?.text?.toString()?.trim()!="0.00"
            etUtcCounts?.setSelection(etUtcCounts?.text.toString().length)
            if (!etUtcCounts?.text.toString().trim().isBlank()&&BigDecimal(etUtcCounts?.text.toString().trim())-BigDecimal(maxUtcCounts)> BigDecimal("0")) etUtcCounts?.setText(""+maxUtcCounts)
            if (!etUtcCounts?.text.toString().trim().isBlank())
                etUytCounts?.setText(""+BigDecimalUtils.divideDown( BigDecimalUtils.multiply(bean?.config?.utcPrice,etUtcCounts?.text.toString().trim()).toString(),bean?.uytProPrice,2))
                else   etUytCounts?.setText("")

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
            okBtn?.isEnabled= passwordEt?.text.toString().trim().isNotEmpty() && !etUtcCounts?.text?.toString()?.trim().isNullOrEmpty()&&etUtcCounts?.text?.toString()?.trim()!="0"&&etUtcCounts?.text?.toString()?.trim()!="0."&&etUtcCounts?.text?.toString()?.trim()!="0.0"&&etUtcCounts?.text?.toString()?.trim()!="0.00"
        }
    }




    override fun onAuthenticationSucceeded(purpose: Int, value: String) {

    }
    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(utcCounts:String,uytCounts:String,params:String,rate:String)
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