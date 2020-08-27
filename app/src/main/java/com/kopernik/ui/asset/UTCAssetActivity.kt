package com.kopernik.ui.asset


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.ui.asset.adapter.UTCExchangeRecordAdapter
import com.kopernik.ui.asset.adapter.UTCSynthesisRecordAdapter
import com.kopernik.ui.asset.entity.UtcComRecord
import com.kopernik.ui.asset.viewModel.UTCAssetViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_utc_asset.*
import kotlinx.android.synthetic.main.activity_utc_asset.recyclerView
import kotlinx.android.synthetic.main.activity_utc_asset.recyclerView1
import kotlinx.android.synthetic.main.activity_utc_asset.smartRefreshLayout

class UTCAssetActivity : NewFullScreenBaseActivity<UTCAssetViewModel, ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_utc_asset
    private var machinngType=0
    private var pager=1
    private var pager1=1
    var adpter=UTCSynthesisRecordAdapter(arrayListOf())
    var adpter1=UTCExchangeRecordAdapter(arrayListOf())

    override fun initView(savedInstanceState: Bundle?) {
        llTitle.setOnClickListener {
            machinngType=0
          onTabOnClick(true,false)
        }
        llTitle1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true)
        }

        recyclerView.layoutManager=LinearLayoutManager(this)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_synthesis_record_head,null))
        recyclerView.adapter=adpter
        recyclerView1.layoutManager=LinearLayoutManager(this)
        adpter1.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_exchange_record_head,null))
        recyclerView1.adapter=adpter1
        ///兑换
        exchange.setOnClickListener {
            viewModel.run {
              getAssetConfig().observe(this@UTCAssetActivity, Observer {
                  if (it.status==200){
                      var exchangeDialog = ExchangeDialog.newInstance(1)
                      exchangeDialog!!.setOnRequestListener(object : ExchangeDialog.RequestListener {
                          override fun onRequest(type: Int, params: String) {

                          }
                      })
                      exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
                  }else{
                      ErrorCode.showErrorMsg(this@UTCAssetActivity,it.status)
                  }
              })
            }

        }
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager=1
                pager1=1
                getList()
            }

        })
        smartRefreshLayout.autoRefresh()

    }

    fun getList(){
      viewModel.run {
          if (machinngType==0) {
              var map = mapOf("" to "")
              composeRecord(map).observe(this@UTCAssetActivity, Observer {

                  if (it.status==200){
                      val datas: List<UtcComRecord>?=it.data.datas
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

          }

      }


    }
    //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean){
        smartRefreshLayout.autoRefresh()
        if (oneClick){
            utcTitle.setTextColor(resources.getColor(R.color.color_ffcf32))
            utcTitleLine.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView.visibility=View.VISIBLE
        }else {
            utcTitle.setTextColor(resources.getColor(R.color.white))
            utcTitleLine.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView.visibility=View.GONE
        }
        if (twoClick){
            utcTitle1.setTextColor(resources.getColor(R.color.color_ffcf32))
            utcTitleLine1.setBackgroundColor(resources.getColor(R.color.color_ffcf32))
            recyclerView1.visibility=View.VISIBLE
        }else {
            utcTitle1.setTextColor(resources.getColor(R.color.white))
            utcTitleLine1.setBackgroundColor(resources.getColor(R.color.white))
            recyclerView1.visibility=View.GONE
        }

    }

    override fun initData() {


    }

}