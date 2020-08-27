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
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kopernik.R
import com.kopernik.app.utils.TimeUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.home.Entity.GetUtkEntity
import java.util.*


class ReminderDialog(private val context: Context,  private var getUtkEntity:GetUtkEntity) {
    private var dialog: Dialog? = null
    private var container: ConstraintLayout? = null
    private var msgTv: TextView? = null
    private var tvCount: TextView? = null
    private var etResult: EditText? = null
    private var mBtn: Button? = null
    private var tvTiming:TextView?=null
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
        tvTiming = view.findViewById(R.id.tvTiming)
        msgTv = view.findViewById(R.id.txt_msg)
        tvCount = view.findViewById(R.id.tvCount)
        etResult = view.findViewById(R.id.etResult)
        mBtn = view.findViewById(R.id.btn)
        etResult?.addTextChangedListener(watcher)
        var counts1: Int = Random().nextInt(9) + 1
        var counts2: Int = Random().nextInt(9) + 1
        tvCount?.text="$counts1+$counts2=?"
        var countsResult=counts1+counts2
        //验证码
        tvCount?.setOnClickListener {
            counts1 =Random().nextInt(9) + 1
            counts2= Random().nextInt(9) + 1
            tvCount?.text="$counts1+$counts2=?"
            countsResult=counts1+counts2
        }
        msgTv?.text=getUtkEntity?.config?.onceCount
        var hour = TimeUtils.currentTimeMillisecond()
        if (getUtkEntity?.time == null) {//1.上次没有领取 直接领取
            tvTiming?.text = "00:00:00"
        }else if (getUtkEntity?.config?.onceTime!=null&&hour - getUtkEntity?.time!!.toLong() > getUtkEntity?.config?.onceTime!! * 60 * 60) {//2.大于间隔时间
            tvTiming?.text = "00:00:00"
        }else if (getUtkEntity?.config?.onceTime!=null&&hour - getUtkEntity?.time!!.toLong() < getUtkEntity?.config?.onceTime!! * 60 * 60){//倒计时
           var countDownTime=getUtkEntity?.config?.onceTime!! * 60 *1000-(hour - getUtkEntity?.time!!.toLong())
            countDownTimer=object :CountDownTimer(countDownTime,1000){
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {
                    var day=millisUntilFinished/(1000*60*60*24)//天
                    var hour=(millisUntilFinished-day*(1000*60*60*24))/(1000*60*60)//时
                    var hourStr=""
                    if (hour<10) hourStr="0$hour" else hourStr="$hour"
                    var minute=(millisUntilFinished-day*(1000*60*60*24)-hour*(1000*60*60))/(1000*60)//分
                    var minuteStr=""
                    if (minute<10) minuteStr="0$minute" else minuteStr="$minute"
                    var second=(millisUntilFinished-day*(1000*60*60*24)-hour*(1000*60*60)-minute*(1000*60))/1000//秒
                    var secondStr=""
                    if (second<10) secondStr="0$second" else  secondStr="$second"
                    tvTiming?.text = "$hourStr:$minuteStr:$secondStr"
                }
                }.start()

        }

        mBtn?.setOnClickListener {
            if (etResult?.text.toString().trim().isNullOrEmpty()){
                ToastUtils.showShort(context,context.getString(R.string.input_counts_result))
                return@setOnClickListener
            }
            if (etResult?.text.toString().trim() != "$countsResult"){
                ToastUtils.showShort(context,context.getString(R.string.input_counts_result_error))
                return@setOnClickListener
            }
            if (tvTiming?.text=="00:00:00") {
                listener?.let { it.onRequest() }
                countDownTimer?.cancel()
                dialog?.dismiss()
            }else{
                ToastUtils.showShort(context,context.getString(R.string.recvice_not_time))
            }

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


    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {

            if (etResult!!.text.toString().isNotEmpty()) {
                mBtn?.isEnabled = true
                mBtn?.setTextColor(context.getColor(R.color.color_20222F))

            } else {
                mBtn?.setTextColor(context.getColor(R.color.color_5D5386))
                mBtn?.isEnabled = false
            }
        }
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