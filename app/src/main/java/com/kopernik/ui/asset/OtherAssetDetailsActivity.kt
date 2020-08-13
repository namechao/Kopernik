package com.kopernik.ui.asset

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import com.oushangfeng.pinnedsectionitemdecoration.callback.OnHeaderClickListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.dialog.TxTypeDialog
import com.kopernik.app.dialog.WithdrawEarningsDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.ToastUtils
import com.kopernik.common.MapConstant
import com.kopernik.ui.asset.adapter.OtherAssetDetailsAdapter
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.AssetDetailsChildBean
import com.kopernik.ui.asset.entity.AssetDetailsItemBean
import com.kopernik.ui.asset.entity.WithdrawEarningsBean
import com.kopernik.ui.asset.viewModel.AssetDetailsViewModel
import kotlinx.android.synthetic.main.activity_other_asset_details.*


class OtherAssetDetailsActivity : NewBaseActivity<AssetDetailsViewModel, ViewDataBinding>(), TxTypeDialog.TypeChooseListener {
    companion object{
         val BTC = 2
         val ETH = 3
         val USDT = 4
         val HT = 5
    }

    private var chainName: String = ""
    private var chooseType = "ALL"
    private var adapter: OtherAssetDetailsAdapter? = null
    private var assetBean: AssetBean? = null
    private var txTypePosition = 0
    private var pageNumber = 1
    private val pageSize = 10
    private var chainType = 1
    private val mainDatas: MutableList<AssetDetailsItemBean> =
        ArrayList<AssetDetailsItemBean>()

    var assetTitle: String = ""
    override fun layoutId()=R.layout.activity_other_asset_details

    override fun initView(savedInstanceState: Bundle?) {
        chainType = intent.getIntExtra("type", 1)
        assetTitle = intent.getStringExtra("assetTitle")
        if (chainType == 2) {
            chainName = "BTC"
            setTitleAndRight(
                "U-BTC" + resources.getString(R.string.nav_title_asset),
                resources.getString(R.string.asset_details_funtion_tv)
            )
        } else if (chainType == 3) {
            chainName = "ETH"
            setTitleAndRight(
                "U-ETH" + resources.getString(R.string.nav_title_asset),
                resources.getString(R.string.asset_details_funtion_tv)
            )
        } else if (chainType == 4) {
            chainName = "USDT"
            setTitleAndRight(
                "U-USDT" + resources.getString(R.string.nav_title_asset),
                resources.getString(R.string.asset_details_funtion_tv)
            )
        } else if (chainType == 5) {
            chainName = "HT"
            setTitleAndRight(
                "U-HT" + resources.getString(R.string.nav_title_asset),
                resources.getString(R.string.asset_details_funtion_tv)
            )
        }
        //初始化下方点击按钮
        //充币
        depositCoin.setOnClickListener {
           viewModel.run {
               deposit(chainName).observe(this@OtherAssetDetailsActivity, Observer {
                   if (it.status == 200) {
                       LaunchConfig.startDepositCoinActivity(
                           this@OtherAssetDetailsActivity,
                           chainType,
                           it.data.addressHash
                       )
                   } else {
                       ToastUtils.showShort(this@OtherAssetDetailsActivity, it.errorMsg)
                   }
               })
           }
        }
        //提币
        withdrawCoin.setOnClickListener {
            viewModel.run {
                cashwithdrawal(chainName).observe(this@OtherAssetDetailsActivity, Observer {
                    if (it.status == 200) {

                        LaunchConfig.ToBeExtractedListAc(
                            this@OtherAssetDetailsActivity,
                            chainType,
                            chainName,
                            it.data.availableAmount
                        )
                    } else {
                        ToastUtils.showShort(this@OtherAssetDetailsActivity, it.errorMsg)
                    }
                })
            }
        }
        //转账
        transferAccounts.setOnClickListener {

            viewModel.run {
                transferaccount(chainName).observe(this@OtherAssetDetailsActivity, Observer {
                    if (it.status == 200) {
                        LaunchConfig.startTransferAc(
                            this@OtherAssetDetailsActivity,
                            chainType,
                            chainName
                        )
                    } else {
                        ToastUtils.showShort(this@OtherAssetDetailsActivity, it.errorMsg)
                    }
                })
            }
        }
        //映射
        mapping.setOnClickListener {
            viewModel.run {
                mapping("1", chainName).observe(this@OtherAssetDetailsActivity, Observer {
                    if (it.status == 200) {
                        LaunchConfig.startMapActivity(
                            this@OtherAssetDetailsActivity,
                            MapConstant.MAP,
                            chainType
                        )
                    } else {
                        ToastUtils.showShort(this@OtherAssetDetailsActivity, it.errorMsg)
                    }
                })
            }
        }
      //取消映射
        cancelMapping.setOnClickListener {
            viewModel.run {
                unMapping("2", chainName).observe(this@OtherAssetDetailsActivity, Observer {
                    if (it.status == 200) {
                        LaunchConfig.startMapActivity(
                            this@OtherAssetDetailsActivity,
                            MapConstant.UNMAP,
                            chainType
                        )
                    } else {
                        ToastUtils.showShort(this@OtherAssetDetailsActivity, it.errorMsg)
                    }
                })
            }
        }
    }

