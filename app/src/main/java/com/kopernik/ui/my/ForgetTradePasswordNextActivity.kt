package com.kopernik.ui.my

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod

import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.my.ViewModel.ForgetTradePasswordViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_trade_password_next.*


class ForgetTradePasswordNextActivity : NewBaseActivity<ForgetTradePasswordViewModel, ViewDataBinding>() {
    private var openEye=false
    private var openEye1=false
   var  changeTradePasswordType=-1
    override fun layoutId()=R.layout.activity_trade_password_next
    override fun initView(savedInstanceState: Bundle?) {
        intent.getIntExtra("changeTradePasswordType",-1)?.let {
            changeTradePasswordType=it
        }
        if (changeTradePasswordType==1) {
            setTitle(resources.getString(R.string.title_set_trade_psw))
        }else{
            setTitle(resources.getString(R.string.title_change_trade_psw))
        }

        etTradeInput?.addTextChangedListener(
            passwordInputListener
        )
        etTradeInputAgain?.addTextChangedListener(
            passwordInputListener1
        )
        icClear.setOnClickListener {
            etTradeInput.setText("")
        }
        icClear1.setOnClickListener {
            etTradeInputAgain.setText("")
        }
        icEye.setOnClickListener {
            if (!openEye){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etTradeInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                openEye = true
                etTradeInput.setSelection(etTradeInput.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etTradeInput.transformationMethod = PasswordTransformationMethod.getInstance()
                openEye = false
                etTradeInput.setSelection(etTradeInput.text.toString().length)
                icEye.setImageResource(R.mipmap.ic_close_eye)
            }
        }
        icEye1.setOnClickListener {
            if (!openEye1){//开眼逻辑
                //从密码不可见模式变为密码可见模式
                etTradeInputAgain.transformationMethod = HideReturnsTransformationMethod.getInstance()
                openEye1 = true
                etTradeInputAgain.setSelection(etTradeInputAgain.text.toString().length)
                icEye1.setImageResource(R.mipmap.ic_open_eye)
            }else{//闭眼逻辑
                //从密码可见模式变为密码不可见模式
                etTradeInputAgain.transformationMethod = PasswordTransformationMethod.getInstance()
                openEye1 = false
                etTradeInputAgain.setSelection(etTradeInputAgain.text.toString().length)
                icEye1.setImageResource(R.mipmap.ic_close_eye)
            }
        }
        confirmBtn.setOnClickListener {
            if (etTradeInputAgain!!.text.toString().trim().isNullOrEmpty()){
                ToastUtils.showShort(getActivity(), getString(R.string.input_pasword_again_error))
                return@setOnClickListener
            }
            //点击按钮密码输入俩次是否一致
            if (etTradeInput!!.text.toString().trim() != etTradeInputAgain!!.text.toString().trim()) {
                ToastUtils.showShort(getActivity(), getString(R.string.twice_password_error))
                return@setOnClickListener
            }
            setTradePsw()
        }

    }

    override fun initData() {
    }
    //导入网络请求
    private fun setTradePsw() {
        viewModel.run {
            var map= mapOf("pwd" to etTradeInput.text.toString().trim() )
            changeTradePsw(map).observe(this@ForgetTradePasswordNextActivity, androidx.lifecycle.Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@ForgetTradePasswordNextActivity,this@ForgetTradePasswordNextActivity.getString(R.string.forget_trade_password_success))
                    setResult(14)
                    finish()
                }else{
                    ToastUtils.showShort(this@ForgetTradePasswordNextActivity,it.errorMsg)
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
            val str = etTradeInput!!.text.toString()
            val str1=etTradeInputAgain!!.text.toString()
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
            val str = etTradeInput!!.text.toString()
            val str1=etTradeInputAgain!!.text.toString()
            confirmBtn.isEnabled = str.isNotEmpty()&&str1.isNotEmpty()
        }
    }
}