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


class UYTAlertDialog3(private val context: Context) {
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
    private fun init(): UYTAlertDialog3 {
        // 获取Dialog布局
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.withdraw_uyt_alertdialog, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)
        titleTv = view.findViewById(R.id.txt_title)
        titleTv?.setVisibility(View.GONE)
        msgTv = view.findViewById(R.id.txt_msg)
        msgTv?.setVisibility(View.GONE)
        negBtn = view.findViewById(R.id.btn_neg)
        negBtn?.setVisibility(View.GONE)
        posBtn = view.findViewById(R.id.btn_pos)
        posBtn?.setVisibility(View.GONE)
        img_line = view.findViewById(R.id.img_line)
        img_line?.setVisibility(View.GONE)

        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.setLayoutParams(
            FrameLayout.LayoutParams(
                (display.width * 0.85).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        return this
    }

    fun setTitle(title: String): UYTAlertDialog3 {
        showTitle = true
        if ("" == title) {
            titleTv!!.text = "标题"
        } else {
            titleTv!!.text = title
        }
        return this
    }

    fun setMsg(msg: String): UYTAlertDialog3 {
        showMsg = true
        if ("" == msg) {
            msgTv!!.text = "内容"
        } else {
            msgTv!!.text = msg
        }
        return this
    }

    fun setCancelable(cancel: Boolean): UYTAlertDialog3 {
        dialog!!.setCancelable(cancel)
        return this
    }

    fun setPositiveButton(
        text: String,
        listener: View.OnClickListener?
    ): UYTAlertDialog3 {
        showPosBtn = true
        if ("" == text) {
            posBtn?.setText("确定")
        } else {
            posBtn?.setText(text)
        }
        posBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        })
        return this
    }

    fun setNegativeButton(
        text: String,
        listener: View.OnClickListener?
    ): UYTAlertDialog3 {
        showNegBtn = true
        if ("" == text) {
            negBtn?.setText("取消")
        } else {
            negBtn?.setText(text)
        }
        negBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        })
        return this
    }

    private fun setLayout() {
        if (!showTitle && !showMsg) {
            titleTv?.text = "提示"
            titleTv?.visibility = View.VISIBLE
        }
        if (showTitle) {
            titleTv?.visibility = View.VISIBLE
        }
        if (showMsg) {
            msgTv?.visibility = View.VISIBLE
        }
        if (!showPosBtn && !showNegBtn) {
            posBtn?.setText("确定")
            posBtn?.setVisibility(View.VISIBLE)
            posBtn?.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
        }
        if (showPosBtn && showNegBtn) {
            posBtn?.setVisibility(View.VISIBLE)
            negBtn?.setVisibility(View.VISIBLE)
            img_line?.visibility = View.VISIBLE
        }
        if (showPosBtn && !showNegBtn) {
            posBtn?.setShapeCornersBottomLeftRadius(5.toFloat())?.setShapeCornersBottomRightRadius(5.toFloat())
                ?.setUseShape()
            posBtn?.setUseShape()
            posBtn?.setVisibility(View.VISIBLE)
        }
        if (!showPosBtn && showNegBtn) {
            negBtn?.setShapeCornersBottomLeftRadius(5.toFloat())?.setShapeCornersBottomRightRadius(5.toFloat())
                ?.setUseShape()
            negBtn?.setVisibility(View.VISIBLE)
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