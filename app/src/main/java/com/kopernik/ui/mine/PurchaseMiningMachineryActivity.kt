package com.kopernik.ui.mine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.GetUtkDialog
import com.kopernik.app.dialog.PurchaseDialog
import com.kopernik.app.dialog.ReminderDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.mine.adapter.OutTimeMiningMAdapter
import com.kopernik.ui.mine.adapter.PurchaseMiningMAdapter
import com.kopernik.ui.mine.adapter.RuntimeMiningMAdapter
import com.kopernik.ui.mine.entity.Data
import com.kopernik.ui.mine.entity.Machine
import com.kopernik.ui.mine.entity.MineBean
import com.kopernik.ui.mine.entity.PurchaseEntity
import com.kopernik.ui.mine.viewModel.MineMachineryViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.*
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.llTab
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.recyclerView
import kotlinx.android.synthetic.main.activity_purchase_mining_machinery.smartRefreshLayout
import kotlin.math.log


class PurchaseMiningMachineryActivity : NewBaseActivity<MineMachineryViewModel,ViewDataBinding>() {

    override fun layoutId()=R.layout.activity_purchase_mining_machinery
    //0购买矿机列表 1有效矿机 2过期矿机
    private var machinngType=0
    private var pager1=1
    private var pager2=1
    var type=""
    var adapter= PurchaseMiningMAdapter(arrayListOf())
    var adpter1= RuntimeMiningMAdapter(arrayListOf())
    var adpter2= OutTimeMiningMAdapter(arrayListOf())
    var minebean:MineBean?=null
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_purchase_mm))
        //购买矿机
        llTab.setOnClickListener {
            machinngType=0
            onTabOnClick(true,false,false)
        }
       //运行矿机
        llTab1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true,false) }
        //过时矿机
        llTab2.setOnClickListener {
            machinngType=2
            onTabOnClick(false,false,true)}
            smartRefreshLayout.setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager1=1
                pager2=1
                getList()
            }

        })
        //购买按钮
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id==R.id.tv_purchase){
                //判断是否设置交易密码
                if (UserConfig.singleton?.accountBean!=null){
                    if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                        if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                            LaunchConfig.startTradePasswordActivity(this, 1,1)
                            return@setOnItemChildClickListener
                        }
                    }else{
                        if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                            LaunchConfig.startTradePasswordActivity(this, 2,1)
                            return@setOnItemChildClickListener
                        }
                    }
                }
               //flag true代表可以买 fasle 购买矿机和数量以达到最大
               if (minebean!=null &&minebean?.flag!=null && !minebean?.flag!!){
                   ToastUtils.showShort(this,getString(R.string.purchase_total_limit))
                  return@setOnItemChildClickListener

               }
                //parentFlag true代表可以买 fasle  矿机到8个提示文案：此社群矿机已达上限
                if (minebean!=null&&minebean?.parentFlag!=null&& !minebean?.parentFlag!!){
                    ToastUtils.showShort(this,getString(R.string.purchase_team_total_limit))
                    return@setOnItemChildClickListener
                }
                //购买重组数据
                var purchaseEntity=PurchaseEntity()
                purchaseEntity.mineMacName= (adapter.data[position] as Machine).name
                purchaseEntity.mineMacPrice= (adapter.data[position] as Machine).price
                purchaseEntity.consumeUyt= BigDecimalUtils.divide(BigDecimalUtils.multiply((adapter.data[position] as Machine).price,"0.3").toString(),minebean?.uytPrice,8)
                purchaseEntity.consumeUytPro= BigDecimalUtils.divide(BigDecimalUtils.multiply((adapter.data[position] as Machine).price,"0.7").toString(),minebean?.uytProPrice,8)
                minebean?.uytCaptial?.amount?.let {
                    purchaseEntity.uytBanlance=it
                }
                minebean?.uytProCaptial?.amount?.let {
                    purchaseEntity.uytProBanlance=it
                }
                minebean?.uytToUsdt?.let {
                    purchaseEntity.uytToUsdt=it
                }
                minebean?.uytProToUsdt?.let {
                    purchaseEntity.uytProToUsdt=it
                }
                //购买弹窗
                var dialog = PurchaseDialog.newInstance(1,purchaseEntity)
                dialog!!.setOnRequestListener(object : PurchaseDialog.RequestListener {
                    override fun onRequest(params: String) {
                        type= (adapter.data[position] as Machine).type.toString()
                        checkPassword(params)
                    }
                })
                dialog!!.show(supportFragmentManager, "withdrawRecommed")
            }
        }
        smartRefreshLayout.autoRefresh()
    }
    fun  checkPassword(params: String){
        viewModel.run {
            var map= mapOf("pwd" to MD5Utils.md5(MD5Utils.md5(params)))
            checkTradePassword(map).observe(this@PurchaseMiningMachineryActivity, Observer {
                if (it.status==200){
                 purchaseMineMac()
                }else{
                    ErrorCode.showErrorMsg(getActivity(), it.status)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        smartRefreshLayout.autoRefresh()
    }
    private fun purchaseMineMac() {
        viewModel.run {
        var uid=""
        UserConfig.singleton?.accountBean?.uid?.let {
            uid=it
        }
        var map= mapOf("uid" to uid ,"type" to type,"iconType" to "UYT")
        buyMineMachine(map).observe(this@PurchaseMiningMachineryActivity, Observer {
            if (it.status==200)
                ReminderDialog(this@PurchaseMiningMachineryActivity)
                    .setCancelable(true)
                    .show()
            else   ErrorCode.showErrorMsg(getActivity(), it.status)
        })
        }
    }

    //获取矿机列表
    fun getList(){
        viewModel.run {
            if (machinngType==0) {
                getMachineList().observe(this@PurchaseMiningMachineryActivity, Observer { data ->
                    smartRefreshLayout.finishRefreshWithNoMoreData()
                    if (data.status == 200) {
                        minebean=data.data
                        data.data.nachineList?.let {
                            adapter?.setNewData(it)
                        }

                    }
                })
            }
            else if(machinngType==1) {
                var map = mapOf("status" to "1", "pageNumber" to pager1.toString(), "pageSize" to "10")
                getMachine1(map).observe(this@PurchaseMiningMachineryActivity, Observer {

                    if (it.status==200){
                        val datas: List<Data>?=it.data.datas
                        if (pager1 == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager1++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adpter1?.setNewData(datas)
                        } else {
                            if (datas != null) {
                                if (datas.size < 10) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                                } else {
                                    pager1++
                                    smartRefreshLayout.finishLoadMore(true)
                                }
                            }
                            if (datas != null) {
                                adpter1?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }else if(machinngType==2){
                var map = mapOf("status" to "2", "pageNumber" to pager2.toString(), "pageSize" to "10")
                getMachine2(map).observe(this@PurchaseMiningMachineryActivity, Observer {
                    if (it.status==200){
                        val datas: List<Data>?=it.data.datas
                        if (pager2 == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager2++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adpter2?.setNewData(datas)
                        } else {
                            if (datas != null) {
                                if (datas.size < 10) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                                } else {
                                    pager2++
                                    smartRefreshLayout.finishLoadMore(true)
                                }
                            }
                            if (datas != null) {
                                adpter2?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }

        }
    }


    override fun initData() {
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
        recyclerView1.layoutManager= LinearLayoutManager(this)
        recyclerView1.adapter=adpter1
        recyclerView2.layoutManager= LinearLayoutManager(this)
        recyclerView2.adapter=adpter2
    }

     //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean,threeClick:Boolean){
           smartRefreshLayout.autoRefresh()
                if (oneClick){
                    tv_purchase.setTextColor(resources.getColor(R.color.color_ffcf32))
                    tv_purchase_line.visibility=View.VISIBLE
                    recyclerView.visibility=View.VISIBLE
                }else {
                    tv_purchase.setTextColor(resources.getColor(R.color.white))
                    tv_purchase_line.visibility=View.GONE
                    recyclerView.visibility=View.GONE
                }
             if (twoClick){
                    tv_runtime_mining.setTextColor(resources.getColor(R.color.color_ffcf32))
                    tv_runtime_mining_line.visibility=View.VISIBLE
                    recyclerView1.visibility=View.VISIBLE
                 }else {
                    tv_runtime_mining.setTextColor(resources.getColor(R.color.white))
                   tv_runtime_mining_line.visibility=View.GONE
                    recyclerView1.visibility=View.GONE
                 }
             if (threeClick){
                tv_outtime_mining.setTextColor(resources.getColor(R.color.color_ffcf32))
                tv_outtime_mining_line.visibility=View.VISIBLE
                recyclerView2.visibility=View.VISIBLE
            }else {
                 tv_outtime_mining.setTextColor(resources.getColor(R.color.white))
                 recyclerView2.visibility=View.GONE
                 tv_outtime_mining_line.visibility=View.GONE
            }

       }

}