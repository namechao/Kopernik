package com.kopernik.ui.asset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.dialog.UTKTransferDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.UTCExchangeRecordAdapter
import com.kopernik.ui.asset.adapter.UTCSynthesisRecordAdapter
import com.kopernik.ui.asset.adapter.UTKReceiveRecordAdapter
import com.kopernik.ui.asset.adapter.UTKTransferRecordAdapter
import com.kopernik.ui.asset.entity.ExchangeRecord
import com.kopernik.ui.asset.entity.UtcComRecord
import com.kopernik.ui.asset.entity.UtkReceiveRecord
import com.kopernik.ui.asset.entity.UtkTransferRecord
import com.kopernik.ui.asset.viewModel.UTCAssetViewModel
import com.kopernik.ui.asset.viewModel.UTKAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_utk_asset.*
import kotlinx.android.synthetic.main.activity_utk_asset.assetTotal
import kotlinx.android.synthetic.main.activity_utk_asset.llTitle
import kotlinx.android.synthetic.main.activity_utk_asset.recyclerView
import kotlinx.android.synthetic.main.activity_utk_asset.recyclerView1
import kotlinx.android.synthetic.main.activity_utk_asset.smartRefreshLayout

class UTKAssetActivity : NewFullScreenBaseActivity<UTKAssetViewModel,ViewDataBinding>() {
    private var machinngType=0
    private var pager=1
    private var pager1=1
    private var allConfigEntity:AllConfigEntity?=null
    var adpter= UTKReceiveRecordAdapter(arrayListOf())
    var adpter1= UTKTransferRecordAdapter(arrayListOf())
    override fun layoutId()=R.layout.activity_utk_asset
    override fun initView(savedInstanceState: Bundle?) {
        //两个列表初始化
        recyclerView.layoutManager= LinearLayoutManager(this)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_receive_record_head,null))
        recyclerView.adapter=adpter
        recyclerView1.layoutManager= LinearLayoutManager(this)
        adpter1.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_transfer_record_head,null))
        recyclerView1.adapter=adpter1
        transfer.setOnClickListener {
            if (allConfigEntity!=null) {
                var dialog = UTKTransferDialog.newInstance(allConfigEntity!!)
                dialog!!.setOnRequestListener(object : UTKTransferDialog.RequestListener {
                    override fun onRequest(
                        transferCounts: String,
                        uid: String,
                        rate: String,
                        psw: String
                    ) {
                        transfer(transferCounts, uid, rate, psw)
                    }
                })
                dialog!!.show(supportFragmentManager, "withdrawRecommed")
            }

        }
    }

    override fun initData() {
        llTitle.setOnClickListener {
            machinngType=0
            onTabOnClick(true,false)
        }
        llTitle1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true)
        }

        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager=1
                pager1=1
                getList()
                getCurrentAsset()
            }

        })
        smartRefreshLayout.autoRefresh()
    }
    private  fun  getCurrentAsset(){
        viewModel.run {
            getAssetConfig().observe(this@UTKAssetActivity, Observer {
                if (it.status==200){
                    allConfigEntity=it.data
                    assetTotal.text= BigDecimalUtils.roundDOWN(it.data. utk,2)
                }
            })
        }
    }
    private fun transfer(ransferCounts: String, uid: String,rate:String,psw:String) {

        viewModel.run {
            var map= mapOf("amount" to ransferCounts,"uidReceive" to uid,"rate" to rate ,"type" to "UTK" ,"pwd" to MD5Utils.md5(
                MD5Utils.md5(psw)))
            transfer(map).observe(this@UTKAssetActivity, Observer {
                if (it.status==200){
                    ToastUtils.showShort(this@UTKAssetActivity,resources.getString(R.string.tip_asset_transfer_success))
                }else{
                    ErrorCode.showErrorMsg(this@UTKAssetActivity,it.status)
                }
            })
        }

    }
    fun getList(){
        viewModel.run {
            if (machinngType==0) {
                var map = mapOf("pageNumber" to pager.toString(),"pageSize" to "10")
                receiveRecord(map).observe(this@UTKAssetActivity, Observer {

                    if (it.status==200){
                        val datas: List<UtkReceiveRecord>?=it.data.datas
                        if (pager == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.setNoMoreData(true)
                                return@Observer
                            }
                            if (datas.size > 9) {
                                smartRefreshLayout.finishRefresh(300)
                                pager++
                            } else {
                                smartRefreshLayout.finishRefreshWithNoMoreData()
                            }

                            adpter?.setNewData(datas)
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
                                adpter?.addData(datas)
                            }
                        }
                    }else{
                        ErrorCode.showErrorMsg(getActivity(), it.status)
                    }
                })
            }else{
                var map = mapOf("type" to "UTK","pageNumber" to pager1.toString(),"pageSize" to "10")
                transferRecord(map).observe(this@UTKAssetActivity, Observer {

                    if (it.status==200){
                        val datas: List<UtkTransferRecord>?=it.data.datas
                        if (pager1 == 1) {
                            if (datas == null || datas.isEmpty()) {
                                smartRefreshLayout.setNoMoreData(true)
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
            }

        }


    }
    //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean){
        smartRefreshLayout.autoRefresh()
        if (oneClick){
            utkTitle.setTextColor(resources.getColor(R.color.color_ffcf32))
            utkTitleLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView.visibility= View.VISIBLE
        }else {
            utkTitle.setTextColor(resources.getColor(R.color.white))
            utkTitleLine.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView.visibility= View.GONE
        }
        if (twoClick){
            utkTitle1.setTextColor(resources.getColor(R.color.color_ffcf32))
            utkTitleLine1.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView1.visibility= View.VISIBLE
        }else {
            utkTitle1.setTextColor(resources.getColor(R.color.white))
            utkTitleLine1.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView1.visibility= View.GONE
        }

    }

}