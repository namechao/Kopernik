package com.kopernik.ui.mine

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.dialog.PurchaseDialog
import com.kopernik.app.dialog.UDMTDialog
import com.kopernik.ui.mine.adapter.OutTimeMiningMAdapter
import com.kopernik.ui.mine.adapter.PurchaseMiningMAdapter
import com.kopernik.ui.mine.adapter.RuntimeMiningMAdapter
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.*
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.llTab
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.recyclerView
import kotlinx.android.synthetic.main.activity_udmt_asset.*


class PurchaseMiningMachineryActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_purchase_mining_machinery

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_purchase_mm))
        //购买矿机
        llTab.setOnClickListener { onTabOnClick(true,false,false) }
       //运行矿机
        llTab1.setOnClickListener { onTabOnClick(false,true,false) }
        //过时矿机
        llTab2.setOnClickListener {  onTabOnClick(false,false,true)}
    }


    override fun initData() {
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa","ssdsdad","Asdadasdasda","asfafafafaa")
        recyclerView.layoutManager= LinearLayoutManager(this)
        var adpter= PurchaseMiningMAdapter(list)
        adpter.setOnItemClickListener { adapter, view, position ->

                var dialog = PurchaseDialog.newInstance(1)
            dialog!!.setOnRequestListener(object : PurchaseDialog.RequestListener {
                    override fun onRequest(type: Int, params: String) {

                    }
                })
            dialog!!.show(supportFragmentManager, "withdrawRecommed")
        }
        recyclerView.adapter=adpter



        recyclerView1.layoutManager= LinearLayoutManager(this)
        var adpter1= RuntimeMiningMAdapter(list)
        recyclerView1.adapter=adpter1


        recyclerView2.layoutManager= LinearLayoutManager(this)
        var adpter2= OutTimeMiningMAdapter(list)
        recyclerView2.adapter=adpter2
    }

     //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean,threeClick:Boolean){

                if (oneClick){
                    tv_purchase.setTextColor(resources.getColor(R.color.color_ffcf32))
                    tv_purchase_line.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
                    recyclerView.visibility=View.VISIBLE
                }else {
                    tv_purchase.setTextColor(resources.getColor(R.color.white))
                    tv_purchase_line.setBackgroundColor(resources.getColor(R.color.white))
                    recyclerView.visibility=View.GONE
                }
             if (twoClick){
                    tv_runtime_mining.setTextColor(resources.getColor(R.color.color_ffcf32))
                    tv_runtime_mining_line.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
                    recyclerView1.visibility=View.VISIBLE
                 }else {
                    tv_runtime_mining.setTextColor(resources.getColor(R.color.white))
                    tv_runtime_mining_line.setBackgroundColor(resources.getColor(R.color.white))
                    recyclerView1.visibility=View.GONE
                 }
             if (threeClick){
                tv_outtime_mining.setTextColor(resources.getColor(R.color.color_ffcf32))
                tv_outtime_mining_line.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
                recyclerView2.visibility=View.VISIBLE
            }else {
                 tv_outtime_mining.setTextColor(resources.getColor(R.color.white))
                 tv_outtime_mining_line.setBackgroundColor(resources.getColor(R.color.white))
                recyclerView2.visibility=View.GONE
            }

       }

}