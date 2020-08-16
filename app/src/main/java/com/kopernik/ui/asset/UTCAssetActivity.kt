package com.kopernik.ui.asset

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.WithdrawEarningsDialog
import com.kopernik.ui.asset.adapter.UTCExchangeRecordAdapter
import com.kopernik.ui.asset.adapter.UTCSynthesisRecordAdapter
import kotlinx.android.synthetic.main.activity_utc_asset.*

class UTCAssetActivity : NewFullScreenBaseActivity<NoViewModel, ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_utc_asset

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager=LinearLayoutManager(this)
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa")
        var adpter=UTCExchangeRecordAdapter(list)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_exchange_record_head,null))
        recyclerView.adapter=adpter
        recyclerView.layoutManager=LinearLayoutManager(this)
        var adpter1=UTCSynthesisRecordAdapter(list)
        adpter1.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_synthesis_record_head,null))
        recyclerView.adapter=adpter1
        ///兑换
        exchange.setOnClickListener {
            var exchangeDialog = ExchangeDialog.newInstance(1)
            exchangeDialog!!.setOnRequestListener(object : ExchangeDialog.RequestListener {
                override fun onRequest(type: Int, params: String) {

                }
            })
            exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
        }
    }

    override fun initData() {


    }

}