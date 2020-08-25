package com.kopernik.ui.asset

import android.os.Bundle
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
import com.kopernik.app.dialog.WithdrawEarningsDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.UYTDepositWithdrawlAssetAdapter
import com.kopernik.ui.asset.adapter.UYTTransferAssetAdapter

import com.kopernik.ui.asset.entity.AssetDetailsItemBean
import com.kopernik.ui.asset.entity.WithdrawEarningsBean
import com.kopernik.ui.asset.viewModel.AssetDetailsViewModel
import kotlinx.android.synthetic.main.activity_uyt_asset.*


class UYTAssetActivity : NewFullScreenBaseActivity<AssetDetailsViewModel, ViewDataBinding>() {
    companion object{

         val UYT = 1
         val BTC = 2
         val ETH = 3
         val USDT = 4
         val HT = 5
    }

    private var chainName: String = "UYT"
    private var chooseType = "ALL"
    private var adapter: UYTDepositWithdrawlAssetAdapter? = null
    private var pageNumber = 1
    private val pageSize = 10
    private var chainType = 1
    private val mainDatas: MutableList<AssetDetailsItemBean> =
        ArrayList<AssetDetailsItemBean>()



    override fun layoutId()=R.layout.activity_uyt_asset

    override fun initView(savedInstanceState: Bundle?) {

    }
    //初始化数据
    override fun initData() {
        var list= arrayListOf("gadgg","asfagadga","asfafas","asagagasgadgdsfdsfasfa")
        adapter = UYTDepositWithdrawlAssetAdapter(list)
        adapter?.setOnItemChildClickListener { adapter, view, position ->

        }

        recyclerView.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        var adapter1 = UYTTransferAssetAdapter(list)
        adapter?.setOnItemChildClickListener { adapter, view, position ->

        }
        recyclerView1.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView1.adapter = adapter1
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
//                getData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pageNumber = 1
//                if (assetBean == null) {
////                    getAssetAndListData()
//                } else {
////                    getData()
//                }
            }
        })

       //充币
        tvDepositCoin.setOnClickListener {

              LaunchConfig.startDepositMoneyActivity(
                  this@UYTAssetActivity,
                  ""
              )

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
//                        LaunchConfig.startTransferAc(
//                            this@UYTAssetActivity,
//                            chainType,
//                            chainName
//                        )
//                    } else {
//                        ToastUtils.showShort(this@UYTAssetActivity, it.errorMsg)
//                    }
//                })
            }
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


    //获取资产和下边记录列表
    private fun getAssetAndListData() {
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


}