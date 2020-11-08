package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.home.Entity.GetUtkEntity
import java.util.*


class ReminderDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var container: LinearLayout? = null
    private var mBtn: Button? = null
    private val display: Display
    private var showTitle = false
    private var showMsg = false
    var countDownTimer:CountDownTimer?=null
    private fun init(): ReminderDialog {
        // 获取Dialog布局
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.view_reminder_dialog, null)

        // 获取自定义Dialog布局中的控件
        container = view.findViewById(R.id.container)

        mBtn = view.findViewById(R.id.btn)
        var counts1: Int = Random().nextInt(9) + 1
        var counts2: Int = Random().nextInt(9) + 1

        var countsResult=counts1+counts2



        mBtn?.setOnClickListener {
            listener?.onRequest()
                dialog?.dismiss()
        }


        // 定义Dialog布局和参数
        dialog = Dialog(context, R.style.AlertDialogStyle)
        dialog!!.setContentView(view)

        // 调整dialog背景大小
        container?.layoutParams = FrameLayout.LayoutParams(
            (display.width * 0.8).toInt(),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        return this
    }


    fun setCancelable(cancel: Boolean): ReminderDialog {
        dialog!!.setCancelable(cancel)
        return this
    }
    private var listener:RequestListener?=null
    open fun setOnRequestListener(requestListener: RequestListener):ReminderDialog {
        listener=requestListener
        return this
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