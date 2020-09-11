package com.kopernik.ui.login

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
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.VerifyCodeAlertDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.adapter.ChoseAreaAdapter
import com.kopernik.ui.login.bean.LoginCountryBean
import com.kopernik.ui.login.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.confirmBtn
import kotlinx.android.synthetic.main.activity_forget_password.etInput
import kotlinx.android.synthetic.main.activity_forget_password.etVerifyCode
import kotlinx.android.synthetic.main.activity_forget_password.icClear
import kotlinx.android.synthetic.main.activity_forget_password.ivPhoneHead
import kotlinx.android.synthetic.main.activity_forget_password.llHeader
import kotlinx.android.synthetic.main.activity_forget_password.tvCode
import kotlinx.android.synthetic.main.activity_forget_password.tvPhoneHead
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_trade_password.*
import java.util.ArrayList

class ForgetPasswordActivity : NewBaseActivity<RegisterViewModel, ViewDataBinding>() {

    private  var registerType=-1
    private  var changePasswordType=-1
    private  var phoneNumber=""
    override fun layoutId()=R.layout.activity_forget_password

    override fun initView(savedInstanceState: Bundle?) {
        intent.getIntExtra("changePasswordType",-1)?.let{
            changePasswordType=it
        }
        if (changePasswordType==1) {
            setTitle(resources.getString(R.string.login_forget_psw))
        }else{
            setTitle(resources.getString(R.string.login_change_password))
            llHeader.isEnabled=false
            icClear.visibility=View.GONE
            etInput.isEnabled=false
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
            if (changePasswordType==2){
                var phone= UserConfig.singleton?.accountBean?.phone
                if (phone!=null){
                    phoneNumber=phone
                    if (phone.length>5){
                        etInput.setText("${phone.subSequence(0,3)}****${phone.subSequence(phone.length-4,phone.length)}")
                    }
                }
            }
        }else {
            //邮箱登录
                etInput.hint = resources.getString(R.string.login_input_email_hint)
                etInput.inputType = InputType.TYPE_CLASS_TEXT
            etInput.hint=resources.getString(R.string.login_input_email_hint)
                etInput.setText("")
                llHeader.visibility = View.GONE
            if (changePasswordType==2){
                var email= UserConfig.singleton?.accountBean?.email
                if (email!=null){
                    phoneNumber=email
                    etInput.setText(email)
                }
            }
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
            if (changePasswordType==1){
                phoneNumber=etInput.text.toString().trim()
            }
            //手机登录
            if (registerType==1) {
                if (phoneNumber.isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                return@setOnClickListener
                }
                if (!phoneNumber.matches(Regex("1[0-9]{10}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                    return@setOnClickListener
                }
                viewModel.run {
                    checkUser(phoneNumber).observe(this@ForgetPasswordActivity, Observer {
                        if (it.status==200)  {
                            VerifyCodeAlertDialog(this@ForgetPasswordActivity,phoneNumber)
                                .setCancelable(false)
                                .setPositiveButton(object : VerifyCodeAlertDialog.RequestListener{
                                    override fun onRequest(imageVerifyCode: String) {
                                        viewModel.run {
                                            sendCode(phoneNumber,imageVerifyCode).observe(this@ForgetPasswordActivity,
                                                Observer {
                                                    if (it.status == 200) {
                                                        timer.start()
                                                    } else {
                                                        ErrorCode.showErrorMsg(this@ForgetPasswordActivity, it.status)
                                                    }
                                                })
                                        }
                                    }

                                })
                                .show()
                        } else{
                            ErrorCode.showErrorMsg(this@ForgetPasswordActivity, it.status)
                        }
                    })
                }



            }else{//邮箱获取验证码
                if (phoneNumber.isNullOrEmpty()) {
                    ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                    return@setOnClickListener
                }
                if (!phoneNumber.matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
                ) {
                    ToastUtils.showShort(this, resources.getString(R.string.verify_email_error))
                    return@setOnClickListener
                }

                viewModel.run {
                    sendEmailCode(phoneNumber).observeForever {
                        if (it.status == 200) {
                            timer.start()
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetPasswordActivity, it.status)
                        }
                    }
                }
            }

        }
        //下一步按钮
        confirmBtn.setOnClickListener {
            if (changePasswordType==1){
                phoneNumber=etInput.text.toString().trim()
            }
            if (registerType==1){
                //手机号不为空
                if (phoneNumber.isNullOrEmpty()){
                    ToastUtils.showShort(this, resources.getString(R.string.phone_not_empty))
                    return@setOnClickListener
                }
                if (!phoneNumber.matches(Regex("1[0-9]{10}"))
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
                    checkPhone(phoneNumber,etVerifyCode.text.toString().trim()).observe(this@ForgetPasswordActivity, Observer {
                        if (it.status == 200) {
                            LaunchConfig.startForgetPasswordNextActivity(this@ForgetPasswordActivity,
                                1,
                                changePasswordType,
                                phoneNumber,
                                etVerifyCode.text.toString().trim()
                            )
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetPasswordActivity, it.status)
                        }
                    })
                }
            }else{
                //邮箱不为空
                if (phoneNumber.isNullOrEmpty()) ToastUtils.showShort(this, resources.getString(R.string.email_not_empty))
                if (!phoneNumber.matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"))
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
                    checkEMail(phoneNumber,etVerifyCode.text.toString().trim()).observe(this@ForgetPasswordActivity, Observer {
                        if (it.status == 200) {
                            LaunchConfig.startForgetPasswordNextActivity(this@ForgetPasswordActivity,
                                2,
                                changePasswordType,
                                phoneNumber,
                                etVerifyCode.text.toString().trim()
                            )
                        } else {
                            ErrorCode.showErrorMsg(this@ForgetPasswordActivity, it.status)
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