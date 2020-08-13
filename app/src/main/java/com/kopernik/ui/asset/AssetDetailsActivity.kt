package com.kopernik.ui.asset

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.WithdrawEarningsDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.AssetDetailsAdapter
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.AssetDetailsChildBean
import com.kopernik.ui.asset.entity.AssetDetailsItemBean
import com.kopernik.ui.asset.entity.WithdrawEarningsBean
import com.kopernik.ui.asset.viewModel.AssetDetailsViewModel
import kotlinx.android.synthetic.main.activity_asset_details.*
import kotlinx.android.synthetic.main.activity_asset_details.recyclerView
import kotlinx.android.synthetic.main.activity_asset_details.smartRefreshLayout
import kotlinx.android.synthetic.main.activity_asset_details.transferAccounts


class AssetDetailsActivity : NewBaseActivity<AssetDetailsViewModel, ViewDataBinding>() {
    companion object{

         val UYT = 1
         val BTC = 2
         val ETH = 3
         val USDT = 4
         val HT = 5
    }

    private var chainName: String = "UYT"
    private var chooseType = "ALL"
    private var adapter: AssetDetailsAdapter? = null
    private var assetBean: AssetBean? = null
    private var pageNumber = 1
    private val pageSize = 10
    private var chainType = 1
    private val mainDatas: MutableList<AssetDetailsItemBean> =
        ArrayList<AssetDetailsItemBean>()



    override fun layoutId()=R.layout.activity_asset_details

    override fun initView(savedInstanceState: Bundle?) {
        chainType = intent.getIntExtra("chainType", 1)
        setTitle(chainName + resources.getString(R.string.nav_title_asset))
    }
    //初始化数据
    override fun initData() {
        adapter = AssetDetailsAdapter(mainDatas!!)
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view.id==R.id.extraction_recommend_income){
                showDialog()
            }
        }
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            PinnedHeaderItemDecoration.Builder(1)
                .setClickIds(R.id.choose_tv)
                .disableHeaderClick(false)
                .create()
        )
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pageNumber = 1
                if (assetBean == null) {
                    getAssetAndListData()
                } else {
                    getData()
                }
            }
        })

       //收款
      collectMoney.setOnClickListener {
          UserConfig.singleton?.mnemonic?.address?.let {
              LaunchConfig.startDepositMoneyActivity(
                  this@AssetDetailsActivity,
                  chainType,
                  it
              )
          }

      }
        //转账
        transferAccounts.setOnClickListener {
            viewModel.run {
                transferaccount(chainName).observe(this@AssetDetailsActivity, Observer {
                    if (it.status == 200) {
                        LaunchConfig.startTransferAc(
                            this@AssetDetailsActivity,
                            chainType,
                            chainName
                        )
                    } else {
                        ToastUtils.showShort(this@AssetDetailsActivity, it.errorMsg)
                    }
                })
            }
        }
        //投票
        vote.setOnClickListener {
            LaunchConfig.startMyVoteAc(
                this@AssetDetailsActivity
            )
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
        if (UserConfig.singleton?.getAccount()==null) return
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
           getAsset().observe(this@AssetDetailsActivity, androidx.lifecycle.Observer {
               if (it.status==200){
                   assetBean = it.data
                        val info =
                            AssetDetailsItemBean(AssetDetailsItemBean.TYPE_INFO, assetBean)
                        mainDatas.add(info)
                        val head = AssetDetailsItemBean(
                            AssetDetailsItemBean.TYPE_HEADER,
                            resources.getString(R.string.all)
                        )
                        mainDatas.add(head)
                        getData()
               } else{
                   ToastUtils.showShort(getActivity(),it.errorMsg)
               }
           })

        }

    }
    private fun getData() {
        viewModel.run {
            var map = mapOf<String?, String?>(
                "type" to chooseType,
                "iconType" to chainName,
                "pageNumber" to pageNumber.toString(),
                "pageSize" to pageSize.toString()
            )
            getAssetDetails(map).observe(this@AssetDetailsActivity, Observer {
                val datas: List<AssetDetailsChildBean.DatasBean> = it.data.datas!!
                val existData: MutableList<AssetDetailsItemBean> =
                    ArrayList()
                existData.add(mainDatas[0])
                existData.add(mainDatas[1])
                if (pageNumber == 1) {
                    if (datas == null || datas.isEmpty()) {
                        smartRefreshLayout.finishRefreshWithNoMoreData()
                        existData.add(AssetDetailsItemBean(AssetDetailsItemBean.TYPE_EMPTY))
                        adapter!!.setNewData(existData)
                        return@Observer
                    }
                    if (datas.size == 10) {
                        smartRefreshLayout.finishRefresh(300)
                        pageNumber++
                    } else {
                        smartRefreshLayout.finishRefreshWithNoMoreData()
                    }
                    for (item in it.data.datas!!) {
                        existData.add(
                            AssetDetailsItemBean(
                                AssetDetailsItemBean.TYPE_ITEM,
                                item
                            )
                        )
                    }
                    adapter!!.setNewData(existData )
                } else {
                    if (datas.size < 10) {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        pageNumber++
                        smartRefreshLayout.finishLoadMore(true)
                    }
                    val loadMoreData: MutableList<AssetDetailsItemBean> =
                        ArrayList()
                    for (item in it.data.datas!!) {
                        loadMoreData.add(
                            AssetDetailsItemBean(
                                AssetDetailsItemBean.TYPE_ITEM,
                                item
                            )
                        )
                    }
                    adapter!!.addData(loadMoreData)
                }
            })
        }

    }


}