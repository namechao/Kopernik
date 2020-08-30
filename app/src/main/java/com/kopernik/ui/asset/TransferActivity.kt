package com.kopernik.ui.asset

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.TransferDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.entity.TransferCoinBean
import com.kopernik.ui.asset.viewModel.TransferViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_transfer.*
import java.math.BigDecimal

class TransferActivity : NewBaseActivity<TransferViewModel, ViewDataBinding>() {


    private var balanaceOf: BigDecimal? = null
    private var allConfigEntity: AllConfigEntity?=null
    private var rate=""
    override fun layoutId() = R.layout.activity_transfer

    override fun initView(savedInstanceState: Bundle?) {
        intent.getSerializableExtra("allConfigEntity")?.let {
            allConfigEntity=it as AllConfigEntity
        }
        setTitle("UYT"+resources.getString(R.string.title_asset_transfer))
        getConfigAsset()
        eTUidAddress?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        eTTransferCounts?.maxLines = 2
        eTUidAddress?.addTextChangedListener(textWatcher)
        eTTransferCounts?.addTextChangedListener(textWatcher1)

        etRemark!!.hint = getString(R.string.please_input_remark)
        okBtn.setOnClickListener {
            if (etRemark!!.text.toString().length > 64) {
                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
                return@setOnClickListener
            }
            if(allConfigEntity!=null) {
                //校验提现金额
                if (BigDecimal(allConfigEntity?.uyt).compareTo(BigDecimal("0")) == 0) {
                    ToastUtils.showShort(
                        getActivity(),
                        getString(R.string.tip_alert_no_asset_transfer)
                    )
                    return@setOnClickListener
                }
                //校验提现金额
                if (BigDecimal(
                        eTTransferCounts.text.toString().trim()
                    ) > BigDecimal(allConfigEntity?.uyt)
                ) {
                    ToastUtils.showShort(getActivity(), getString(R.string.uyt_transfer_error))
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
                            LaunchConfig.startTradePasswordActivity(this, 1,1)
                            return@setOnClickListener
                        }
                    }
                }
                showDialog()
            }
        }

    }

    //显示输入密码弹窗
    private fun showDialog() {
        if (allConfigEntity?.rateList!=null) {
            for (i in allConfigEntity?.rateList!!){
                if (i.type.contains("ROLL_OUT")) rate = BigDecimalUtils.roundDOWN(i.rate,8)
            }
        }
        var bean = TransferCoinBean()
        bean.handlerFee =rate
        bean.receiveId = eTUidAddress!!.text.toString().trim()
        bean.transferNumber = eTTransferCounts.text.toString().trim()
        var extractDialog = TransferDialog.newInstance(bean)
        extractDialog!!.setOnRequestListener(object : TransferDialog.RequestListener {
            override fun onRequest( params: String) {
                submitWithDrawlCoin(params)
            }
        })
        extractDialog!!.show(supportFragmentManager, "withdrawRecommed")
    }



    private  fun  getConfigAsset(){
        viewModel.run {
            getAssetConfig().observe(this@TransferActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                }
            })
        }
    }
    override fun initData() {

    }
    private fun submitWithDrawlCoin(psw:String) {
        viewModel.run {
            var map= mapOf("amount" to eTTransferCounts.text.toString().trim(),"uidReceive" to eTUidAddress.text.toString().trim(),"rate" to rate ,"type" to "UYT" ,"pwd" to MD5Utils.md5(
                MD5Utils.md5(psw)),  "remark" to etRemark.text.toString().trim())
            transfer(map).observe(this@TransferActivity, Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@TransferActivity,resources.getString(R.string.tip_asset_transfer_success))
                }else{
                    ErrorCode.showErrorMsg(this@TransferActivity,it.status)
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
                eTUidAddress!!.text.toString().isNotEmpty() && eTTransferCounts!!.text.toString().isNotEmpty()
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
                eTUidAddress!!.text.toString().isNotEmpty() && eTTransferCounts!!.text.toString().isNotEmpty()
        }
    }
}
