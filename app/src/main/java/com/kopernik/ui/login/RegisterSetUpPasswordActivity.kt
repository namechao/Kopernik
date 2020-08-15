package com.kopernik.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.bean.AccountListBean
import com.kopernik.ui.login.bean.Mnemonic
import com.kopernik.ui.login.viewmodel.ImportMnemonicConfirmViewModel
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_set_up_password.*
import java.util.*

class RegisterSetUpPasswordActivity :
    NewBaseActivity<ImportMnemonicConfirmViewModel, ViewDataBinding>() {
    companion object {
        var MNEMONIC_EXTRA = "mnemonicStr"
    }

    private val inputs: List<String> =
        ArrayList()
    private var newPwd1: EditText? = null
    private var newPwd2: EditText? = null
    private var mnemonicBean: Mnemonic? = null

    override fun layoutId() = R.layout.activity_set_up_password
    override fun initView(savedInstanceState: Bundle?) {
//        mnemonicBean = intent.getSerializableExtra(MNEMONIC_EXTRA) as Mnemonic
        setTitle(getString(R.string.title_set_password))
//        newPwd1 = password.findViewById(R.id.input_et)
//        newPwd2 = passwordAgain.findViewById(R.id.input_et)
//        newPwd1?.hint =
//            getString(R.string.please_input_pass)
//        newPwd2?.hint =
//            getString(R.string.please_input_pass_again)
//        newPwd1?.inputType =
//            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
//        newPwd2?.inputType =
//            InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
//        newPwd1?.addTextChangedListener(
//            passwordInputListener
//        )
//        newPwd2?.addTextChangedListener(
//            passwordInputListener
//        )
    }

    override fun initData() {

//        confirmBtn.setOnClickListener {
//            //点击按钮密码输入俩次是否一致
//            if (newPwd1!!.text.toString().trim() != newPwd2!!.text.toString().trim()) {
//                ToastUtils.showShort(getActivity(), getString(R.string.twice_password_error))
//                return@setOnClickListener
//            }
//            importMnemonics()
//        }
    }

//    //导入网络请求
//    private fun importMnemonics() {
//        if (mnemonicBean == null || mnemonicBean?.mnemonic?.size == 0) return
//        val sb = StringBuilder()
//        for (i in mnemonicBean?.mnemonic?.indices!!) {
//            sb.append(mnemonicBean?.mnemonic?.get(i)).append(" ")
//        }
//        val mnemonicStr = sb.toString().trim()
//        val mnemonicMd5 = MD5Utils.md5(mnemonicStr)
//        viewModel.importAccount(
//            newPwd1?.text.toString().trim(),
//            mnemonicBean?.address!!,
//            mnemonicMd5
//        ).observe(
//            this, androidx.lifecycle.Observer {
//                if (it.status == 200) {
//                    val accountList: AccountListBean
//                    //获取已存储的账户列表
//                    var accountListStr = UserConfig.singleton?.allAccount
//                    accountList =
//                        if (accountListStr.isNullOrEmpty()) AccountListBean() else Gson().fromJson(
//                            accountListStr,
//                            AccountListBean::class.java
//                        )
//                    //判断是否一登录账户
//                    for (account in accountList.accounts!!) {
//                        if (it.data.loginAcountHash == account.loginAcountHash) {
//                            ToastUtils.showShort(
//                                getActivity(),
//                                getString(R.string.account_already_exists)
//                            )
//                            return@Observer
//                        }
//                    }
//                    //如果没有登录过就记录登录信息
//                    UserConfig.singleton?.accountString = Gson().toJson(it.data)
//                    accountList.accounts?.add(0, it.data)
//                    accountList.behaviorAccounts?.add(0, it.data)
//                    UserConfig.singleton?.allAccount = Gson().toJson(accountList)
//                    UserConfig.singleton?.clear()
//                    //存储助记词
//                    mnemonicBean?.let {    UserConfig.singleton?.mnemonic=it }
//                    LaunchConfig.startMainAc(this@RegisterSetUpPasswordActivity)
//                    mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
//                    this@RegisterSetUpPasswordActivity.finish()
//                } else {
//                    it?.status?.let { it1 -> ErrorCode.showErrorMsg(this, it1) }
//                }
//            }
//
//        )
//    }


//    private var passwordInputListener: TextWatcher = object : TextWatcher {
//        override fun beforeTextChanged(
//            s: CharSequence,
//            start: Int,
//            count: Int,
//            after: Int
//        ) {
//        }
//
//        override fun onTextChanged(
//            s: CharSequence,
//            start: Int,
//            before: Int,
//            count: Int
//        ) {
//        }
//
//        override fun afterTextChanged(s: Editable) {
//            val str = newPwd1!!.text.toString()
//            if (StringUtils.isContainUpperCase(str)) {
//                pwdRules1.setTextColor(resources.getColor(R.color.text_gray))
//            } else {
//                pwdRules1.setTextColor(resources.getColor(R.color.red))
//            }
//            if (StringUtils.isContainNumber(str)) {
//                pwdRules2.setTextColor(resources.getColor(R.color.text_gray))
//            } else {
//                pwdRules2.setTextColor(resources.getColor(R.color.red))
//            }
//            if (StringUtils.isContainLowerCase(str)) {
//                pwdRules3.setTextColor(resources.getColor(R.color.text_gray))
//            } else {
//                pwdRules3.setTextColor(resources.getColor(R.color.red))
//            }
//            if (str.length > 7) {
//                pwdRules4.setTextColor(resources.getColor(R.color.text_gray))
//            } else {
//                pwdRules4.setTextColor(resources.getColor(R.color.red))
//            }
//            changeStatusBtn(str)
//        }
//    }
//
//    //根据提示提控制确认按钮可点击与否
//    private fun changeStatusBtn(str: String) {
//        confirmBtn.isEnabled =
//            StringUtils.isContainUpperCase(str) && StringUtils.isContainNumber(str) && StringUtils.isContainLowerCase(
//                str
//            ) && str.length > 7 && !newPwd2?.text.toString().trim().isNullOrEmpty()
//    }


}