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
        setTitle("UYT" + resources.getString(R.string.title_recharge_address))
        val type = intent.getIntExtra("type", -1)
        val address = intent.getStringExtra("address")
        if (type == -1) finish()
        if (StringUtils.isEmpty(address)) {
            ToastUtils.showShort(getActivity(), getString(R.string.address_is_empty))
            finish()
        } else {
            rulesTv.setText(getString(R.string.uyt_recharge_rules))
            addressTv.setText(address)
            copyTv .setOnClickListener(View.OnClickListener { v: View? ->
                    APPHelper.copy(
                        this,
                        address
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