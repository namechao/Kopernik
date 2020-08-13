package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.kopernik.R


class UYTAlertDialog2(private val context: Context) {
    private var dialog: Dialog? = null
    private var container: LinearLayout? = null
    private var titleTv: TextView? = null
    private var msgTv: TextView? = null
    private var mBtn: Button? = null
    private val display: Display
    private var showTitle = false
    private var showMsg = false
    private fun init(): UYTAlertDialog2 {
        // 获取Dialog布局
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.view_alertdialog2, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)
        titleTv = view.findViewById(R.id.txt_title)
        titleTv?.setVisibility(View.GONE)
        msgTv = view.findViewById(R.id.txt_msg)
        msgTv?.setVisibility(View.GONE)
        msgTv?.setMovementMethod(ScrollingMovementMethod.getInstance())
        mBtn = view.findViewById(R.id.btn)

        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.setLayoutParams(
            FrameLayout.LayoutParams(
                (display.width * 0.85).toInt(),
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        )
        return this
    }

    fun setTitle(title: String): UYTAlertDialog2 {
        showTitle = true
        if ("" == title) {
            titleTv!!.text = "标题"
        } else {
            titleTv!!.text = title
        }
        return this
    }

    fun setMsg(msg: String): UYTAlertDialog2 {
        showMsg = true
        if ("" == msg) {
            msgTv!!.text = "内容"
        } else {
            msgTv!!.text = msg
        }
        return this
    }

    fun setCancelable(cancel: Boolean): UYTAlertDialog2 {
        dialog!!.setCancelable(cancel)
        return this
    }

    fun setButton(
        text: String?,
        listener: View.OnClickListener?
    ): UYTAlertDialog2 {
        if (text != null && !text.isEmpty()) {
            mBtn!!.text = text
        } else {
            mBtn!!.text = ""
        }
        mBtn!!.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        }
        return this
    }

    private fun setLayout() {
        if (!showTitle && !showMsg) {
            titleTv!!.text = "提示"
            titleTv!!.visibility = View.VISIBLE
        }
        if (showTitle) {
            titleTv!!.visibility = View.VISIBLE
        }
        if (showMsg) {
            msgTv!!.visibility = View.VISIBLE
        }
    }

    val isShowing: Boolean
        get() = dialog!!.isShowing

    fun show() {
        setLayout()
        dialog!!.show()
    }

    init {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
        init()
    }
}