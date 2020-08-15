package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.kopernik.R


class ReminderDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var container: ConstraintLayout? = null

    private var msgTv: TextView? = null
    private var mBtn: Button? = null
    private var tvTiming:TextView?=null
    private val display: Display
    private var showTitle = false
    private var showMsg = false
    private fun init(): ReminderDialog {
        // 获取Dialog布局
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.view_reminder_dialog, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)
        tvTiming = view.findViewById(R.id.tvTiming)
        msgTv = view.findViewById(R.id.txt_msg)
        mBtn = view.findViewById(R.id.btn)
        mBtn?.setOnClickListener {
            listener?.let { it.onRequest() }
        }
        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.setLayoutParams(
            FrameLayout.LayoutParams(
                (display.width * 0.8).toInt(),
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        )
        return this
    }



    fun setMsg(msg: String): ReminderDialog {
        showMsg = true
        if ("" == msg) {
            msgTv!!.text = "内容"
        } else {
            msgTv!!.text = msg
        }
        return this
    }

    fun setCancelable(cancel: Boolean): ReminderDialog {
        dialog!!.setCancelable(cancel)
        return this
    }

    private var listener:RequestListener?=null
    open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest()
    }

    val isShowing: Boolean
        get() = dialog!!.isShowing

    fun show() {
        dialog!!.show()
    }

    init {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
        init()
    }
}