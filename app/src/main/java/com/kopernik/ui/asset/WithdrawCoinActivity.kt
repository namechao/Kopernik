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
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.VerifyCodeAlertDialog
import com.kopernik.app.dialog.WithdrawlDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.asset.entity.WithdrawCoinBean
import com.kopernik.ui.asset.viewModel.WithdrawCoinDetailsViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.kopernik.ui.setting.AddContactActivity
import com.xuexiang.xqrcode.XQRCode
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_deposit_money.*
import kotlinx.android.synthetic.main.activity_withdraw_coin.*
import kotlinx.android.synthetic.main.activity_withdraw_coin.chainName
import kotlinx.android.synthetic.main.activity_withdraw_coin.chainType
import kotlinx.android.synthetic.main.activity_withdraw_coin.csChoseCoin
import kotlinx.android.synthetic.main.activity_withdraw_coin.etRemark
import kotlinx.android.synthetic.main.activity_withdraw_coin.okBtn
import kotlinx.android.synthetic.main.activity_withdraw_coin.tvCoinType1
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.math.BigDecimal

class WithdrawCoinActivity : NewBaseActivity<WithdrawCoinDetailsViewModel,ViewDataBinding>(),
    EasyPermissions.PermissionCallbacks {
companion object{
    private const val REQUEST_CODE = 1
    private const val REQUEST_CODE_QRCODE_PERMISSIONS = 2
}
    private val perms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var allConfigEntity:AllConfigEntity?=null
    private var balanace="0"
    private var fee: String? = null
    private var coinType="USDT"
    private var coinName="USDT"

    private var rate=""
    override fun layoutId()=R.layout.activity_withdraw_coin
    override fun initView(savedInstanceState: Bundle?) {
        setTitleAndRightResButton( resources.getString(R.string.title_asset_withdrawl),R.mipmap.ic_deposit_history,object :OnRightClickItem{
            override fun onClick() {
                var intent=Intent(this@WithdrawCoinActivity,DepositCoinHistoryActivity::class.java)
                intent.putExtra("coinType",coinType)
                intent.putExtra("historyType","Cash")
                startActivity(intent)
            }

        })
        csChoseCoin.setOnClickListener {
           var intent= Intent(this,ChoseCoinTypeActivity::class.java)
            intent.putExtra("choseType","2")
            startActivityForResult(intent,
                DepositMoneyActivity.STARTCODE
            )
        }

        intent.getSerializableExtra("allConfigEntity")?.let {
            allConfigEntity=it as AllConfigEntity
        }
        ivScan.setOnClickListener { v: View? ->
            if (!EasyPermissions.hasPermissions(this, *perms)) {
                requestCodeQRCodePermissions()
                return@setOnClickListener
            }
            XQRCode.startScan(this, REQUEST_CODE)
        }
        //获取币种余额
        balanace=allConfigEntity?.usdt.toString()
        availableUse.text = resources.getString(R.string.title_asset_use)+BigDecimalUtils.roundDOWN(balanace,8)+" "+coinName
        tvWithDrawlType.text=coinName
        //全部按钮
        tvWithDrawlAll.setOnClickListener {
            eTWithdrawlCoinCounts.setText(balanace)
        }
        //手续费
        if (allConfigEntity?.rateList!=null) {
            for (i in allConfigEntity?.rateList!!){
                if (i.type.contains("Cash")) rate =BigDecimalUtils.roundDOWN(i.rate,8)
            }
        }
        tvHandlerFee.text=rate
        eTWithdrawlCoinCounts?.maxLines = 2
        eTWithdrwalAddress?.addTextChangedListener(textWatcher)
        eTWithdrawlCoinCounts?.addTextChangedListener(textWatcher1)
    }

    override fun initData() {
        getConfigAsset()
        okBtn.setOnClickListener {
            if (etRemark!!.text.toString().length > 64) {
                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
                return@setOnClickListener
            }
            //校验提现金额
            if (BigDecimal(balanace).compareTo(BigDecimal("0")) == 0) {
                ToastUtils.showShort(getActivity(), getString(R.string.tip_alert_no_asset_withdrawl))
                return@setOnClickListener
            }
            //校验提现金额
            if (BigDecimal(eTWithdrawlCoinCounts.text.toString().trim()) > BigDecimal(balanace)) {
                ToastUtils.showShort(getActivity(), getString(R.string.uyt_witdrawl_error))
                return@setOnClickListener
            }
            //判断是否设置交易密码
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                        LaunchConfig.startTradePasswordActivity(this, 1,1)
                        return@setOnClickListener
                    }
                }else{
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                        LaunchConfig.startTradePasswordActivity(this, 2,1)
                        return@setOnClickListener
                    }
                }
            }
            showDialog()
        }


    }
    private  fun  getConfigAsset(){
        viewModel.run {
            getAssetConfig().observe(this@WithdrawCoinActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                }
            })
        }
    }
    //显示提币弹窗
    private fun showDialog() {


        var wdBean = WithdrawCoinBean()
        wdBean.mineFee =rate
        wdBean.addressHash = eTWithdrwalAddress!!.text.toString().trim()
        wdBean.withdrawNumber = eTWithdrawlCoinCounts.text.toString().trim()
        var dialog = WithdrawlDialog.newInstance(wdBean,"")
        dialog!!.setOnRequestListener(object : WithdrawlDialog.RequestListener {
            override fun onRequest(params: String,type:Int) {
                submitWithDrawlCoin(params)
            }
        })
        dialog!!.show(supportFragmentManager, "withdrawRecommed")
    }


    private fun submitWithDrawlCoin(pwd:String) {
        var map = mapOf(
            "amount" to eTWithdrawlCoinCounts.text.toString().trim(),
            "addressHash" to eTWithdrwalAddress.text.toString().trim(),
            "rate" to rate,
            "type" to coinType,
            "pwd" to MD5Utils.md5(
                MD5Utils.md5(pwd)),
            "remark" to etRemark.text.toString().trim()
        )
        viewModel.run {
            submitWithDrawlCoin(map).observe(this@WithdrawCoinActivity, Observer {
                if (it.status == 200) {
                    ToastUtils.showShort(
                        this@WithdrawCoinActivity,
                        getString(R.string.tip_asset_withdraw_success)
                    );
                    this@WithdrawCoinActivity.finish()
                } else{
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }

            })
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
            okBtn.isEnabled =
                eTWithdrwalAddress!!.text.toString().isNotEmpty() && eTWithdrawlCoinCounts!!.text.toString().isNotEmpty()
        }
    }
    private var textWatcher1: TextWatcher = object : TextWatcher {
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
                eTWithdrwalAddress!!.text.toString().isNotEmpty() && eTWithdrawlCoinCounts!!.text.toString().isNotEmpty()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== DepositMoneyActivity.STARTCODE &&resultCode== DepositMoneyActivity.RESULTCODE){
            data?.getStringExtra("CoinType")?.let {
                coinName=it
                update()
            }

        }else if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //处理二维码扫描结果
                handleScanResult(data)
        }
    }

    fun update(){
        if (coinName=="USDT"){
            coinType="USDT"
            balanace= allConfigEntity?.usdt.toString()
            remarkTip.visibility=View.GONE
            etRemark.visibility=View.GONE
            chainName.visibility=View.VISIBLE
            chainType.visibility=View.VISIBLE
        }else if(coinName=="UYT_TEST"){
            coinType="UYT"
            balanace= allConfigEntity?.uyt.toString()
            remarkTip.visibility=View.VISIBLE
            etRemark.visibility=View.VISIBLE
            chainName.visibility=View.GONE
            chainType.visibility=View.GONE
        }else if(coinName=="UYT"){
            coinType="UYTPRO"
            balanace= allConfigEntity?.uytPro.toString()
            remarkTip.visibility=View.VISIBLE
            etRemark.visibility=View.VISIBLE
            chainName.visibility=View.GONE
            chainType.visibility=View.GONE
        }else if(coinName=="UTC"){
            coinType="UTC"
            balanace= allConfigEntity?.utc.toString()
            remarkTip.visibility=View.VISIBLE
            etRemark.visibility=View.VISIBLE
            chainName.visibility=View.GONE
            chainType.visibility=View.GONE
        }
        availableUse.text = resources.getString(R.string.title_asset_use)+BigDecimalUtils.roundDOWN(balanace,8)+" "+coinName
        tvWithDrawlType.text=coinName
        tvCoinType1.text=coinName
        eTWithdrwalAddress.setText("")
        eTWithdrawlCoinCounts.setText("")
        etRemark.setText("")
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults, this
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        XQRCode.startScan(this, REQUEST_CODE)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.qrcode_permissions),
          REQUEST_CODE_QRCODE_PERMISSIONS,
            perms.toString()
        )
    }
}