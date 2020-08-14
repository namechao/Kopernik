package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.login.bean.AccountListBean
import com.kopernik.ui.login.bean.Mnemonic
import com.kopernik.ui.login.viewmodel.CreateAccountViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : NewBaseActivity<CreateAccountViewModel, ViewDataBinding>() {
    companion object{
        var MNEMONIC_EXTRA = "mnemonicStr"
    }

    private val mnemonicList: List<String>? = null
    private var mnemonicBean:Mnemonic?=null
    override fun layoutId()=R.layout.activity_create_account

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("创建账户")
        mnemonicBean =
            intent.getSerializableExtra(MNEMONIC_EXTRA) as Mnemonic
        password.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        confirmPassword.inputType =
            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
    }


    override fun initData() {
        okBtn.setOnClickListener {
            if (password.text.toString() != confirmPassword.text.toString()) {
                ToastUtils.showShort(getActivity(), getString(R.string.twice_password_error))
                return@setOnClickListener
            }
            if (password.text.toString().length < 8) {
                ToastUtils.showShort(getActivity(), getString(R.string.password_too_short))
                return@setOnClickListener
            }
            if (label.text.toString().isEmpty()) {
                ToastUtils.showShort(getActivity(), getString(R.string.please_input_label))
                return@setOnClickListener
            }

            if (!StringUtils.isContainAll(password.text.toString())) {
                ToastUtils.showShort(getActivity(), getString(R.string.password_input_error))
                return@setOnClickListener
            }
            createdAccount()
        }
        password.addTextChangedListener(object : TextWatcher {
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
                val str = password.text.toString()
                if (StringUtils.isContainUpperCase(str)) {
                    pwdLettersTv.setTextColor(resources.getColor(R.color.text_gray))
                } else {
                    pwdLettersTv.setTextColor(resources.getColor(R.color.red))
                }
                if (StringUtils.isContainNumber(str)) {
                    pwdNumberTv.setTextColor(resources.getColor(R.color.text_gray))
                } else {
                    pwdNumberTv.setTextColor(resources.getColor(R.color.red))
                }
                if (StringUtils.isContainLowerCase(str)) {
                    pwdLettersBothTv.setTextColor(resources.getColor(R.color.text_gray))
                } else {
                    pwdLettersBothTv.setTextColor(resources.getColor(R.color.red))
                }
                if (str.length > 7) {
                    pwdLengthTv.setTextColor(resources.getColor(R.color.text_gray))
                } else {
                    pwdLengthTv.setTextColor(resources.getColor(R.color.red))
                }
            }
        })
    }

    private fun createdAccount() {
        if (mnemonicBean==null||mnemonicBean?.mnemonic?.size==0)return
        val sb = StringBuilder()
        for (i in mnemonicBean?.mnemonic?.indices!!) {
            sb.append(mnemonicBean?.mnemonic?.get(i)).append(" ")
        }
        val mnemonicStr = sb.toString().trim()
        val mnemonicMd5 = MD5Utils.md5(mnemonicStr)
        viewModel.run {
           createAccount(
               label.text.toString().trim(),
               password.text.toString().trim(),
               mnemonicBean?.address!!,
               mnemonicMd5
           ).observe(
               this@CreateAccountActivity,
               Observer {
                   if (it.status == 200) {
                       ToastUtils.showShort(
                           getActivity(),
                           getActivity()!!.resources.getString(R.string.tip_create_account_success)
                       )
                       val gson = Gson()
                       val accountBean: AccountBean = it.data
                       UserConfig.singleton?.accountString = gson.toJson(accountBean)
                   val accountListBean: AccountListBean
                   val allAccountListStr: String? = UserConfig.singleton?.allAccount
                   accountListBean = if (allAccountListStr.isNullOrEmpty()) {
                       AccountListBean()
                   } else {
                       gson.fromJson<AccountListBean>(
                           allAccountListStr,
                           AccountListBean::class.java
                       )
                   }
                   accountListBean.accounts?.add(0, accountBean)
                   accountListBean.behaviorAccounts?.add(0, accountBean)
                   UserConfig.singleton?.allAccount = gson.toJson(accountListBean)
                   UserConfig.singleton?.clear()
                   LaunchConfig.startMainAc(this@CreateAccountActivity)
                   mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
                   this@CreateAccountActivity.finish()
               }

           })
        }
    }

}