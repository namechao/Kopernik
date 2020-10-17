package com.kopernik.ui.asset

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.QRCode.QRCodeEncoderModel
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_deposit_money.*

import kotlinx.android.synthetic.main.layout_copy.*

class DepositMoneyActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_deposit_money

    override fun initView(savedInstanceState: Bundle?) {
        setTitle( resources.getString(R.string.title_asset_deposit))
        val address = intent.getStringExtra("address")
        val memo = intent.getStringExtra("memo")
        val type = intent.getStringExtra("type")
        if (StringUtils.isEmpty(address)) {
            ToastUtils.showShort(getActivity(), getString(R.string.address_is_empty))
            finish()
        } else {
                if (type=="UYT") {
                    rulesTv.text = getString(R.string.uyt_test_deposit_rules)
                    depositCoinTip.text = getString(R.string.memo_test_tip)
                }
                else if (type=="UYTPRO") {
                    rulesTv.text = getString(R.string.uyt_deposit_rules)
                    depositCoinTip.text = getString(R.string.memo_tip)
                }
            addressTv.text = address
            tvCopyAddress .setOnClickListener(View.OnClickListener {
                APPHelper.copy(
                        this,
                        address
                    )
                })
            tvMemo.text=memo
            tvCopyMemo.setOnClickListener(View.OnClickListener {
                APPHelper.copy(
                    this,
                    memo
                )
            })
      QRCodeEncoderModel.EncodeQRCode(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bitmap -> qrcodeIv.setImageBitmap(bitmap) })
        }
    }

    override fun initData() {

    }
}