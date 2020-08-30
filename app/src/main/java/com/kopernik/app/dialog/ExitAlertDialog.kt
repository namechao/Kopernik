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


class ExitAlertDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var negBtn: SuperButton? = null
    private var posBtn: SuperButton? = null
    private val display: Display
    private var container: LinearLayout? = null
    private fun init(): ExitAlertDialog {
        // 获取Dialog布局
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.exit_alertdialog, null)
        container = view.findViewById(R.id.container)
        negBtn = view.findViewById(R.id.btn_neg)
        posBtn = view.findViewById(R.id.btn_pos)


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



    fun setCancelable(cancel: Boolean): ExitAlertDialog {
        dialog!!.setCancelable(cancel)
        return this
    }

    fun setPositiveButton(
        listener: View.OnClickListener?
    ): ExitAlertDialog {
        posBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        })
        return this
    }

    fun setNegativeButton(
        text: String,
        listener: View.OnClickListener?
    ): ExitAlertDialog {
        negBtn?.setOnClickListener(View.OnClickListener { v ->
            listener?.onClick(v)
            dialog!!.dismiss()
        })
        return this
    }

    private fun setLayout() {
        negBtn?.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
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