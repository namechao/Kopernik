package com.kopernik.ui.setting

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.ui.my.ViewModel.RealNameViewModel
import kotlinx.android.synthetic.main.activity_verify_pw.*

class VerifyPwActivity : NewBaseActivity<RealNameViewModel,ViewDataBinding>() {
    private var editText: EditText? = null

    override fun layoutId()=R.layout.activity_verify_pw

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.use_fingerprint))
        editText = pwLL.findViewById(R.id.input_et)
        pwLL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE

        editText?.hint = getString(R.string.input_pw_verify)
        editText?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        editText?.addTextChangedListener(object : TextWatcher {
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
                okBtn.isEnabled = editText?.text.toString().isNotEmpty()
            }
        })
        okBtn.setOnClickListener { checkPw() }
    }
    private fun checkPw() {
//        KeyboardUtils.hideSoftKeyboard(editText)
//        viewModel.verifyPsw(editText!!.text.toString().trim()).observe(this, Observer {
//
//            if (it.status==200) {
//                        val intent = Intent()
//                        intent.putExtra("pw", editText!!.text.toString())
//                        setResult(RESULT_OK, intent)
//                        finish()
//                    } else {
//                  ErrorCode.showErrorMsg(getActivity(), it.status)
//                    }
//        })
    }
    override fun initData() {

    }
}