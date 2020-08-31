package com.kopernik.ui.my

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import com.kopernik.ui.login.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_trade_password.*
import java.util.ArrayList

class ForgetTradePasswordActivity : NewBaseActivity<RegisterViewModel, ViewDataBinding>() {

    private  var registerType=-1
    private  var changeTradePasswordType=-1
    override fun layoutId()=R.layout.activity_trade_password

    override fun initView(savedInstanceState: Bundle?) {
       
        intent.getIntExtra("changeTradePasswordType",-1)?.let{
            changeTradePasswordType=it
        }
        if (changeTradePasswordType==1) {
            setTitle(resources.getString(R.string.title_set_trade_psw))
        }else{
            setTitle(resources.getString(R.string.title_change_trade_psw))
        }
        intent.getIntExtra("registerType",-1)?.let{
            registerType=it
        }
        if (registerType==1){
            //手机登录
            etInput.hint=resources.getString(R.string.login_input_phone_hint)
            etInput.inputType= InputType.TYPE_CLASS_PHONE
            etInput.hint=resources.getString(R.string.login_input_phone_hint)
            llHeader.visibility= View.VISIBLE
            etInput.setText("")
        }else {
            //邮箱登录
            etInput.hint = resources.getString(R.string.login_input_email_hint)
            etInput.inputType = InputType.TYPE_CLASS_TEXT
            etInput.hint=resources.getString(R.string.login_input_email_hint)
            etInput.setText("")
            llHeader.visibility = View.GONE
        }
        //清除按钮
        icClear.setOnClickListener {
            etInput.setText("")
        }
        //选择区域
        llHeader.setOnClickListener {
            showDialog()
        }
        //获取验证码
        tvCode.setOnClickListener {

            //手机登录
            if (registerType==1) {
                if (etInput.text.toString().trim().isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                    return@setOnClickListener
                }
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
                viewModel.run {
                    sendCode(etInput.text.toString().trim()).observeForever {
                        if (it.status == 200) {
                            timer.start()
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetTradePasswordActivity, it.status)
                        }
                    }
                }
            }else{//邮箱获取验证码
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }

                viewModel.run {
                    sendEmailCode(etInput.text.toString().trim()).observeForever {
                        if (it.status == 200) {
                            timer.start()
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetTradePasswordActivity, it.status)
                        }
                    }
                }
            }

        }
        //下一步按钮
        confirmBtn.setOnClickListener {
            if (registerType==1){
                //手机号不为空
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
                //验证码
                if (etVerifyCode.text.toString().trim().isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.re_input_code))
                    return@setOnClickListener
                }
                if (etVerifyCode.text.toString().trim().length!=6){
                    ToastUtils.showShort(this, resources.getString(R.string.input_verify_code_error))
                    return@setOnClickListener
                }
                viewModel.run {
                    checkPhone(etInput.text.toString().trim(),etVerifyCode.text.toString().trim()).observe(this@ForgetTradePasswordActivity, Observer {
                        if (it.status == 200) {
                            LaunchConfig.startTradePasswordNextActivity(this@ForgetTradePasswordActivity,
                                changeTradePasswordType
                            )
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetTradePasswordActivity, it.status)
                        }
                    })
                }
            }else{
                //邮箱不为空
                if (etInput.text.toString().trim().isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                if (!etInput.text.toString()
                        .trim().matches(
                            Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }
                //验证码
                if (etVerifyCode.text.toString().trim().isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.re_input_code))
                    return@setOnClickListener
                }
                if (etVerifyCode.text.toString().trim().length!=6){
                    ToastUtils.showShort(this, resources.getString(R.string.input_verify_code_error))
                    return@setOnClickListener
                }
                viewModel.run {
                    checkEMail(etInput.text.toString().trim(),etVerifyCode.text.toString().trim()).observe(this@ForgetTradePasswordActivity, Observer {
                        if (it.status == 200) {
                            LaunchConfig.startTradePasswordNextActivity(this@ForgetTradePasswordActivity,
                                changeTradePasswordType
                            )
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetTradePasswordActivity, it.status)
                        }
                    })
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        finish()
    }
    override fun initData() {

    }
    //计时器定时
    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tvCode.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
            tvCode.setTextColor(ContextCompat.getColor(baseContext, R.color.color_5D5386))
            tvCode.isClickable = false
        }

        override fun onFinish() {
            tvCode.text = "重新获取"
            tvCode.setTextColor(ContextCompat.getColor(baseContext, R.color.color_F4C41B))
            tvCode.isClickable = true
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
    fun showDialog(){
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_chose_area_code, null)
        // 创建PopupWindow对象，指定宽度和高度
        val popupWindow =
            PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        var recycleView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recycleView?.layoutManager= LinearLayoutManager(this)
        val list: MutableList<LoginCountryBean> = ArrayList()
        list.add(LoginCountryBean(R.mipmap.ic_china,"+86"))
        list.add(LoginCountryBean(R.mipmap.ic_korea,"+82"))
        list.add(LoginCountryBean(R.mipmap.ic_england,"+44"))
        list.add(LoginCountryBean(R.mipmap.ic_america,"+1"))
        list.add(LoginCountryBean(R.mipmap.ic_australia,"+61"))
        list.add(LoginCountryBean(R.mipmap.ic_singapore,"+65"))
        var adapter= ChoseAreaAdapter(list)
        adapter.setOnItemClickListener { adapter, view, position ->
            tvPhoneHead.text=(adapter.data[position] as LoginCountryBean).header
            ivPhoneHead.setImageResource((adapter.data[position] as LoginCountryBean).resId)
            popupWindow.dismiss()
        }
        recycleView?.adapter=adapter


//        window.width = recycleView.getWidth()
        // 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // 设置可以获取焦点
        popupWindow.isFocusable = true
        // 设置可以触摸弹出框以外的区域
        popupWindow.isOutsideTouchable = true
        // 更新popupwindow的状态
        popupWindow.update()
        // 以下拉的方式显示，并且可以设置显示的位置
        popupWindow.showAsDropDown(llHeader, 0, 20)
    }
}