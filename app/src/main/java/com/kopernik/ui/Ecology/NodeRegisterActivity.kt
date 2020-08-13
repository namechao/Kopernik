package com.kopernik.ui.Ecology

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
import com.kopernik.app.dialog.SignatureDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.common.SignatureType
import com.kopernik.ui.Ecology.entity.CheckRegisterInfo
import com.kopernik.ui.Ecology.viewModel.NodeRegisterViewModel
import com.kopernik.ui.asset.entity.RegisterNodeSignatureBean
import kotlinx.android.synthetic.main.activity_node_register.*

class NodeRegisterActivity : NewBaseActivity<NodeRegisterViewModel, ViewDataBinding>() {
    private var nodeNameEt: EditText? = null
    private var nodeReferrerEt: EditText? = null
    private var nodeWebsiteEt: EditText? = null
    private var registerInfo: CheckRegisterInfo? = null

    @Override

    override fun layoutId() = R.layout.activity_node_register

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.node_register_title))
        registerInfo = intent.getParcelableExtra("info")
        nodeNameEt = nodeName.findViewById(R.id.input_et)
        nodeReferrerEt = nodeAddress.findViewById(R.id.input_et)
        nodeWebsiteEt = nodeWebsite.findViewById(R.id.input_et)

        nodeName.findViewById<TextView>(R.id.edit_require_tv).visibility = View.VISIBLE
        nodeAddress.findViewById<TextView>(R.id.edit_require_tv).visibility = View.VISIBLE

        nodeNameEt?.setHint(getString(R.string.node_register_name_hint))
        nodeReferrerEt!!.hint = getString(R.string.node_register_referrer_hint)
        nodeWebsiteEt!!.hint = getString(R.string.node_register_website_hint)
        nodeReferrerEt!!.maxLines = 3
        nodeWebsiteEt?.findViewById<TextView>(R.id.edit_require_tv)?.visibility = View.GONE

        nodeNameEt?.addTextChangedListener(nodeNameWatcher)
        nodeReferrerEt!!.addTextChangedListener(nodeAddressWatcher)
        nextBtn.setOnClickListener { checkNodeName() }
    }

    override fun initData() {

    }


    var nodeNameWatcher: TextWatcher = object : TextWatcher {
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
            restNextBtn()
            if (s.toString().isEmpty()) return
            //            checkNodeName();
        }
    }

    var nodeAddressWatcher: TextWatcher = object : TextWatcher {
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
            restNextBtn()
            if (s.toString().isEmpty()) return
            //这块注释了
            //            checkNodeAddress();
        }

        override fun afterTextChanged(s: Editable) {}
    }

    var nodeWebsiteWatcher: TextWatcher = object : TextWatcher {
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
            restNextBtn()
        }
    }

    private fun restNextBtn() {
        nextBtn.isEnabled = nodeNameEt!!.text.toString().isNotEmpty() && nodeReferrerEt!!.text.toString()
            .isNotEmpty()
    }

    private fun checkNodeName() {

        viewModel.run {
            checkNodeName(nodeNameEt!!.text.toString().trim()).observe(
                this@NodeRegisterActivity,
                Observer {
                    if (it.status == 200) {
                        val bean = RegisterNodeSignatureBean()
                        bean.desc1 = nodeNameEt!!.text.toString()
                        bean.desc2 = registerInfo?.low.toString() + " UYT"
                        bean.balance = registerInfo?.balance
                        bean.nodeName = nodeNameEt!!.text.toString()
                        bean.nodeAddress = nodeReferrerEt!!.text.toString()
                        bean.website = nodeWebsiteEt!!.text.toString()
                        val dialog: SignatureDialog =
                            SignatureDialog.newInstance(SignatureType.NODE_REGISTER, bean)
                        dialog.show(supportFragmentManager, "signature")
                    } else {
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
        }
    }
    //这块注释了
    private fun checkNodeAddress() {
//        OkGo.< BaseBeanNoData > post < BaseBeanNoData ? > (ServiceUrl.checkNodeAddress.toString() + "nodeHash=" + nodeReferrerEt!!.text
//            .toString())
//            .tag(this)
//            .execute(object : DialogCallback<BaseBeanNoData?>(getActivity()) {
//                fun onSuccess(response: Response<BaseBeanNoData?>?) {}
//                fun onShowErrorMsg(code: Int) {
//                    ErrorMsg.showErrorMsg(getActivity(), code)
//                }
//            })
    }
}