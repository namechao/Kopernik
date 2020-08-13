package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.allen.library.SuperButton
import com.kopernik.R

class UYTAlertDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var container: LinearLayout? = null
    private var titleTv: TextView? = null
    private var msgTv: TextView? = null
    private var negBtn: SuperButton? = null
    private var posBtn: SuperButton? = null
    private var img_line: ImageView? = null
    private val display: Display
    private var showTitle = false
    private var showMsg = false
    private var showPosBtn = false
    private var showNegBtn = false
    private fun init(): UYTAlertDialog {
        // 获取Dialog布局
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.view_alertdialog, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)
        titleTv = view.findViewById(R.id.txt_title)
        titleTv?.visibility = View.GONE
        msgTv = view.findViewById(R.id.txt_msg)
        msgTv?.visibility = View.GONE
        negBtn = view.findViewById(R.id.btn_neg)
        negBtn?.visibility = View.GONE
        posBtn = view.findViewById(R.id.btn_pos)
        posBtn?.visibility = View.GONE
        img_line = view.findViewById(R.id.img_line)
        img_line?.visibility = View.GONE

        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.layoutParams = FrameLayout.LayoutParams(
            (display.width * 0.85).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return this
    }

    fun setTitle(title: String): UYTAlertDialog {
        showTitle = true
        if ("" == title) {
            titleTv!!.text = "标题"
        } else {
            titleTv!!.text = title
        }
        return this
    }

    fun setMsg(msg: String): UYTAlertDialog {
        showMsg = true
        if ("" == msg) {
            msgTv!!.text = "内容"
        } else {
            msgTv!!.text = msg
        }
        return this
    }

    fun setCancelable(cancel: Boolean): UYTAlertDialog {
        dialog!!.setCancelable(cancel)
        return this
    }

    fun setPositiveButton(
        text: String,
        listener: View.OnClickListener?
    ): UYTAlertDialog {
        showPosBtn = true
        if ("" == text) {
            posBtn!!.text = "确定"
        } else {
            posBtn!!.text = text
        }
        posBtn!!.setOnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        }
        return this
    }

    fun setNegativeButton(
        text: String,
        listener: View.OnClickListener?
    ): UYTAlertDialog {
        showNegBtn = true
        if ("" == text) {
            negBtn!!.text = "取消"
        } else {
            negBtn!!.text = text
        }
        negBtn!!.setOnClickListener { v ->
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
        if (!showPosBtn && !showNegBtn) {
            posBtn!!.text = "确定"
            posBtn!!.visibility = View.VISIBLE
            posBtn!!.setOnClickListener { dialog!!.dismiss() }
        }
        if (showPosBtn && showNegBtn) {
            posBtn!!.visibility = View.VISIBLE
            negBtn!!.visibility = View.VISIBLE
            img_line!!.visibility = View.VISIBLE
        }
        if (showPosBtn && !showNegBtn) {
            posBtn!!.setShapeCornersBottomLeftRadius(5f).setShapeCornersBottomRightRadius(5f)
                .setUseShape()
            posBtn!!.setUseShape()
            posBtn!!.visibility = View.VISIBLE
        }
        if (!showPosBtn && showNegBtn) {
            negBtn!!.setShapeCornersBottomLeftRadius(5f).setShapeCornersBottomRightRadius(5f)
                .setUseShape()
            negBtn!!.visibility = View.VISIBLE
        }
    }

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