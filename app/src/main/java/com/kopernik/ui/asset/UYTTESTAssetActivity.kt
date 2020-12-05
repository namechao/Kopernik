package com.kopernik.ui.asset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.UytTestExchangeDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.UYTDepositWithdrawlAssetAdapter
import com.kopernik.ui.asset.adapter.UYTTransferAssetAdapter
import com.kopernik.ui.asset.adapter.UytTestExchangeRecordAdapter
import com.kopernik.ui.asset.entity.DepositWithdarwlRecord
import com.kopernik.ui.asset.entity.UYTTransferRecord
import com.kopernik.ui.asset.entity.UytTestExchangeRecord
import com.kopernik.ui.asset.viewModel.UYTAssetViewModel
import com.kopernik.ui.asset.viewModel.UYTTESTAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_utc_asset.*
import kotlinx.android.synthetic.main.activity_utc_asset.exchange
import kotlinx.android.synthetic.main.activity_uyt_asset.*
import kotlinx.android.synthetic.main.activity_uyt_asset.assetTotal
import kotlinx.android.synthetic.main.activity_uyt_asset.ivBack
import kotlinx.android.synthetic.main.activity_uyt_asset.llTitle
import kotlinx.android.synthetic.main.activity_uyt_asset.llTitle1
import kotlinx.android.synthetic.main.activity_uyt_asset.recyclerView
import kotlinx.android.synthetic.main.activity_uyt_asset.recyclerView1
import kotlinx.android.synthetic.main.activity_uyt_asset.smartRefreshLayout
import kotlinx.android.synthetic.main.activity_uyt_asset.transfer
import kotlinx.android.synthetic.main.activity_uyt_asset.uytTitle
import kotlinx.android.synthetic.main.activity_uyt_asset.uytTitle1
import kotlinx.android.synthetic.main.activity_uyt_asset.uytTitleLine
import kotlinx.android.synthetic.main.activity_uyt_asset.uytTitleLine1
import kotlinx.android.synthetic.main.activity_uyt_test_asset.*

class UYTTESTAssetActivity : NewFullScreenBaseActivity<UYTTESTAssetViewModel, ViewDataBinding>() {

    private var pager=1
    private var pager1=1
    private var allConfigEntity: AllConfigEntity?=null
    private var machinngType=0
    private var adapter= UYTDepositWithdrawlAssetAdapter(arrayListOf())
    private var adapter1 = UYTTransferAssetAdapter(arrayListOf())
    private var adapter2 = UytTestExchangeRecordAdapter(arrayListOf())
    private var type = ""
    private var isFirst=true
    override fun layoutId()=R.layout.activity_uyt_test_asset

    override fun initView(savedInstanceState: Bundle?) {
        intent.getStringExtra("asset")?.let {
            assetTotal.text=BigDecimalUtils.roundDOWN(it,2)
        }
        intent.getStringExtra("type")?.let {
            type=it
        }
        ivBack.setOnClickListener {
            finish()
        }
    }
    //初始化数据
    override fun initData() {
        recyclerView.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView1.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView1.adapter = adapter1
        recyclerView2.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter2
        adapter2.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_uyt_test_exchange_record_head,null))
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getListData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pager=1
                pager1=1
                if (isFirst){
                    isFirst=false
                }else {
                    getCurrentAsset()
                }
                getListData()
            }
        })
        smartRefreshLayout.autoRefresh()

        llTitle.setOnClickListener {
            machinngType=0
            onTabOnClick(true,false,false)
        }
        llTitle1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true,false)
        }
        llTitle2.setOnClickListener {
            machinngType=2
            onTabOnClick(false,false,true)
        }
        ///兑换
        exchange.setOnClickListener {
            //判断是否设置交易密码
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                        LaunchConfig.startTradePasswordActivity(this, 1,1)
                        return@setOnClickListener
                    }
                }else{
                    if (UserConfig.singleton?.tradePassword.isNullOrEmpty()){
                        LaunchConfig.startTradePasswordActivity(this, 2,1)
                        return@setOnClickListener
                    }
                }
            }
            getConfig()
        }
    }
    fun getConfig(){
        viewModel.run {
            getConfig().observe(this@UYTTESTAssetActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                    if (allConfigEntity!=null){
                        var exchangeDialog = UytTestExchangeDialog.newInstance(allConfigEntity!!)
                        exchangeDialog!!.setOnRequestListener(object : UytTestExchangeDialog.RequestListener {
                            override fun onRequest(etUytTestCounts:String,uytCounts: String, params: String,rate:String) {
                                exchangeCoin(etUytTestCounts,uytCounts,params,rate)
                            }
                        })
                        exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
                    }

                }else{
                    ErrorCode.showErrorMsg(this@UYTTESTAssetActivity,it.status)
                }
            })
        }
    }
    private fun exchangeCoin(etUytTestCounts:String,uytCounts: String, psw: String,rate:String) {

        viewModel.run {
            var map= mapOf("amount" to etUytTestCounts,"rate" to rate ,"pwd" to MD5Utils.md5(
                MD5Utils.md5(psw)))
            exchange(map).observe(this@UYTTESTAssetActivity, Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@UYTTESTAssetActivity,resources.getString(R.string.exchange_success))
                }else{
                    ErrorCode.showErrorMsg(this@UYTTESTAssetActivity,it.status)
                }
            })
        }

    }

