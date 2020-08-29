package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.kopernik.R
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.ui.mine.entity.PurchaseEntity
import java.math.BigDecimal

class PurchaseDialog : DialogFragment(),
    FingerprintDialog.AuthenticationCallback {
    private var passwordEt: EditText? = null
    private var okBtn: Button? = null
    private var type = 0
    private var miningMachineType:TextView?=null
    private var miningMachinePrice:TextView?=null
    private var payUytCoins:TextView?=null
    private var balanceNotEnough:TextView?=null
    private var uytBalance:TextView?=null
    private var purchaseEntity: PurchaseEntity?=null
    private var isBalance=false
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        purchaseEntity=arguments?.getParcelable("purchaseEntity")
        val dialog =
            Dialog(activity!!, R.style.AlertDialogStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_purchase)
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.CENTER
        lp.width =    ( window.windowManager.defaultDisplay.width * 0.86).toInt()
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =
        window.attributes = lp
        val bundle = arguments
        type = bundle!!.getInt("type")
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        miningMachineType = dialog.findViewById(R.id.mining_machine_type)
        miningMachinePrice = dialog.findViewById(R.id.mining_machine_price)
        payUytCoins = dialog.findViewById(R.id.pay_uyt_coins)
        balanceNotEnough = dialog.findViewById(R.id.tv_balance_not_enough)
        uytBalance = dialog.findViewById(R.id.uyt_balance)
        passwordEt = dialog.findViewById(R.id.password_et)
        okBtn = dialog.findViewById(R.id.confirm)
        passwordEt?.addTextChangedListener(passwordWatcher)
        miningMachineType?.text=purchaseEntity?.mineMacName
        miningMachinePrice?.text= BigDecimalUtils.roundDOWN(purchaseEntity?.mineMacPrice,2)+"USDT"
        payUytCoins?.text=purchaseEntity?.consumeUyt+"UYT"
        uytBalance?.text=resources.getString(R.string.uyt_balance)+": "+purchaseEntity?.uytBanlance
        if ( BigDecimalUtils.substract(purchaseEntity?.consumeUyt,purchaseEntity?.uytBanlance) >BigDecimal(0)){
            balanceNotEnough?.visibility=View.VISIBLE
            okBtn?.isEnabled=false
            isBalance=false
            okBtn?.setTextColor(resources.getColor(R.color.color_5D5386,null))
        }else{
            balanceNotEnough?.visibility=View.GONE
            isBalance=true
            okBtn?.isEnabled=true
        }


        //关闭弹窗
        dialog.findViewById<ImageView>(R.id.icon_close).setOnClickListener {
            dismiss()
        }


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

                if (passwordEt!!.text.toString().isNotEmpty()&&isBalance) {
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
        fun newInstance(type: Int, bean: Any?): PurchaseDialog {
            val fragment = PurchaseDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            bundle.putParcelable("purchaseEntity", bean as Parcelable?)
            return fragment
        }
    }

}