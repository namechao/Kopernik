package com.kopernik.ui.asset


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ExchangeDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.asset.adapter.UTCExchangeRecordAdapter
import com.kopernik.ui.asset.adapter.UTCSynthesisRecordAdapter
import com.kopernik.ui.asset.entity.ExchangeRecord
import com.kopernik.ui.asset.entity.UtcComRecord
import com.kopernik.ui.asset.viewModel.UTCAssetViewModel
import com.kopernik.ui.mine.entity.AllConfigEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import dev.utils.common.encrypt.MD5Utils
import kotlinx.android.synthetic.main.activity_utc_asset.*
import kotlinx.android.synthetic.main.activity_utc_asset.recyclerView
import kotlinx.android.synthetic.main.activity_utc_asset.recyclerView1
import kotlinx.android.synthetic.main.activity_utc_asset.smartRefreshLayout

class UTCAssetActivity : NewFullScreenBaseActivity<UTCAssetViewModel, ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_utc_asset
    private var machinngType=0
    private var pager=1
    private var pager1=1
    private var allConfigEntity:AllConfigEntity?=null
    private var isFirst=true
    var adpter=UTCSynthesisRecordAdapter(arrayListOf())
    var adpter1=UTCExchangeRecordAdapter(arrayListOf())

    override fun initView(savedInstanceState: Bundle?) {
        intent.getStringExtra("asset")?.let {
            assetTotal.text=BigDecimalUtils.roundDOWN(it,2)
        }
        llTitle.setOnClickListener {
            machinngType=0
          onTabOnClick(true,false)
        }
        llTitle1.setOnClickListener {
            machinngType=1
            onTabOnClick(false,true)
        }
        ivBack.setOnClickListener {
            finish()
        }
        recyclerView.layoutManager=LinearLayoutManager(this)
        adpter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_synthesis_record_head,null))
        recyclerView.adapter=adpter
        recyclerView1.layoutManager=LinearLayoutManager(this)
        adpter1.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_exchange_record_head,null))
        recyclerView1.adapter=adpter1
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
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pager=1
                pager1=1
                getList()
                if (isFirst){
                    isFirst=false
                }else{
                getCurrentAsset()
                }
            }

        })
        smartRefreshLayout.autoRefresh()

    }
      fun getConfig(){
         viewModel.run {
             getConfig().observe(this@UTCAssetActivity, Observer {
                 if (it.status==200){
                     allConfigEntity=it.data
                     if (allConfigEntity!=null){
                         var exchangeDialog = ExchangeDialog.newInstance(allConfigEntity!!)
                         exchangeDialog!!.setOnRequestListener(object : ExchangeDialog.RequestListener {
                             override fun onRequest(utcCounts:String,uytCounts: String, params: String,rate:String) {
                                 exchangeCoin(utcCounts,uytCounts,params,rate)
                             }
                         })
                         exchangeDialog!!.show(supportFragmentManager, "withdrawRecommed")
                     }

                 }else{
                     ErrorCode.showErrorMsg(this@UTCAssetActivity,it.status)
                 }
             })
         }
      }

     private  fun  getCurrentAsset(){
         viewModel.run {
             getAssetConfig().observe(this@UTCAssetActivity, Observer {
                 if (it.status==200){
                     allConfigEntity=it.data
                     assetTotal.text=BigDecimalUtils.roundDOWN(it.data.utc,2)
                 }
             })
         }
     }
    private fun exchangeCoin(utccounts:String,uytCounts: String, psw: String,rate:String) {

        viewModel.run {
            var map= mapOf("amountUtc" to utccounts,"amountUyt" to uytCounts,"rate" to rate ,"pwd" to MD5Utils.md5(MD5Utils.md5(psw)))
            exchange(map).observe(this@UTCAssetActivity, Observer {
                if (it.status==200){
                  ToastUtils.showShort(this@UTCAssetActivity,resources.getString(R.string.exchange_success))
                }else{
                    ErrorCode.showErrorMsg(this@UTCAssetActivity,it.status)
                }
            })
        }

    }



    fun getList(){
      viewModel.run {
          if (machinngType==0) {
              var map = mapOf("pageNumber" to pager.toString(),"pageSize" to "10")
              composeRecord(map).observe(this@UTCAssetActivity, Observer {

                  if (it.status==200){
                      val datas: List<UtcComRecord>?=it.data.datas
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
              var map = mapOf("pageNumber" to pager1.toString(),"pageSize" to "10")
              exchangeRecord(map).observe(this@UTCAssetActivity, Observer {

                  if (it.status==200){
                      val datas: List<ExchangeRecord>?=it.data.datas
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
          }

      }


    }
    //按钮点击
    fun onTabOnClick(oneClick:Boolean,twoClick:Boolean){
        smartRefreshLayout.autoRefresh()
        if (oneClick){
            utcTitle.setTextColor(resources.getColor(R.color.color_ffcf32))
            utcTitleLine.visibility=View.VISIBLE
            recyclerView.visibility=View.VISIBLE
        }else {
            utcTitle.setTextColor(resources.getColor(R.color.white))
            utcTitleLine.visibility=View.GONE
            recyclerView.visibility=View.GONE
        }
        if (twoClick){
            utcTitle1.setTextColor(resources.getColor(R.color.color_ffcf32))
            utcTitleLine1.visibility=View.VISIBLE
            recyclerView1.visibility=View.VISIBLE
        }else {
            utcTitle1.setTextColor(resources.getColor(R.color.white))
            utcTitleLine1.visibility=View.GONE
            recyclerView1.visibility=View.GONE
        }

    }

    override fun initData() {


    }

}