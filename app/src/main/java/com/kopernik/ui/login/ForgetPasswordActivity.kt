package com.kopernik.ui.login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.confirmBtn
import kotlinx.android.synthetic.main.activity_forget_password.etInput
import kotlinx.android.synthetic.main.activity_forget_password.icClear
import kotlinx.android.synthetic.main.activity_forget_password.tvCode
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_set_up_password.*

class ForgetPasswordActivity : NewBaseActivity<RegisterViewModel, ViewDataBinding>() {


    override fun layoutId()=R.layout.activity_forget_password

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(resources.getString(R.string.login_change_password))
        confirmBtn.setOnClickListener {
            LaunchConfig.startForgetPasswordNextActivity(this)
        }
        icClear.setOnClickListener {
            etInput.setText("")
        }
        //获取验证码
        tvCode.setOnClickListener {

            //手机登录
            if (etInput.text.toString().trim().isNullOrEmpty() && !etInput.text.toString()
                    .trim().matches(
                        Regex("1[0-9]{10}")
                    )
            ) {
                ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                return@setOnClickListener
            }
            viewModel.run {
                sendCode(etInput.text.toString().trim()).observeForever {
                    if (it.status == 200) {
                        timer.start()
                    } else {
                        ToastUtils.showShort(this@ForgetPasswordActivity, it.errorMsg)
                    }
                }
            }

        }
        //下一步按钮
        confirmBtn.setOnClickListener {
            //手机登录
            if (etInput.text.toString().trim().isNullOrEmpty() && !etInput.text.toString()
                    .trim().matches(
                        Regex("1[0-9]{10}")
                    )
            ) {
                ToastUtils.showShort(this, resources.getString(R.string.verify_phone_error))
                return@setOnClickListener
            }
            if (etInviteCode.text.toString().trim().isNullOrEmpty()){
                ToastUtils.showShort(this, resources.getString(R.string.verify_invite_code_error))
                return@setOnClickListener
            }
            if (etVerifyCode.text.toString().trim().length!=6){
                ToastUtils.showShort(this, resources.getString(R.string.input_verify_code_error))
                return@setOnClickListener
            }

                viewModel.run {
                    checkPhone(etInput.text.toString().trim(),verifyCode.text.toString().trim()).observe(this@ForgetPasswordActivity, Observer {
                        if (it.status == 200) {
                            LaunchConfig.startRegisterSetUpPasswordActivity(this@ForgetPasswordActivity,
                                "1",
                                etInput.text.toString().trim()
                                ,
                                etInviteCode.text.toString().trim()
                            )
                        } else {
                            ToastUtils.showShort(this@ForgetPasswordActivity, it.errorMsg)
                        }
                    })
                }

        }

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

}