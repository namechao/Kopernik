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
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_trade_password.*

class WithdrawlDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {



    private var tvPhone: TextView? = null
    private var tvCode: TextView? = null
    private var phoneNumber=""
    private var verfiyCode: EditText? = null
    private var okBtn: Button? = null
    private var bean:WithdrawCoinBean?=null
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_withdrawl)
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
         bundle?.getParcelable<WithdrawCoinBean>("bean")?.let{
            bean=it
        }

        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        tvPhone = dialog.findViewById(R.id.tvPhone)
        tvCode = dialog.findViewById(R.id.tvCode)
        verfiyCode = dialog.findViewById(R.id.etInputCode)
        verfiyCode?.addTextChangedListener(passwordWatcher)
        okBtn = dialog.findViewById(R.id.ok)
        var phone= UserConfig.singleton?.accountBean?.phone
        if (phone!=null){
            phoneNumber=phone
            if (phone.length>5){
                tvPhone?.text="${phone.subSequence(0,3)}****${phone.subSequence(phone.length-4,phone.length)}"
            }
        }
        tvCode?.setOnClickListener {
            if (phoneNumber.isNullOrEmpty()) {
                ToastUtils.showShort(context, resources.getString(R.string.phone_not_empty))
                return@setOnClickListener
            }
            //获取验证码
            listener?.let { it.onRequest(verfiyCode!!.text.toString().trim(),0,phoneNumber) }
        }
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
            listener?.let { it.onRequest(verfiyCode!!.text.toString().trim(),1,phoneNumber) }
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
        fun onRequest(params:String,type: Int,phoneNumber:String)
    }
    open fun startTime(){
        timer.start()
    }
    //计时器定时
    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tvCode?.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
           context?.let {  tvCode?.setTextColor(ContextCompat.getColor(it, R.color.color_5D5386) )}
            tvCode?.isClickable = false
        }

        override fun onFinish() {
            tvCode?.text = "重新获取"
            context?.let {  tvCode?.setTextColor(ContextCompat.getColor(it, R.color.color_F4C41B))}
            tvCode?.postInvalidate()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
    companion object {
        fun newInstance(bean: WithdrawCoinBean,type:String): WithdrawlDialog {
            val fragment = WithdrawlDialog()
            val bundle = Bundle()
            bundle.putParcelable("bean", bean)
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}