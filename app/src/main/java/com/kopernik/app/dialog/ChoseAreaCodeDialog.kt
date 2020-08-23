package com.kopernik.app.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager

import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kopernik.R
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import java.util.*

class ChoseAreaCodeDialog : DialogFragment(){
    private var recycleView: RecyclerView? = null
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!, R.style.BottomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_chose_area_code)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val window = dialog.window
        window.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =
        window.attributes = lp
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
         //选择器
        recycleView = dialog.findViewById(R.id.recyclerView)
        recycleView?.layoutManager=LinearLayoutManager(activity)
        val list: MutableList<LoginCountryBean> = ArrayList()
        list.add(LoginCountryBean(R.mipmap.ic_china,"+86"))
        list.add(LoginCountryBean(R.mipmap.ic_korea,"+82"))
        list.add(LoginCountryBean(R.mipmap.ic_england,"+44"))
        list.add(LoginCountryBean(R.mipmap.ic_america,"+1"))
        list.add(LoginCountryBean(R.mipmap.ic_australia,"+61"))
        list.add(LoginCountryBean(R.mipmap.ic_singapore,"+65"))
        var adapter=ChoseAreaAdapter(list)
        recycleView?.adapter=adapter

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