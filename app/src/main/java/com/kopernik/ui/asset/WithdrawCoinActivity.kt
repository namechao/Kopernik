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
import com.kopernik.app.dialog.WithdrawlDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.entity.WithdrawCoinBean
import com.kopernik.ui.asset.viewModel.WithdrawCoinDetailsViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.xuexiang.xqrcode.XQRCode
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_withdraw_coin.*
import kotlinx.android.synthetic.main.activity_withdraw_coin.etRemark
import kotlinx.android.synthetic.main.activity_withdraw_coin.okBtn
import java.math.BigDecimal

class WithdrawCoinActivity : NewBaseActivity<WithdrawCoinDetailsViewModel,ViewDataBinding>() {
companion object{
    private const val REQUEST_CODE = 1
    private const val REQUEST_CODE_QRCODE_PERMISSIONS = 2
}
    private var allConfigEntity:AllConfigEntity?=null
    private var balanace=""
    private var fee: String? = null
    private var type:String=""

    private var rate=""
    override fun layoutId()=R.layout.activity_withdraw_coin
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_asset_withdrawl))
        intent.getSerializableExtra("allConfigEntity")?.let {
            allConfigEntity=it as AllConfigEntity
        }
        intent.getStringExtra("type")?.let {
            type=it
        }
        if (type=="UYT"){
            withdrawlAddressTip.text=getString(R.string.desposit_test_address)
            balanace= allConfigEntity?.uyt.toString()
        }else if(type=="UYTPRO"){
            withdrawlAddressTip.text=getString(R.string.desposit_address)
            balanace= allConfigEntity?.uytPro.toString()
        }
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

        if (allConfigEntity?.rateList!=null) {
            for (i in allConfigEntity?.rateList!!){
                if (i.type.contains("Cash")) rate =BigDecimalUtils.roundDOWN(i.rate,8)
            }
        }
        var wdBean = WithdrawCoinBean()
        wdBean.mineFee =rate
        wdBean.addressHash = eTWithdrwalAddress!!.text.toString().trim()
        wdBean.withdrawNumber = eTWithdrawlCoinCounts.text.toString().trim()
        var dialog = WithdrawlDialog.newInstance(wdBean,type)
        dialog!!.setOnRequestListener(object : WithdrawlDialog.RequestListener {
            override fun onRequest(params: String) {
                submitWithDrawlCoin(params)
            }
        })
        dialog!!.show(supportFragmentManager, "withdrawRecommed")
    }



    private fun submitWithDrawlCoin(psw:String) {
        var map = mapOf(
            "amount" to eTWithdrawlCoinCounts.text.toString().trim(),
            "addressHash" to eTWithdrwalAddress.text.toString().trim(),
            "rate" to rate,
            "type" to type,
            "pwd" to MD5Utils.md5(MD5Utils.md5(psw)),
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

}