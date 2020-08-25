package com.kopernik.ui.mine

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.dialog.PurchaseDialog
import com.kopernik.ui.mine.adapter.OutTimeMiningMAdapter
import com.kopernik.ui.mine.adapter.PurchaseMiningMAdapter
import com.kopernik.ui.mine.adapter.RuntimeMiningMAdapter
import com.kopernik.ui.mine.viewModel.MineMachineryViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.*
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.llTab
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.recyclerView
import kotlinx.android.synthetic.main.activity_udmt_asset.*


class PurchaseMiningMachineryActivity : NewBaseActivity<MineMachineryViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_purchase_mining_machinery
    //0购买矿机列表 1有效矿机 2过期矿机
    private var machinngType=0
    var adapter= PurchaseMiningMAdapter(arrayListOf())
    
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_purchase_mm))
        //购买矿机
        llTab.setOnClickListener { onTabOnClick(true,false,false) }
       //运行矿机
        llTab1.setOnClickListener { onTabOnClick(false,true,false) }
        //过时矿机
        llTab2.setOnClickListener {  onTabOnClick(false,false,true)}
        smartRefreshLayout.setOnRefreshListener {
            getList()
        }
        adapter?.setOnItemChildClickListener { adapter, view, position -> 
            if (view.id==R.id.tv_purchase){
                var dialog = PurchaseDialog.newInstance(1)
                dialog!!.setOnRequestListener(object : PurchaseDialog.RequestListener {
                    override fun onRequest(type: Int, params: String) {

                    }
                })
                dialog!!.show(supportFragmentManager, "withdrawRecommed")
            }
        }
        smartRefreshLayout.autoRefresh()
    }
    fun getList(){
        viewModel.run {
            getMachineList().observe(this@PurchaseMiningMachineryActivity, Observer { data ->
                smartRefreshLayout.finishRefresh()
                 if (data.status==200){
                     data.data.nachineList?.let {
                         adapter?.setNewData(it)
                     }
                    
                 }
            })
//            var map= mapOf("status" to "1","pageNumber" to "1" ,"pageSize" to "10")
//            getMachine(map).observe(this@PurchaseMiningMachineryActivity, Observer {
//
//            })
        }
    }


    override fun initData() {
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
        recyclerView1.layoutManager= LinearLayoutManager(this)
        var adpter1= RuntimeMiningMAdapter(arrayListOf())
        recyclerView1.adapter=adpter1


        recyclerView2.layoutManager= LinearLayoutManager(this)
        var adpter2= OutTimeMiningMAdapter(arrayListOf())
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