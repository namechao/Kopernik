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
import com.kopernik.app.dialog.UTKTransferDialog
import com.kopernik.ui.asset.adapter.UTCExchangeRecordAdapter
import com.kopernik.ui.asset.adapter.UTCSynthesisRecordAdapter
import com.kopernik.ui.asset.adapter.UTKReceiveRecordAdapter
import com.kopernik.ui.asset.adapter.UTKTransferRecordAdapter
import kotlinx.android.synthetic.main.activity_utk_asset.*
import kotlinx.android.synthetic.main.activity_utk_asset.recyclerView

class UTKAssetActivity : NewFullScreenBaseActivity<NoViewModel,ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_utk_asset

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager= LinearLayoutManager(this)
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa")
        var adpter= UTKReceiveRecordAdapter(list)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_receive_record_head,null))
        recyclerView.adapter=adpter
        recyclerView1.layoutManager= LinearLayoutManager(this)
        var adpter1= UTKTransferRecordAdapter(list)
        adpter1.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_transfer_record_head,null))
        recyclerView1.adapter=adpter1
        transfer.setOnClickListener {
            var dialog = UTKTransferDialog.newInstance(1)
            dialog!!.setOnRequestListener(object : UTKTransferDialog.RequestListener {
                override fun onRequest(type: Int, params: String) {

                }
            })
            dialog!!.show(supportFragmentManager, "withdrawRecommed")
        }
    }

    override fun initData() {

    }

}