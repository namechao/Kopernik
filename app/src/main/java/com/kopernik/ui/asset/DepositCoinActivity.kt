package com.kopernik.ui.asset

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.QRCode.QRCodeEncoderModel
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.common.AssetOptConstant
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.viewModel.DepositCoinViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_deposit_coin.*
import kotlinx.android.synthetic.main.layout_copy.*

class DepositCoinActivity : NewBaseActivity<DepositCoinViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_deposit_coin

    override fun initView(savedInstanceState: Bundle?) {

//                AssetOptConstant.BTC -> {
//                    setTitle("BTC" + resources.getString(R.string.title_recharge_address))
//                    rulesTv.setText(getString(R.string.btc_recharge_rules))
//                }
//                AssetOptConstant.ETH -> {
//                    setTitle("ETH" + resources.getString(R.string.title_recharge_address))
//                    rulesTv.setText(getString(R.string.eth_recharge_rules))
//                }
//                AssetOptConstant.USDT -> {
//                    setTitle("USDT" + resources.getString(R.string.title_recharge_address))
//                    rulesTv.setText(getString(R.string.usdt_recharge_rules))
//                }
//                AssetOptConstant.HT -> {
//                    setTitle("HT" + resources.getString(R.string.title_recharge_address))
//                    rulesTv.setText(getString(R.string.ht_recharge_rules))
//                }
//            addressTv.setText(address)

//            copyTv .setOnClickListener(View.OnClickListener { v: View? ->
//                    APPHelper.copy(
//                        this,
//                        address
//                    )
//                })
//      QRCodeEncoderModel.EncodeQRCode(address)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ bitmap -> qrcodeIv.setImageBitmap(bitmap) })
//        }
    }

    override fun initData() {

    }
}