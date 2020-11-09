package com.kopernik.ui.asset

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.TransferDialog
import com.kopernik.app.dialog.VerifyCodeAlertDialog
import com.kopernik.app.dialog.WithdrawlDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.entity.TransferCoinBean
import com.kopernik.ui.asset.viewModel.TransferViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_transfer.*
import kotlinx.android.synthetic.main.activity_transfer.availableUse
import kotlinx.android.synthetic.main.activity_transfer.csChoseCoin
import kotlinx.android.synthetic.main.activity_transfer.etRemark
import kotlinx.android.synthetic.main.activity_transfer.okBtn
import kotlinx.android.synthetic.main.activity_transfer.tvCoinType1
import kotlinx.android.synthetic.main.activity_transfer.tvHandlerFee
import kotlinx.android.synthetic.main.activity_transfer.tvWithDrawlAll
import kotlinx.android.synthetic.main.activity_transfer.tvWithDrawlType

import java.math.BigDecimal

class TransferActivity : NewBaseActivity<TransferViewModel, ViewDataBinding>() {


    private var balanace=""
    private var allConfigEntity: AllConfigEntity?=null
    private var rate=""
    private var coinType="USDT"
    private var coinName="USDT"
    override fun layoutId() = R.layout.activity_transfer

    override fun initView(savedInstanceState: Bundle?) {
        intent.getSerializableExtra("allConfigEntity")?.let {
            allConfigEntity=it as AllConfigEntity
        }

        setTitleAndRightResButton( resources.getString(R.string.title_asset_transfer),R.mipmap.ic_deposit_history,object :OnRightClickItem{
            override fun onClick() {
                var intent=Intent(this@TransferActivity,TransferHistoryActivity::class.java)
                intent.putExtra("coinType",coinType)
                intent.putExtra("historyType","Recharge")
                startActivity(intent)
            }

        })
        //选择币种
        csChoseCoin.setOnClickListener {
            var intent= Intent(this,ChoseCoinTypeActivity::class.java)
            intent.putExtra("choseType","3")
            startActivityForResult(intent,
                DepositMoneyActivity.STARTCODE
            )
        }

        balanace= allConfigEntity?.usdt.toString()
        availableUse.text = resources.getString(R.string.title_asset_use)+BigDecimalUtils.roundDOWN(balanace,8)+" "+coinName
        //全部按钮
        tvWithDrawlAll.setOnClickListener {
            eTTransferCounts.setText(balanace)
        }
        tvWithDrawlType.text=coinName
        eTUidAddress?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        eTTransferCounts?.maxLines = 2
        eTUidAddress?.addTextChangedListener(textWatcher)
        eTTransferCounts?.addTextChangedListener(textWatcher1)
        //手续费
        if (allConfigEntity?.rateList!=null) {
            for (i in allConfigEntity?.rateList!!){
                if (i.type.contains("ROLL_OUT")) rate =BigDecimalUtils.roundDOWN(i.rate,8)
            }
        }
        tvHandlerFee.text=rate
        etRemark!!.hint = getString(R.string.please_input_remark)
        okBtn.setOnClickListener {
            if (etRemark!!.text.toString().length > 64) {
                ToastUtils.showShort(getActivity(), getString(R.string.remark_too_long))
                return@setOnClickListener
            }
            if(allConfigEntity!=null) {
                //校验提现金额
                if (BigDecimal(balanace).compareTo(BigDecimal("0")) == 0) {
                    ToastUtils.showShort(
                        getActivity(),
                        getString(R.string.tip_alert_no_asset_transfer)
                    )
                    return@setOnClickListener
                }
                //校验提现金额
                if (BigDecimal(
                        eTTransferCounts.text.toString().trim()
                    ) > BigDecimal(balanace)
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
                            LaunchConfig.startTradePasswordActivity(this, 2,1)
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
        var extractDialog = TransferDialog.newInstance(bean,"")
        extractDialog!!.setOnRequestListener(object : TransferDialog.RequestListener {
            override fun onRequest( params: String,type:Int) {
                submitWithDrawlCoin(params)
            }
        })
        extractDialog!!.show(supportFragmentManager, "withdrawRecommed")
    }

    override fun initData() {

    }
    private fun submitWithDrawlCoin(pwd:String) {
        viewModel.run {
            var map= mapOf("amount" to eTTransferCounts.text.toString().trim(),
                "uidReceive" to eTUidAddress.text.toString().trim(),
                "rate" to rate ,
                "type" to coinType,
                "pwd" to MD5Utils.md5(
                    MD5Utils.md5(pwd)))
            transfer(map).observe(this@TransferActivity, Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@TransferActivity,resources.getString(R.string.tip_asset_transfer_success))
                    this@TransferActivity.finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== DepositMoneyActivity.STARTCODE &&resultCode== DepositMoneyActivity.RESULTCODE){
            data?.getStringExtra("CoinType")?.let {
                coinName=it
                update()
            }

        }
    }
    fun update(){
        if(coinName=="USDT"){
            coinType="USDT"
            balanace= allConfigEntity?.usdt.toString()
        }else if(coinName=="UYT_TEST"){
            coinType="UYT"
            balanace= allConfigEntity?.uyt.toString()
        }else if(coinName=="UYT"){
            coinType="UYTPRO"
            balanace= allConfigEntity?.uytPro.toString()
        }else if(coinName=="UTK"){
            coinType="UTK"
            balanace= allConfigEntity?.utk.toString()
        }else if(coinName=="UTDM"){
            coinType="UTDM"
            balanace= allConfigEntity?.utdm.toString()
        }
        availableUse.text = resources.getString(R.string.title_asset_use)+BigDecimalUtils.roundDOWN(balanace,8)+" "+coinName
        tvWithDrawlType.text=coinName
        tvCoinType1.text=coinName
        eTUidAddress.setText("")
        eTTransferCounts.setText("")
        etRemark.setText("")
    }
}
