package com.kopernik.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.my.ViewModel.ForgetTradePasswordViewModel
import kotlinx.android.synthetic.main.activity_modify_nick.*

class ModifyNickActivity : NewBaseActivity<ForgetTradePasswordViewModel, ViewDataBinding>() {
    private var nickEt: EditText? = null


    override fun layoutId() = R.layout.activity_modify_nick

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_modify_nick))
        nickLL?.findViewById<TextView>(R.id.edit_require_tv)
            ?.setVisibility(View.GONE)
        nickEt = nickLL.findViewById(R.id.input_et)
        nickEt?.addTextChangedListener(textWatcher)
        nickEt?.setHint(getString(R.string.input_new_nick))
        okBtn.setOnClickListener { v: View? ->
            KeyboardUtils.hideSoftKeyboard(nickEt)
            if (nickEt?.getText().toString().length > 12) {
                ToastUtils.showShort(getActivity(), getString(R.string.input_new_nick_error))
                return@setOnClickListener
            }
            modify()
        }
    }

    override fun initData() {

    }

    //请求网络
    private fun modify() {
        var nicklabel = nickEt?.text.toString().trim()
        var map = mapOf("acountLabel" to nicklabel)
//        viewModel.updateNick(map).observe(this, Observer {
//            if (it.status === 200) {
//                ToastUtils.showShort(getActivity(), getString(R.string.tip_change_success))
////                val accountBean: AccountBean? = UserConfig.singleton?.getAccount()
////                accountBean?.loginlabel = nickEt!!.text.toString()
////                UserConfig.singleton?.accountString = Gson().toJson(accountBean)
////                finish()
//            } else {
//                ToastUtils.showShort(getActivity(), it.errorMsg)
//            }
//        })
    }

    private var textWatcher: TextWatcher = object : TextWatcher {
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
            okBtn.isEnabled = nickEt!!.text.toString().isNotEmpty()
        }
    }
}


