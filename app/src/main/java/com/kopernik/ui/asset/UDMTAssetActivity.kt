package com.kopernik.ui.asset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.UDMTDialog
import com.kopernik.ui.asset.adapter.UDMTFinanceRecordsAdapter
import kotlinx.android.synthetic.main.activity_udmt_asset.*

class UDMTAssetActivity : NewFullScreenBaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_udmt_asset

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager= LinearLayoutManager(this)
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa")
        var adpter= UDMTFinanceRecordsAdapter(list)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_finance_record_head,null))
        recyclerView.adapter=adpter
        ///兑换
        synthesisUtc.setOnClickListener {
            var exchangeDialog = UDMTDialog.newInstance(1)
            exchangeDialog!!.setOnRequestListener(object : UDMTDialog.RequestListener {
                override fun onRequest(type: Int, params: String) {

                }
            })
            exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
        }
    }

    override fun initData() {

    }

}