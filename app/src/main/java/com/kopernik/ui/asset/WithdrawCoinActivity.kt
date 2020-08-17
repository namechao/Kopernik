package com.kopernik.ui.asset

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.WithdrawlDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.entity.WithdrawCoinBean
import com.kopernik.ui.asset.viewModel.WithdrawCoinDetailsViewModel
import com.xuexiang.xqrcode.XQRCode
import kotlinx.android.synthetic.main.activity_withdraw_coin.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.math.BigDecimal

class WithdrawCoinActivity : NewBaseActivity<WithdrawCoinDetailsViewModel,ViewDataBinding>() {
companion object{
    private const val REQUEST_CODE = 1
    private const val REQUEST_CODE_QRCODE_PERMISSIONS = 2
}

    private var balanaceOf: BigDecimal? = null
    private var fee: String? = null
    private var iconType:String=""
    private var availableAmount:String=""

    override fun layoutId()=R.layout.activity_withdraw_coin
    override fun initView(savedInstanceState: Bundle?) {
        availableAmount = intent.getStringExtra("availableAmount")
        setTitle(getString(R.string.title_asset_withdrawl))
    }

    override fun initData() {
        eTWithdrwalAddress!!.addTextChangedListener(textWatcher)
        okBtn.setOnClickListener {
//            if (remark!!.text.toString().length > 64) {
//                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
//                return@setOnClickListener
//            }
            showDialog()
            //校验提现金额
            if (balanaceOf?.compareTo(BigDecimal("0")) == 0) {
                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_no_asset_transfer))
                return@setOnClickListener
            }
            val addr = eTWithdrwalAddress!!.text.toString()
            if (!addr.matches(Regex("^(1|3)[a-zA-Z\\d]{24,33}$"))) {
                ToastUtils.showShort(getActivity(), getString(R.string.btc_addr_error))
                return@setOnClickListener
            }
        }


    }

    //显示提币弹窗
    private fun showDialog() {
        var wdBean = WithdrawCoinBean()
        wdBean.mineFee = fee
        wdBean.addressHash = eTWithdrwalAddress!!.text.toString()
        wdBean.withdrawNumber = availableAmount
        wdBean.withdrawNumberUnit = ""
        wdBean.mineFeeUnit = ""
        var dialog = WithdrawlDialog.newInstance(1)
        dialog!!.setOnRequestListener(object : WithdrawlDialog.RequestListener {
            override fun onRequest(type: Int, params: String) {
                checkPsw(params, dialog)
            }
        })
        dialog!!.show(supportFragmentManager, "withdrawRecommed")
    }



    //检查密码是否正确
    fun checkPsw(params: String, extractDialog: WithdrawlDialog) {
        viewModel.verifyPsw(params).observe(this, Observer {
            if (it.status == 200) {
                submitWithDrawlCoin()
                extractDialog!!.dismiss()
            } else {
                ErrorCode.showErrorMsg(getActivity(), it.status)
            }
        })
    }

    private fun submitWithDrawlCoin() {
        var map = mapOf(
            "id" to "",
            "address" to eTWithdrwalAddress.text.toString().trim(),
            "memo" to etRemark.text.toString().trim()
        )
        viewModel.run {
            submitWithDrawlCoin(map).observe(this@WithdrawCoinActivity, Observer {
                if (it.status == 200) {
                    ToastUtils.showShort(
                        this@WithdrawCoinActivity,
                        getString(R.string.tip_node_register_success)
                    );
                } else {
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }

            })
        }
    }

    /**
     * 处理二维码扫描结果
     * @param data
     */
    private fun handleScanResult(data: Intent?) {
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    val result = bundle.getString(XQRCode.RESULT_DATA)
                    eTWithdrwalAddress!!.setText(result)
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    ToastUtils.showShort(getActivity(), getString(R.string.decode_qrcode_error))
                }
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
            okBtn.isEnabled = eTWithdrwalAddress!!.text.toString().isNotEmpty()
        }
    }




}