    override fun initData() {
        adapter = OtherAssetDetailsAdapter(mainDatas!!, chainType)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            PinnedHeaderItemDecoration.Builder(1)
                .setClickIds(R.id.choose_tv)
                .disableHeaderClick(false)
                .setHeaderClickListener(object : OnHeaderClickListener {
                    override fun onHeaderClick(
                        view: View,
                        id: Int,
                        position: Int
                    ) {
                        val dialog: TxTypeDialog = TxTypeDialog.newInstance(txTypePosition)
                        dialog.show(supportFragmentManager, "txType")
                        dialog.setListener(this@OtherAssetDetailsActivity)
                    }

                    override fun onHeaderLongClick(
                        view: View,
                        id: Int,
                        position: Int
                    ) {
                    }
                })
                .create()
        )
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.choose_tv) {
                val dialog: TxTypeDialog = TxTypeDialog.newInstance(txTypePosition)
                dialog.show(supportFragmentManager, "txType")
                dialog.setListener(this)
            } else if (view.id == R.id.extraction_mining_income) {//提取收挖矿收益
                showDialog()
            }
        }
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

    }
    //显示提取收益输入密码弹窗
    fun showDialog() {
        var wdBean = WithdrawEarningsBean()
        wdBean.title = resources.getString(R.string.extraction_mining_income)
        wdBean.oneLineLeft = resources.getString(R.string.mining_revenue)
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
    fun checkPsw(params:String,extractDialog: WithdrawEarningsDialog){
        viewModel.verifyPsw(params).observe(this, Observer {
            if (it.status==200) {
                extractDialog!!.dismiss()
            } else {
                ErrorCode.showErrorMsg(getActivity(), it.status)
            }
        })
    }
    //确定提取
    fun saveExtract() {
        viewModel.saveGains().observe(this, Observer {
            if (it.status == 200) {
                ToastUtils.showShort(
                    this@OtherAssetDetailsActivity,
                    getString(R.string.extracted_successfully)
                )
            } else {
                ErrorCode.showErrorMsg(this@OtherAssetDetailsActivity, it.status)
            }
        })
    }

    override fun functionCall() {
        LaunchConfig.startCrossChainTxAc(this, chainType)
    }

    private fun getAssetAndListData() {
        viewModel.run {
            getAsset().observe(this@OtherAssetDetailsActivity, androidx.lifecycle.Observer {
                if (it.status == 200) {
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
            var map= mapOf<String?,String?>(
                "type" to chooseType, "iconType"
                        to chainName, "pageNumber" to pageNumber.toString(),
                "pageSize" to pageSize.toString()
            )
            getAssetDetails(map).observe(this@OtherAssetDetailsActivity, Observer {
                val datas: List<AssetDetailsChildBean.DatasBean> = it.data.datas!!
                val existData: MutableList<AssetDetailsItemBean> =
                    ArrayList()
                existData.add(mainDatas[0])
                existData.add(mainDatas[1])
                if (pageNumber == 1) {
                    if (datas == null || datas.size == 0) {
                        smartRefreshLayout.finishRefreshWithNoMoreData()
                        existData.add(AssetDetailsItemBean(AssetDetailsItemBean.TYPE_EMPTY))
                        adapter!!.setNewData(existData )
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

    override fun typeChooseListener(position: Int, type: String?, typeName: String?) {
        if (txTypePosition == position) return
        txTypePosition = position
        chooseType = type!!
        pageNumber = 1
        mainDatas[1].titleName=typeName
        getData()
    }
}