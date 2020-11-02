package com.kopernik.ui.asset

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.QRCode.QRCodeEncoderModel
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.DeviceIDUtil
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.viewModel.DepositCoinViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_deposit_money.*

import kotlinx.android.synthetic.main.layout_copy.*

class DepositMoneyActivity : NewBaseActivity<DepositCoinViewModel,ViewDataBinding>() {
 companion object{
      var STARTCODE=110
      var RESULTCODE=111
 }
    override fun layoutId()=R.layout.activity_deposit_money
    var coinType="USDT"
    var coinOtherType="USDT"
    var address=""
    var memo=""
    var mBitmap:Bitmap?=null
    override fun initView(savedInstanceState: Bundle?) {
        setTitleAndRightResButton( resources.getString(R.string.title_asset_deposit),R.mipmap.ic_deposit_history,object :OnRightClickItem{
            override fun onClick() {
                var intent=Intent(this@DepositMoneyActivity,DepositCoinHistoryActivity::class.java)
                intent.putExtra("coinType",coinType)
                intent.putExtra("historyType","Recharge")
                startActivity(intent)
            }

        })
        //选择币
        csChoseCoin.setOnClickListener {
            var intent= Intent(this,ChoseCoinTypeActivity::class.java)
            intent.putExtra("choseType","1")
            startActivityForResult(intent,STARTCODE)
        }

        saveQrCode.setOnClickListener {
            if (mBitmap != null) {
                DeviceIDUtil.saveBitmap2file(mBitmap!!,this@DepositMoneyActivity)
            }
        }
        tvCopyAddress .setOnClickListener(View.OnClickListener {
            APPHelper.copy(
                this,
                address
            )
        })
        tvCopyMemo.setOnClickListener(View.OnClickListener {
            APPHelper.copy(
                this,
                memo
            )
        })
        getDepositCoin("USDT")
    }
     private fun getDepositCoin(clickType:String){
                    viewModel.run {
               withDrawlCoin(clickType).observe(this@DepositMoneyActivity, Observer {
                   if (it.status==200) {
                       tvCoinType1.text=coinOtherType
                       if (coinOtherType=="USDT"){
                           depositCoinTip.visibility=View.GONE
                           tvMemo.visibility=View.GONE
                           tvCopyMemo.visibility=View.GONE
                           chainName.visibility=View.VISIBLE
                           chainType.visibility=View.VISIBLE
                           rulesTv.text = getString(R.string.usdt_deposit_rules)
                           coinType="USDT"
                       }else if(coinOtherType=="UYT_TEST"){
                           depositCoinTip.visibility=View.VISIBLE
                           tvMemo.visibility=View.VISIBLE
                           tvCopyMemo.visibility=View.VISIBLE
                           chainName.visibility=View.GONE
                           chainType.visibility=View.GONE
                           rulesTv.text = getString(R.string.uyt_test_deposit_rules)
                           depositCoinTip.text = getString(R.string.memo_test_tip)
                           coinType="UYT"
                       }else if(coinOtherType=="UYT"){
                           depositCoinTip.visibility=View.VISIBLE
                           tvMemo.visibility=View.VISIBLE
                           tvCopyMemo.visibility=View.VISIBLE
                           chainName.visibility=View.GONE
                           chainType.visibility=View.GONE
                           rulesTv.text = getString(R.string.uyt_deposit_rules)
                           depositCoinTip.text = getString(R.string.memo_tip)
                           coinType="UYTPRO"
                       }else if(coinOtherType=="UTC"){
                           depositCoinTip.visibility=View.VISIBLE
                           tvMemo.visibility=View.VISIBLE
                           tvCopyMemo.visibility=View.VISIBLE
                           chainName.visibility=View.GONE
                           chainType.visibility=View.GONE
                           rulesTv.text = getString(R.string.utc_deposit_rules)
                           depositCoinTip.text = getString(R.string.memo_tip)
                           coinType="UTC"
                       }
                       address=it.data.acountHash
                       memo=it.data.memo
                       addressTv.text = address
                       tvMemo.text=memo
                       QRCodeEncoderModel.EncodeQRCode(address)
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe { bitmap ->
                               qrcodeIv.setImageBitmap(bitmap)
                               mBitmap=bitmap

                           }
                   }else{
                       ErrorCode.showErrorMsg(this@DepositMoneyActivity,it.status)
                   }
               })
           }
     }
    override fun initData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if (requestCode==STARTCODE&&resultCode==RESULTCODE){
               data?.getStringExtra("CoinType")?.let {
                   coinOtherType=it
                   var clickType=""
                   if (it=="USDT"){
                       clickType="USDT"
                   }else if(it=="UYT_TEST"){
                       clickType="UYT"
                   }else if(it=="UYT"){
                       clickType="UYTPRO"
                   }else if(it=="UTC"){
                       clickType="UTC"
                   }
                   getDepositCoin(clickType)

               }

          }
    }
}