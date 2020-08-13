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
import com.kopernik.app.dialog.WithdrawCoinDialog
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

class WithdrawCoinActivity : NewBaseActivity<WithdrawCoinDetailsViewModel,ViewDataBinding>(), PermissionCallbacks {
companion object{
    private const val REQUEST_CODE = 1
    private const val REQUEST_CODE_QRCODE_PERMISSIONS = 2
}
    private var type = 1
    private var id = -1
    private var balanaceOf: BigDecimal? = null
    private var fee: String? = null
    private var iconType:String=""
    private var availableAmount:String=""
    private val perms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun layoutId()=R.layout.activity_withdraw_coin
    override fun initView(savedInstanceState: Bundle?) {
        type = intent.getIntExtra("type", -1)
        id = intent.getIntExtra("id", -1)
        availableAmount = intent.getStringExtra("availableAmount")
        if (type == -1) finish()
        setTitle(getString(R.string.title_asset_withdraw))
    }

    override fun initData() {
        withdrawlAddress!!.addTextChangedListener(textWatcher)
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
            val addr = withdrawlAddress!!.text.toString()
            if (!addr.matches(Regex("^(1|3)[a-zA-Z\\d]{24,33}$"))) {
                ToastUtils.showShort(getActivity(), getString(R.string.btc_addr_error))
                return@setOnClickListener
            }
        }
        ivScan!!.setOnClickListener { v: View? ->
            if (!EasyPermissions.hasPermissions(this, *perms)) {
                requestCodeQRCodePermissions()
                return@setOnClickListener
            }
            XQRCode.startScan(this, REQUEST_CODE)
        }

    }

    //显示提币弹窗
    private fun showDialog() {
        var wdBean = WithdrawCoinBean()
        wdBean.mineFee = fee
        wdBean.addressHash = withdrawlAddress!!.text.toString()
        wdBean.withdrawNumber = availableAmount
        wdBean.withdrawNumberUnit = ""
        wdBean.mineFeeUnit = ""
        var extractDialog = WithdrawCoinDialog.newInstance(1, wdBean)
        extractDialog!!.setOnRequestListener(object : WithdrawCoinDialog.RequestListener {
            override fun onRequest(type: Int, params: String) {
                checkPsw(params, extractDialog)
            }
        })
        extractDialog!!.show(supportFragmentManager, "withdrawRecommed")
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            handleScanResult(data)
        }
    }

    //检查密码是否正确
    fun checkPsw(params: String, extractDialog: WithdrawCoinDialog) {
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
            "id" to id.toString(),
            "address" to withdrawlAddress.text.toString().trim(),
            "memo" to remark.text.toString().trim()
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
                    withdrawlAddress!!.setText(result)
                    ivScan!!.visibility = View.GONE
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
            okBtn.isEnabled = withdrawlAddress!!.text.toString().isNotEmpty()
            if (withdrawlAddress!!.text.toString().isNotEmpty()) {
                ivScan!!.visibility = View.GONE
            } else {
                ivScan!!.visibility = View.VISIBLE
            }
        }
    }



    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        XQRCode.startScan(this, REQUEST_CODE)
    }



    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.qrcode_permissions),
            REQUEST_CODE_QRCODE_PERMISSIONS,
            *perms
        )
    }
}