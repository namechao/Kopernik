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


    private var balanaceOf: BigDecimal? = null
    private var fee: String? = null

    override fun layoutId() = R.layout.activity_transfer

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("UYT"+resources.getString(R.string.title_asset_transfer))
        eTUidAddress?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        eTTransferCounts?.maxLines = 2
        eTUidAddress?.addTextChangedListener(textWatcher)
        eTTransferCounts?.addTextChangedListener(textWatcher)
        etRemark?.addTextChangedListener(textWatcher)
        eTUidAddress?.hint = getString(R.string.please_input_transfer_count)
        etRemark!!.hint = getString(R.string.please_input_remark)

//        checkStatus()
        okBtn.setOnClickListener {
//            if (fee == null) return@setOnClickListener
//            if (balanaceOf!!.compareTo(BigDecimal("0")) == 0) {
//                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_no_asset_transfer))
//                return@setOnClickListener
//            }
//            if (chainType == UYT) {
//                if (balanaceOf?.compareTo(
//                        BigDecimal(eTUidAddress?.text.toString())
//                            .add(BigDecimal(fee))
//                    )!! < 0
//                ) {
//                    ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
//                    return@setOnClickListener
//                }
//            } else {
//                if (balanaceOf?.compareTo(BigDecimal(eTUidAddress?.text.toString()))!! < 0) {
//                    ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_max_vlue))
//                    return@setOnClickListener
//                }
//            }
//            if (eTTransferCounts!!.text.toString() == UserConfig.singleton?.accountBean
//                    ?.loginAcountHash
//            ) {
//                ToastUtils.showShort(getActivity(), getString(R.string.can_not_transfer_to_self))
//                return@setOnClickListener
//            }
//            if (BigDecimal(eTUidAddress?.text.toString()) <= BigDecimal("0")
//            ) {
//                ToastUtils.showShort(getActivity(), getString(R.string.transfer_value_error))
//                return@setOnClickListener
//            }
//            if (etRemark!!.text.toString().length > 64) {
//                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
//                return@setOnClickListener
//            }
            showDialog()
        }

    }

    //显示输入密码弹窗
    private fun showDialog() {
        var extractDialog = TransferDialog.newInstance(1)
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
                okBtnTransfer()
                extractDialog!!.dismiss()
            } else {
                ErrorCode.showErrorMsg(getActivity(), it.status)
            }
        })
    }



    override fun initData() {

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

    private fun okBtnTransfer() {


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
            okBtn.isEnabled =
                eTUidAddress!!.text.toString().isNotEmpty() && eTTransferCounts!!.text.toString().isNotEmpty()
        }
    }

}
