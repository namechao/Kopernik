package com.kopernik.ui.asset

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.TransferDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.ui.asset.entity.TransferDialogBean
import com.kopernik.ui.asset.viewModel.TransferViewModel
import kotlinx.android.synthetic.main.activity_transfer.*
import java.math.BigDecimal

class TransferActivity : NewBaseActivity<TransferViewModel, ViewDataBinding>() {
    companion object {
        val UYT = 1

        val BTC = 2

        val ETH = 3

        val USDT = 4

        val HT = 5
    }

    private var webview: WebView? = null
    private var contactIv: ImageView? = null
    private var valueEt: EditText? = null
    private var addressEt: EditText? = null
    private var remarkEt: android.widget.EditText? = null
    private var chainType = 1
    private var chainName = ""
    private var balanaceOf: BigDecimal? = null
    private var fee: String? = null

    override fun layoutId() = R.layout.activity_transfer

    override fun initView(savedInstanceState: Bundle?) {
        intent.getIntExtra("chainType", -1)?.let {
            chainType = it
        }
        intent.getStringExtra("chainName")?.let {
            chainName = it
        }
        if (chainType == -1) finish()
        setTitle(resources.getString(R.string.title_asset_transfer))
        valueEt = input1LL.findViewById(R.id.input_et)
        addressEt = input2LL.findViewById(R.id.input_et)
        remarkEt = input3LL.findViewById(R.id.input_et)
        input1LL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE
        input2LL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE
        input3LL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE
        valueEt?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        addressEt?.maxLines = 2
        valueEt?.addTextChangedListener(textWatcher)
        addressEt?.addTextChangedListener(textWatcher)
        remarkEt?.addTextChangedListener(textWatcher)
        valueEt?.hint = getString(R.string.please_input_transfer_count)
        remarkEt!!.hint = getString(R.string.please_input_remark)

        contactIv = input2LL.findViewById(R.id.input_clear_iv)
        contactIv?.setImageResource(R.mipmap.contact_icon)
        contactIv?.visibility = View.VISIBLE
        when (chainType) {
            UYT -> addressEt?.hint = getString(R.string.please_input_dns_transfer_address)
            BTC -> addressEt?.hint = getString(R.string.please_input_btc_transfer_address)
            ETH -> addressEt?.hint = getString(R.string.please_input_eth_transfer_address)
            USDT -> addressEt?.hint = getString(R.string.please_input_usdt_transfer_address)
            HT -> addressEt?.hint = getString(R.string.please_input_eth_transfer_address)
        }
//        checkStatus()
        confirm.setOnClickListener {
//            if (fee == null) return@setOnClickListener
//            if (balanaceOf!!.compareTo(BigDecimal("0")) == 0) {
//                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_no_asset_transfer))
//                return@setOnClickListener
//            }
//            if (chainType == UYT) {
//                if (balanaceOf?.compareTo(
//                        BigDecimal(valueEt?.text.toString())
//                            .add(BigDecimal(fee))
//                    )!! < 0
//                ) {
//                    ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
//                    return@setOnClickListener
//                }
//            } else {
//                if (balanaceOf?.compareTo(BigDecimal(valueEt?.text.toString()))!! < 0) {
//                    ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
//                    return@setOnClickListener
//                }
//            }
//            if (addressEt!!.text.toString() == UserConfig.singleton?.accountBean
//                    ?.loginAcountHash
//            ) {
//                ToastUtils.showShort(getActivity(), getString(R.string.can_not_transfer_to_self))
//                return@setOnClickListener
//            }
//            if (BigDecimal(valueEt?.text.toString()) <= BigDecimal("0")
//            ) {
//                ToastUtils.showShort(getActivity(), getString(R.string.transfer_value_error))
//                return@setOnClickListener
//            }
//            if (remarkEt!!.text.toString().length > 64) {
//                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
//                return@setOnClickListener
//            }
            showDialog()
        }
        contactIv?.setOnClickListener(View.OnClickListener { v: View? ->
            LaunchConfig.startContactForResult(
                this,
                1
            )
        })
    }

