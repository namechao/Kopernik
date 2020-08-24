package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.viewmodel.RegisterSetUpPasswordViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_forget_password_next.*
import kotlinx.android.synthetic.main.activity_set_up_password.*
import kotlinx.android.synthetic.main.activity_set_up_password.confirmBtn
import kotlinx.android.synthetic.main.activity_set_up_password.etInput
import kotlinx.android.synthetic.main.activity_set_up_password.etInputAgain
import kotlinx.android.synthetic.main.activity_set_up_password.icClear
import kotlinx.android.synthetic.main.activity_set_up_password.icClear1
import kotlinx.android.synthetic.main.activity_set_up_password.icEye
import kotlinx.android.synthetic.main.activity_set_up_password.icEye1
import java.util.*

class RegisterSetUpPasswordActivity :
    NewBaseActivity<RegisterSetUpPasswordViewModel, ViewDataBinding>() {
    private var type=-1
    private var acount=""
    private var invitationCode=""
    private var openEye=false
    private var openEye1=false
    override fun layoutId() = R.layout.activity_set_up_password
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_set_password))
        intent.getIntExtra("type",-1)?.let {
           type =it
        }
        intent.getStringExtra("acount")?.let {
            acount =it
        }
        intent.getStringExtra("invitationCode")?.let {
            invitationCode =it
        }
        
        etInput?.hint =
            getString(R.string.please_input_pass)
        etInputAgain?.hint =
            getString(R.string.please_input_pass_again)
        etInput?.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        etInputAgain?.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
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
    }

    override fun initData() {

        confirmBtn.setOnClickListener {
            if (!(etInput!!.text.toString().trim().length in 8..16&&StringUtils.isContainAll(etInput!!.text.toString().trim()))){
                ToastUtils.showShort(getActivity(), getString(R.string.intput_password_error))
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

    //导入网络请求
    private fun register() {
        viewModel.run {
            createAccount(type.toString(),acount,invitationCode,MD5Utils.md5(etInput?.text.toString().trim())).observe(this@RegisterSetUpPasswordActivity, androidx.lifecycle.Observer {
             if (it.status==200){
                 UserConfig.singleton?.accountBean=it.data.user
                 ToastUtils.showShort(this@RegisterSetUpPasswordActivity,this@RegisterSetUpPasswordActivity.getString(R.string.register_success))
                 LaunchConfig.startMainAc(this@RegisterSetUpPasswordActivity)
             }else{
                 ToastUtils.showShort(this@RegisterSetUpPasswordActivity,it.status.toString())
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
            confirmBtn.isEnabled = str.isNotEmpty()
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
            val str = etInputAgain!!.text.toString()
            confirmBtn.isEnabled = str.isNotEmpty()
        }
    }


}