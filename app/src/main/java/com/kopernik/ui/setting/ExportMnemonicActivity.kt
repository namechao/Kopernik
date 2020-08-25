package com.kopernik.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_export_mnemonic.*

class ExportMnemonicActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    private var pwdEt: EditText? = null

    override fun layoutId()=R.layout.activity_export_mnemonic

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_modify_export_words))
        pwdLL.findViewById<TextView>(R.id.edit_require_tv)
            .setVisibility(View.GONE)
        pwdEt = pwdLL.findViewById(R.id.input_et)
        pwdEt?.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT)
        pwdEt?.addTextChangedListener(textWatcher)
        pwdEt?.setHint(getString(R.string.please_input_pass))
        okBtn.setOnClickListener {}
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
            okBtn.isEnabled = !pwdEt!!.text.toString().isEmpty()
        }
    }

//    private fun exportMnemonic() {
//        viewModel.run {
//            exportMnemonic(pwdEt?.text.toString().trim()).observe(this@ExportMnemonicActivity,
//                Observer {
//                    if (it.status==200) {
//                        if (it.data.toString().equals("true")) {
////                            UserConfig.singleton?.mnemonic?.let {
////                                LaunchConfig.startExportMnemonicSuccessAc(
////                                    this@ExportMnemonicActivity
////                                )
////                            }
//
//                        finish()
//                        } else {
//                            ToastUtils.showShort(getActivity(), getString(R.string.error_406))
//                        }
//                    }else{
//                        ErrorCode.showErrorMsg(getActivity(), it.status)
//                    }
//                })
//        }
//    }
    override fun initData() {

    }
}