    //显示提取收益输入密码弹窗
    private fun showDialog() {
        var wdBean = TransferDialogBean()
        wdBean.addressHash = addressEt?.text.toString().trim()
        wdBean.transferNumber = resources.getString(R.string.title_gain_recomment) + chainName
        wdBean.free = "850 UYT"
        var extractDialog = TransferDialog.newInstance(1, wdBean)
        extractDialog!!.setOnRequestListener(object : TransferDialog.RequestListener {
            override fun onRequest(type: Int, params: String) {
                checkPsw(params, extractDialog)
            }
        })
        extractDialog!!.show(supportFragmentManager, "withdrawRecommed")
    }

    //检查密码是否正确
    fun checkPsw(params: String, extractDialog: TransferDialog) {
        viewModel.verifyPsw(params).observe(this, Observer {
            if (it.status == 200) {
                confirmTransfer()
                extractDialog!!.dismiss()
            } else {
                ErrorCode.showErrorMsg(getActivity(), it.status)
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) addressEt!!.setText(data.getStringExtra("contact"))
        }
    }

    override fun initData() {
        initWebview()
    }

    //    初始化加载js
    private fun initWebview() {
        if (webview == null) webview = WebView(this)
        webview?.let {
            it.settings.javaScriptEnabled = true
            it.settings.allowFileAccess = true
            it.settings.domStorageEnabled = true
            it.webViewClient = WebViewClient()
            it.webChromeClient = WebChromeClient()
            it.loadUrl("file:///android_asset/build/index.html")
        }
    }

//    private fun checkStatus() {
//        OkGo.< BaseBean < AssetOptBean > > get<BaseBean<AssetOptBean>>(ServiceUrl.checkTransferStatus)
//            .tag(this)
//            .execute(object : DialogCallback<BaseBean<AssetOptBean?>?>(getActivity()) {
//                fun onSuccess(response: Response<BaseBean<AssetOptBean?>?>) {
//                    if (response.body().status === 200) {
//                        val bean: AssetOptBean = response.body().data
//                        fee = bean.getConfig().getLow()
//                        when (type) {
//                            TransferConstant.UYT -> {
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getDnsSycount())
//                                titleSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUCapital().getDnsSycount(), 4
//                                    ).toString() + " UYT"
//                                )
//                            }
//                            TransferConstant.BTC -> {
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getBitcoinKycount())
//                                titleSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUCapital().getBitcoinKycount(), 8
//                                    ).toString() + " U-BTC"
//                                )
//                            }
//                            TransferConstant.ETH -> {
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getEthKycount())
//                                titleSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUCapital().getEthKycount(), 8
//                                    ).toString() + " U-ETH"
//                                )
//                            }
//                            TransferConstant.USDT -> {
//                                balanaceOf =
//                                    BigDecimal(bean.getUCapital().getUsdtKycount())
//                                titleSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUCapital().getUsdtKycount(), 6
//                                    ).toString() + " U-USDT"
//                                )
//                            }
//                            TransferConstant.HT -> {
//                                balanaceOf = BigDecimal(bean.getUCapital().getHtKycount())
//                                titleSpt.setRightString(
//                                    BigDecimalUtils.roundDOWN(
//                                        bean.getUCapital().getHtKycount(), 6
//                                    ).toString() + " U-HT"
//                                )
//                            }
//                        }
//                    } else {
//                        ToastUtils.showShort(getActivity(), response.body().errorMsg)
//                    }
//                }
//            })
//    }

    private fun confirmTransfer() {
        UserConfig.singleton?.mnemonic?.mnemonic?.let { list ->
            val sb = StringBuilder()
            for (i in list.indices) {
                sb.append(list[i]).append(" ")
            }
            webview?.evaluateJavascript(
                "transfer($chainType,'${addressEt?.text.toString().trim()}','${sb.toString().trim()}','1000000000000')"
            ) {

            }
        }

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
            confirm.isEnabled =
                valueEt!!.text.toString().isNotEmpty() && addressEt!!.text.toString().isNotEmpty()
        }
    }

}
