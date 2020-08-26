package com.kopernik.app.dialog

import android.app.Dialog
import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kopernik.R
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.home.Entity.GetUtkEntity
import java.util.*


class ReminderDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var container: ConstraintLayout? = null
    private var getUtkEntity:GetUtkEntity?=null
    private var msgTv: TextView? = null
    private var tvCount: TextView? = null
    private var etResult: EditText? = null
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
        tvCount = view.findViewById(R.id.tvCount)
        etResult = view.findViewById(R.id.etResult)
        val counts1: Int = Random().nextInt(9) + 1
        val counts2: Int = Random().nextInt(9) + 1
        tvCount?.text="$counts1+$counts2=?"
        var countsResult=counts1+counts2

        mBtn = view.findViewById(R.id.btn)
        mBtn?.setOnClickListener {
            if (etResult?.text.toString().trim().isNullOrEmpty()){
                ToastUtils.showShort(context,context.getString(R.string.input_counts_result))
                return@setOnClickListener
            }
            if (etResult?.text.toString().trim() != "$countsResult"){
                ToastUtils.showShort(context,context.getString(R.string.input_counts_result_error))
                return@setOnClickListener
            }
            listener?.let { it.onRequest() }
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
    fun setUtkEntity(getUtkEntity: GetUtkEntity):ReminderDialog {
      this.getUtkEntity=getUtkEntity
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