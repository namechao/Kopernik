package com.kopernik.ui.asset

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.asset.adapter.ToBeExtractedAdapter
import com.kopernik.ui.asset.entity.ExtractItem
import com.kopernik.ui.asset.viewModel.ToBeExtractedViewModel
import kotlinx.android.synthetic.main.activity_to_be_extracted_list.recyclerView
import kotlinx.android.synthetic.main.activity_to_be_extracted_list.smartRefreshLayout

class ToBeExtractedListActivity : NewBaseActivity<ToBeExtractedViewModel,ViewDataBinding>(),
    OnStatusChildClickListener {
    private var statusLayoutManager: StatusLayoutManager? = null
    private var chainName = ""
    private var chainType = -1
    private var pagerNumber = 1
    private var toBeExtractedAdapter: ToBeExtractedAdapter? = null
    private var availableAmount = ""
    override fun layoutId(): Int= R.layout.activity_to_be_extracted_list
    override fun initView(savedInstanceState: Bundle?) {
        chainName = intent.getStringExtra("chainName")
        chainType = intent.getIntExtra("chainType", -1)
        availableAmount = intent.getStringExtra("availableAmount")
        setTitleAndRightRes(
            resources.getString(R.string.to_be_extract_list),
            R.mipmap.asset_tf_icon,
            resources.getString(R.string.asset_transform),
            object : OnRightClickItem {
                override fun onClick() {
                    LaunchConfig.AssetTransformAc(
                        this@ToBeExtractedListActivity,
                        chainType,
                        chainName,
                        availableAmount
                    )
                }
            })
        statusLayoutManager =
            StatusLayoutHelper.getDefaultStatusManager(smartRefreshLayout, this, this)
        toBeExtractedAdapter = ToBeExtractedAdapter(arrayListOf())
        toBeExtractedAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.cancel) {

            } else if (view.id == R.id.withdrawCoin) {
                LaunchConfig.startWithdrawCoinAc(
                    this,
                    (adapter.getItem(position) as ExtractItem).id,
                    chainType,
                    (adapter.getItem(position) as ExtractItem).btcvalue
                )
            }

        }
        smartRefreshLayout.autoRefresh()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(@NonNull refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(@NonNull refreshLayout: RefreshLayout) {
                pagerNumber = 1
                getData()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = toBeExtractedAdapter
    }

    override fun initData() {
        //展示提醒弹窗
        showDialog()
    }

    //展示提醒弹窗
    private fun showDialog() {
        var dialog = AlertDialog.Builder(this).create()
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_asset_transform_tip, null)
        view.findViewById<Button>(R.id.netStep).setOnClickListener {
            dialog.dismiss()
        }
        dialog.setView(view)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        dialog.show()
//        dialog.window?.setLayout( ScreenUtils.getScreenWidth()*4/5, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    override fun onEmptyChildClick(view: View?) {
        initData()
    }

    override fun onErrorChildClick(view: View?) {
        pagerNumber = 1
        initData()
    }

    override fun onCustomerChildClick(view: View?) {

    }

    private fun getData() {
        var map = mapOf(
            "iconType" to chainName,
            "pageNumber" to pagerNumber.toString(),
            "pagerSize" to "10"
        )
        viewModel.run {
            getTobeExtractedList(map).observe(this@ToBeExtractedListActivity, Observer {
                if (it.status == 200) {
                    val datas: List<ExtractItem>? =
                        it.data.datas
                    if (pagerNumber == 1) {
                        if (datas == null || datas.isEmpty()) {
                            smartRefreshLayout.finishRefresh(300)
                            statusLayoutManager!!.showEmptyLayout()
                            smartRefreshLayout.setNoMoreData(true)
                            return@Observer
                        }
                        if (datas.size > 9) {
                            smartRefreshLayout.finishRefresh(300)
                            pagerNumber++
                        } else {
                            smartRefreshLayout.finishRefreshWithNoMoreData()
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        toBeExtractedAdapter!!.setNewData(datas)
                    } else {
                        if (datas != null) {
                            if (datas.size < 10) {
                                smartRefreshLayout.finishLoadMoreWithNoMoreData()
                            } else {
                                pagerNumber++
                                smartRefreshLayout.finishLoadMore(true)
                            }
                        }
                        statusLayoutManager!!.showSuccessLayout()
                        if (datas != null) {
                            toBeExtractedAdapter?.addData(datas)
                        }
                    }
                } else {
                    statusLayoutManager!!.showErrorLayout()
                    ErrorCode.showErrorMsg(this@ToBeExtractedListActivity, it.status)
                }
            })
        }
    }
}