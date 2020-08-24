package com.kopernik.ui.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.my.ViewModel.RealNameViewModel
import kotlinx.android.synthetic.main.activity_real_name_authentication.*
import kotlinx.android.synthetic.main.activity_real_name_authentication.confirmBtn

class RealNameAuthenticationActivity : NewBaseActivity<RealNameViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_real_name_authentication

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_real_authentic))
    }

    override fun initData() {
        etName.addTextChangedListener(passwordInputListener)
        etIdCard.addTextChangedListener(passwordInputListener1)
        icClear.setOnClickListener {
            etName.setText("")
        }
        icClear1.setOnClickListener {
            etIdCard.setText("")
        }
        //认证按钮
        confirmBtn.setOnClickListener {
            viewModel.realNameAuth(etName.text.toString().trim(), etIdCard.text.toString().trim()).observe(this,
                Observer {
                    if (it.status==200){
                        ToastUtils.showShort(this@RealNameAuthenticationActivity,getString(R.string.name_auth_sucess))
                        finish()
                    }else{
                        ToastUtils.showShort(this@RealNameAuthenticationActivity, it.errorMsg)
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
            val str = etName!!.text.toString()
            val str1 = etIdCard!!.text.toString()
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
            val str = etName!!.text.toString()
            val str1 = etIdCard!!.text.toString()
            confirmBtn.isEnabled = str.isNotEmpty()&&str1.isNotEmpty()
        }
    }
}