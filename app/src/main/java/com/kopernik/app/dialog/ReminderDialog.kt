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
    private var titleTv: TextView? = null
    private var msgTv: TextView? = null
    private var mBtn: Button? = null
    private var mIconClose: ImageView? = null
    private val display: Display
    private var showTitle = false
    private var showMsg = false
    private fun init(): ReminderDialog {
        // 获取Dialog布局
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.view_reminder_dialog, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)
        titleTv = view.findViewById(R.id.txt_title)
        titleTv?.setVisibility(View.GONE)
        msgTv = view.findViewById(R.id.txt_msg)
        msgTv?.setVisibility(View.GONE)
        msgTv?.setMovementMethod(ScrollingMovementMethod.getInstance())
        mBtn = view.findViewById(R.id.btn)
        mIconClose = view.findViewById(R.id.icon_close)

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

    fun setTitle(title: String): ReminderDialog {
        showTitle = true
        if ("" == title) {
            titleTv!!.text = "标题"
        } else {
            titleTv!!.text = title
        }
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

    fun setButton(
        text: String?,
        listener: View.OnClickListener?
    ): ReminderDialog {
        if (text != null && !text.isEmpty()) {
            mBtn!!.text = text
        } else {
            mBtn!!.text = ""
        }
        mBtn!!.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        }
        mIconClose?.setOnClickListener {   dialog!!.dismiss() }
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