//    fun getWithDrawl(){
//        viewModel.run {
//            getConfig().observe(this@UYTTESTAssetActivity, Observer {
//                if (it.status==200){
//                    allConfigEntity=it.data
//                    LaunchConfig.startWithdrawCoinAc(
//                        this@UYTTESTAssetActivity,allConfigEntity,type
//                    )
//                }else{
//                    ErrorCode.showErrorMsg(this@UYTTESTAssetActivity,it.status)
//                }
//            })
//        }
//    }
//    fun getTransferConfig(){
//        viewModel.run {
//            getTransferConfig().observe(this@UYTTESTAssetActivity, Observer {
//                if (it.status==200){
//                    allConfigEntity=it.data
//                    LaunchConfig.startTransferAc(
//                        this@UYTTESTAssetActivity,
//                        allConfigEntity,
//                        type
//                    )
//                }else{
//                    ErrorCode.showErrorMsg(this@UYTTESTAssetActivity,it.status)
//                }
//            })
//        }
//    }
    private  fun  getCurrentAsset(){
        viewModel.run {
            getAssetConfig().observe(this@UYTTESTAssetActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                    assetTotal.text= BigDecimalUtils.roundDOWN(it.data.uyt,2)
                }
            })
        }
    }

    //获取资产和下边记录列表
    private fun getListData() {
        viewModel.run {
            if (machinngType==0) {
                var map = mapOf("type" to type,"pageNumber" to pager.toString(),"pageSize" to "10")
                rechargeCashRecord(map).observe(this@UYTTESTAssetActivity, Observer {
                    if (it.status==200){
                        val datas: List<DepositWithdarwlRecord>?=it.data.datas
                        if (pager == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adapter?.setNewData(datas)
                        } else {
                            if (datas != null) {
                                if (datas.size < 10) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                                } else {
                                    pager++
                                    smartRefreshLayout.finishLoadMore(true)
                                }
                            }
                            if (datas != null) {
                                adapter?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }else if(machinngType==1) {
                var map = mapOf("type" to type,"pageNumber" to pager1.toString(),"pageSize" to "10")
                transferRecord(map).observe(this@UYTTESTAssetActivity, Observer {

                    if (it.status==200){
                        val datas: List<UYTTransferRecord>?=it.data.datas
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

                            adapter1?.setNewData(datas)
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
                                adapter1?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }else{
                var map = mapOf("type" to type,"pageNumber" to pager1.toString(),"pageSize" to "10")
                exchangeRecord(map).observe(this@UYTTESTAssetActivity, Observer {

                    if (it.status==200){
                        val datas=it.data.datas
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

                            adapter2?.setNewData(datas)
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
                                adapter2?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }

        }


    }
    //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean,threeClick:Boolean){
        smartRefreshLayout.autoRefresh()
        if (oneClick){
            uytTitle.setTextColor(resources.getColor(R.color.color_ffcf32))
            uytTitleLine.visibility= View.VISIBLE
            recyclerView.visibility= View.VISIBLE
        }else {
            uytTitle.setTextColor(resources.getColor(R.color.white))
            uytTitleLine.visibility= View.GONE
            recyclerView.visibility= View.GONE
        }
        if (twoClick){
            uytTitle1.setTextColor(resources.getColor(R.color.color_ffcf32))
            uytTitleLine1.visibility= View.VISIBLE
            recyclerView1.visibility= View.VISIBLE
        }else {
            uytTitle1.setTextColor(resources.getColor(R.color.white))
            uytTitleLine1.visibility= View.GONE
            recyclerView1.visibility= View.GONE
        }
        if (threeClick){
            uytTitle2.setTextColor(resources.getColor(R.color.color_ffcf32))
            uytTitleLine2.visibility= View.VISIBLE
            recyclerView2.visibility= View.VISIBLE
        }else {
            uytTitle1.setTextColor(resources.getColor(R.color.white))
            uytTitleLine2.visibility= View.GONE
            recyclerView2.visibility= View.GONE
        }
    }

}