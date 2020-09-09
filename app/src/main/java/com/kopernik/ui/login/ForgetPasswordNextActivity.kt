package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod

import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.viewmodel.ForgetPasswordViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_forget_password_next.*
import kotlinx.android.synthetic.main.activity_forget_password_next.confirmBtn
import kotlinx.android.synthetic.main.activity_forget_password_next.etInput
import kotlinx.android.synthetic.main.activity_forget_password_next.icClear
import kotlinx.android.synthetic.main.activity_forget_password_next.icEye

class ForgetPasswordNextActivity : NewBaseActivity<ForgetPasswordViewModel, ViewDataBinding>() {
    private var openEye=false
    private var openEye1=false
    private var type=-1
    private  var changePasswordType=-1
    private var account=""
    private var verifyCode=""
    override fun layoutId()=R.layout.activity_forget_password_next
    override fun initView(savedInstanceState: Bundle?) {
        intent.getIntExtra("changePasswordType",-1)?.let{
            changePasswordType=it
        }
        intent.getIntExtra("registerType",-1)?.let {
            type =it
        }
        intent.getStringExtra("account")?.let {
            account =it
        }
        intent.getStringExtra("verifyCode")?.let {
            verifyCode =it
        }
        if (changePasswordType==1) {
            setTitle(resources.getString(R.string.login_forget_psw))
        }else{
            setTitle(resources.getString(R.string.login_change_password))
        }
        etInput?.addTextChangedListener(
            passwordInputListener
        )
        etInputAgain?.addTextChangedListener(
            passwordInputListener1
        )
        icClear.setOnClickListener {
            etInput.setText("")
        }
        icClear1.setOnClickListener {
            etInputAgain.setText("")
        }
        icEye.setOnClickListener {
            if (!openEye){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                openEye = true
                etInput.setSelection(etInput.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etInput.transformationMethod = PasswordTransformationMethod.getInstance()
                openEye = false
                etInput.setSelection(etInput.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_close_eye)
            }
        }
        icEye1.setOnClickListener {
            if (!openEye1){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etInputAgain.transformationMethod = HideReturnsTransformationMethod.getInstance()
                openEye1 = true
                etInputAgain.setSelection(etInputAgain.text.toString().length)
                icEye1.setImageResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etInputAgain.transformationMethod = PasswordTransformationMethod.getInstance()
                openEye1 = false
                etInputAgain.setSelection(etInputAgain.text.toString().length)
                icEye1.setImageResource(R.mipmap.ic_close_eye)
            }
        }
        confirmBtn.setOnClickListener {
            if (!(etInput!!.text.toString().trim().length in 8..16&& StringUtils.isContainAll(etInput!!.text.toString().trim()))){
                ToastUtils.showShort(getActivity(), getString(R.string.intput_password_error))
                return@setOnClickListener
            }
            if (etInputAgain!!.text.toString().trim().isNullOrEmpty()){
                ToastUtils.showShort(getActivity(), getString(R.string.input_pasword_again_error))
                return@setOnClickListener
            }
            //点击按钮密码输入俩次是否一致
            if (etInput!!.text.toString().trim() != etInputAgain!!.text.toString().trim()) {
                ToastUtils.showShort(getActivity(), getString(R.string.twice_password_error))
                return@setOnClickListener
            }
            register()
        }
    }

    override fun initData() {
    }
    //导入网络请求
    private fun register() {
        viewModel.run {
            forgetPassword(type.toString(),account, MD5Utils.md5(MD5Utils.md5(etInput?.text.toString().trim())),verifyCode).observe(this@ForgetPasswordNextActivity, androidx.lifecycle.Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@ForgetPasswordNextActivity,this@ForgetPasswordNextActivity.getString(R.string.forget_password_success))
                    setResult(11)
                    finish()
                }else{
                    ErrorCode.showErrorMsg(this@ForgetPasswordNextActivity,it.status)
                }
            })
        }
    }


    private var passwordInputListener: TextWatcher = object : TextWatcher {
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
            val str = etInput!!.text.toString()
            val str1=etInputAgain!!.text.toString()
            confirmBtn.isEnabled = str.isNotEmpty()&&str1.isNotEmpty()
        }
    }
    private var passwordInputListener1: TextWatcher = object : TextWatcher {
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
            val str = etInput!!.text.toString()
            val str1=etInputAgain!!.text.toString()
            confirmBtn.isEnabled = str.isNotEmpty()&&str1.isNotEmpty()
        }
    }

}