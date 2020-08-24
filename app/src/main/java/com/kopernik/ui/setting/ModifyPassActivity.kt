package com.kopernik.ui.setting

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.my.ViewModel.InviteFriendsViewModel
import kotlinx.android.synthetic.main.activity_modify_pass.*


class ModifyPassActivity : NewBaseActivity<InviteFriendsViewModel, ViewDataBinding>() {
    private var oldPwdEt: EditText? = null
    private var newPwd1: EditText? = null
    private var newPwd2: EditText? = null

    override fun layoutId() = R.layout.activity_modify_pass

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_modify_pass))
        oldPwdLL?.findViewById<TextView>(R.id.edit_require_tv)?.visibility = View.GONE
        newPwdLL1?.findViewById<TextView>(R.id.edit_require_tv)?.visibility = View.GONE
        newPwdLL2?.findViewById<TextView>(R.id.edit_require_tv)?.visibility = View.GONE

        oldPwdEt = oldPwdLL.findViewById(R.id.input_et)
        newPwd1 = newPwdLL1.findViewById(R.id.input_et)
        newPwd2 = newPwdLL2.findViewById(R.id.input_et)

        oldPwdEt!!.addTextChangedListener(textWatcher)
        newPwd1!!.addTextChangedListener(textWatcher)
        newPwd2!!.addTextChangedListener(textWatcher)

        oldPwdEt!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        newPwd1!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        newPwd2!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT


        oldPwdEt!!.hint = getString(R.string.please_input_old_pass)
        newPwd1!!.hint = getString(R.string.please_input_new_pass)
        newPwd2!!.hint = getString(R.string.please_input_confirm_pass)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        okBtn.setOnClickListener { v: View? ->
            if (newPwd1!!.text.toString().length < 8) {
                ToastUtils.showShort(getActivity(), getString(R.string.password_too_short))
                return@setOnClickListener
            }
            if (newPwd1!!.text.toString() != newPwd2!!.text.toString()) {
                ToastUtils.showShort(getActivity(), getString(R.string.twice_password_error))
                return@setOnClickListener
            }
            if (newPwd1!!.text.toString().length < 8) {
                ToastUtils.showShort(getActivity(), getString(R.string.password_too_short))
                return@setOnClickListener
            }
            if (!StringUtils.isContainAll(newPwd1!!.text.toString())) {
                ToastUtils.showShort(getActivity(), getString(R.string.password_input_error))
                return@setOnClickListener
            }
            modify()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun modify() {
        var oldPassword = oldPwdEt?.text.toString().trim()
        var newPassword = newPwd1?.text.toString().trim()
        var map = mapOf(
            "acountPwd" to oldPassword,
            "newPwd" to newPassword
        )
//        viewModel.updatePassword(map).observe(this, Observer {
//            if (it.status == 200) {
//                ToastUtils.showShort(getActivity(), getString(R.string.tip_change_success))
//                //删除指纹解锁
////                if (UserConfig?.singleton?.isUseFingerprint!!) {
////                    FingerprintHelper.getInstance().init(getActivity())
////                    FingerprintHelper.getInstance().closeAuthenticate()
////                    UserConfig?.singleton?.isUseFingerprint = false
////                }
//                finish()
//            } else {
//                ToastUtils.showShort(this, it.errorMsg)
//            }
//        })

    }
    var textWatcher: TextWatcher = object : TextWatcher {
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
            val str = newPwd1!!.text.toString()
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
            okBtn.isEnabled = oldPwdEt!!.text.toString().isNotEmpty() &&
                    newPwd1!!.text.toString().isNotEmpty() && newPwd2!!.text.toString().isNotEmpty()
        }
    }

}