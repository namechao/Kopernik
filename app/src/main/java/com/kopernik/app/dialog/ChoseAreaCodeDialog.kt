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
import com.kopernik.ui.asset.entity.*
import com.kopernik.ui.asset.util.OnClickFastListener
import com.kopernik.app.utils.KeyboardUtils

class ChoseAreaCodeDialog : DialogFragment(){
    private var confirm: TextView? = null
    private var type = 0
    private var numberPicker:NumberPicker?=null
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_chose_area_code)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        window.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =
        window.attributes = lp
        val bundle = arguments
        type = bundle!!.getInt("type")
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
         //选择器
        numberPicker = dialog.findViewById(R.id.numberPicker)
        //确定按钮
        confirm = dialog.findViewById(R.id.confirm)
        //取消按钮
        dialog.findViewById<TextView>(R.id.cancel).setOnClickListener { dismiss() }

        confirm?.setOnClickListener(clickFastListener)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {

            listener?.let { it.onRequest(type,"") }
        }
    }







    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(type:Int,params:String)
    }

    companion object {
        fun newInstance(type: Int): ChoseAreaCodeDialog {
            val fragment = ChoseAreaCodeDialog()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

}