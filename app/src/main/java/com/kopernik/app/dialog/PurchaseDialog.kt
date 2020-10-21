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
    private var payType:TextView?=null
    private var payUytCoins:TextView?=null
    private var payUytTestCoins:TextView?=null
    private var balanceNotEnough:TextView?=null
    private var uytBalance:TextView?=null
    private var uytTestBalance:TextView?=null
    private var purchaseEntity: PurchaseEntity?=null
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
        payType = dialog.findViewById(R.id.pay_type)
        payUytTestCoins = dialog.findViewById(R.id.pay_uyt_test_coins)
        balanceNotEnough = dialog.findViewById(R.id.tv_balance_not_enough)
        uytBalance = dialog.findViewById(R.id.uyt_balance)
        uytTestBalance = dialog.findViewById(R.id.uyt_test_balance)
        passwordEt = dialog.findViewById(R.id.password_et)
        okBtn = dialog.findViewById(R.id.confirm)
        passwordEt?.addTextChangedListener(passwordWatcher)
        miningMachineType?.text=purchaseEntity?.mineMacName
        miningMachinePrice?.text= BigDecimalUtils.roundDOWN(purchaseEntity?.mineMacPrice,2)+"USDT"
        payUytCoins?.text=BigDecimalUtils.roundDOWN(purchaseEntity?.consumeUytPro,2)
        payUytTestCoins?.text=BigDecimalUtils.roundDOWN(purchaseEntity?.consumeUyt,2)
        uytBalance?.text=resources.getString(R.string.uyt_balance)+": "+BigDecimalUtils.roundDOWN(purchaseEntity?.uytProBanlance,2)
        payType?.text=purchaseEntity?.uytProRation
        uytTestBalance?.text=resources.getString(R.string.uyt_test_balance)+": "+BigDecimalUtils.roundDOWN(purchaseEntity?.uytBanlance,2)
         if (BigDecimalUtils.compare(purchaseEntity?.consumeUytPro,purchaseEntity?.uytProBanlance)) {
             balanceNotEnough?.visibility = View.VISIBLE
             balanceNotEnough?.text=getString(R.string.balance_not_enough)+"UYT"+getString(R.string.balance_not_enough_end)
         } else  if (BigDecimalUtils.compare(purchaseEntity?.consumeUyt,purchaseEntity?.uytBanlance)) {
             balanceNotEnough?.visibility = View.VISIBLE
             balanceNotEnough?.text=getString(R.string.balance_not_enough)+"UYT_TEST"+getString(R.string.balance_not_enough_end)
         } else {
             balanceNotEnough?.visibility = View.GONE
         }
        KeyboardUtils.showKeyboard(passwordEt)
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
            listener?.let { it.onRequest(passwordEt!!.text.toString().trim()) }
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


            if (passwordEt!!.text.toString().isNotEmpty()&&BigDecimalUtils.compare(purchaseEntity?.uytProBanlance,purchaseEntity?.consumeUytPro)&&BigDecimalUtils.compare(purchaseEntity?.uytBanlance,purchaseEntity?.consumeUyt)) {
                okBtn?.isEnabled = true
                activity?.getColor(R.color.color_20222F)?.let { okBtn?.setTextColor(it) }
            } else {
                activity?.getColor(R.color.color_5D5386)?.let { okBtn?.setTextColor(it) }
                okBtn?.isEnabled = false
            }
        }
    }


    override fun onAuthenticationSucceeded(purpose: Int, value: String) {

    }
    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(params:String)
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