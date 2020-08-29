package com.kopernik.ui.asset

import android.os.Bundle
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
import com.kopernik.app.dialog.WithdrawEarningsDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.UYTDepositWithdrawlAssetAdapter
import com.kopernik.ui.asset.adapter.UYTTransferAssetAdapter
import com.kopernik.ui.asset.entity.WithdrawEarningsBean
import com.kopernik.ui.asset.viewModel.UYTAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import kotlinx.android.synthetic.main.activity_uyt_asset.*
class UYTAssetActivity : NewFullScreenBaseActivity<UYTAssetViewModel, ViewDataBinding>() {

    private var pager=1
    private var pager1=1
    private var allConfigEntity: AllConfigEntity?=null
    private var machinngType=0
    private var adapter= UYTDepositWithdrawlAssetAdapter(arrayListOf())
    private var adapter1 = UYTTransferAssetAdapter(arrayListOf())
    override fun layoutId()=R.layout.activity_uyt_asset

    override fun initView(savedInstanceState: Bundle?) {

    }
    //初始化数据
    override fun initData() {
        recyclerView.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView1.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView1.adapter = adapter1

        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getListData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pager=1
                pager1=1
                getCurrentAsset()
                getListData()
            }
        })
        smartRefreshLayout.autoRefresh()
       //充币
        tvDepositCoin.setOnClickListener {
           viewModel.run {
               withDrawlCoin().observe(this@UYTAssetActivity, Observer {
                   if (it.status==200) {
                       LaunchConfig.startDepositMoneyActivity(
                           this@UYTAssetActivity,
                           it.data.acountHash,
                           it.data.memo
                       )
                   }else{
                       ErrorCode.showErrorMsg(this@UYTAssetActivity,it.status)
                   }
               })
           }


      }
        //提币
        tvWithDrawlCoin.setOnClickListener {
            LaunchConfig.startWithdrawCoinAc(
                this@UYTAssetActivity,"00"
            )
        }
        //转账
        transfer.setOnClickListener {
            viewModel.run {
//                transferaccount(chainName).observe(this@UYTAssetActivity, Observer {
//                    if (it.status == 200) {
                        LaunchConfig.startTransferAc(
                            this@UYTAssetActivity,
                            1,
                            "chainName"
                        )
//                    } else {
//                        ToastUtils.showShort(this@UYTAssetActivity, it.errorMsg)
//                    }
//                })
            }
        }
        llTitle.setOnClickListener {
            machinngType=0
            onTabOnClick(true,false)
        }
        llTitle1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true)
        }
    }

    //显示提取收益输入密码弹窗
    private fun showDialog() {
        var wdBean = WithdrawEarningsBean()
        wdBean.title = resources.getString(R.string.extract)
        wdBean.oneLineLeft = resources.getString(R.string.title_gain_recomment)
        wdBean.oneLineRight = "850 UYT"
        wdBean.twoLineLeft = resources.getString(R.string.estimated_miner_fee)
        wdBean.twoLineRight = "50 UYT"
        var extractDialog = WithdrawEarningsDialog.newInstance(1, wdBean)
        extractDialog!!.setOnRequestListener(object : WithdrawEarningsDialog.RequestListener {
            override fun onRequest(type: Int, params: String) {
                checkPsw(params, extractDialog)
            }
        })
        extractDialog!!.show(supportFragmentManager, "withdrawRecommed")
    }
    //检查密码是否正确
    fun checkPsw(params:String,extractDialog:WithdrawEarningsDialog){
        viewModel.verifyPsw(params).observe(this, Observer {
            if (it.status==200) {
                extract()
                extractDialog!!.dismiss()
            } else {
                ErrorCode.showErrorMsg(getActivity(), it.status)
            }
        })
    }
    //确定提取接口
    fun extract() {
//        if (UserConfig.singleton?.getAccount()==null) return
        viewModel.getGains("UYT").observe(this, Observer {
            if (it.status==200){
                ToastUtils.showShort(this, getString(R.string.extracted_successfully))
            }else{
                ErrorCode.showErrorMsg(this, it.status)
            }
        })

    }
    private  fun  getCurrentAsset(){
        viewModel.run {
            getAssetConfig().observe(this@UYTAssetActivity, Observer {
                smartRefreshLayout.finishRefresh()
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
//           getAsset().observe(this@UYTAssetActivity, androidx.lifecycle.Observer {
//               if (it.status==200){
//                   assetBean = it.data
//                        val info =
//                            AssetDetailsItemBean(AssetDetailsItemBean.TYPE_INFO, assetBean)
//                        mainDatas.add(info)
//                        val head = AssetDetailsItemBean(
//                            AssetDetailsItemBean.TYPE_HEADER,
//                            resources.getString(R.string.all)
//                        )
//                        mainDatas.add(head)
//                        getData()
//               } else{
//                   ToastUtils.showShort(getActivity(),it.errorMsg)
//               }
//           })
//
//        }

    }
//    private fun getData() {
//        viewModel.run {
//            var map = mapOf<String?, String?>(
//                "type" to chooseType,
//                "iconType" to chainName,
//                "pageNumber" to pageNumber.toString(),
//                "pageSize" to pageSize.toString()
//            )
//            getAssetDetails(map).observe(this@UYTAssetActivity, Observer {
//
//            })
//        }
//
    }
    //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean){
        smartRefreshLayout.autoRefresh()
        if (oneClick){
            uytTitle.setTextColor(resources.getColor(R.color.color_ffcf32))
            uytTitleLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView.visibility= View.VISIBLE
        }else {
            uytTitle.setTextColor(resources.getColor(R.color.white))
            uytTitleLine.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView.visibility= View.GONE
        }
        if (twoClick){
            uytTitle1.setTextColor(resources.getColor(R.color.color_ffcf32))
            uytTitleLine1.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView1.visibility= View.VISIBLE
        }else {
            uytTitle1.setTextColor(resources.getColor(R.color.white))
            uytTitleLine1.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView1.visibility= View.GONE
        }

    }

}