package com.kopernik.ui.home.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ReminderDialog
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.app.utils.TimeUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.home.Entity.HomeCoinItem
import com.kopernik.ui.home.Entity.HomeEntity
import com.kopernik.ui.home.ViewModel.HomeViewModel
import com.kopernik.ui.home.adadpter.AutoPollAdapter
import com.kopernik.ui.home.adadpter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my.*


/**
 *
 * @ProjectName:    UYT
 * @Package:        com.kopernik.ui.home.fragment
 * @ClassName:      HomeFragment
 * @Description:     ja5va类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/8 2:35 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/8 2:35 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open class HomeFragment: NewBaseFragment<HomeViewModel, ViewDataBinding>() {

    companion object{
        fun newInstance() = HomeFragment()

    }


    var homeEntity: HomeEntity?=null
    var adapter= HomeAdapter()

    var list= ArrayList<HomeCoinItem>()
    override fun layoutId()= R.layout.fragment_home
    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(savedInstanceState: Bundle?) {
        banner.setOnClickListener {
            LaunchConfig.startInviteFriendsActivity(activity!!)
        }

        //悬浮按钮
        ivFloat.setOnClickListener {
            getUtkStatus()


        }

        val translateAnimation = TranslateAnimation(0f, 0f, 80f, 50f)
        translateAnimation.duration = 1000
        translateAnimation.repeatCount = Animation.INFINITE
        translateAnimation.repeatMode = Animation.REVERSE
        clGold.animation = translateAnimation
        translateAnimation.start()
        initData()
    }
    fun getUtkStatus(){
     viewModel.run {
         getUtkStatus().observe(this@HomeFragment, Observer {
             if (it.status==200) {
                 // 1.需要uyt 2.需要utc  的数量 才允许 用户领取utk
                 // 2.switchReceive 代表当前是否可以领取 utk
                 var hour = TimeUtils.getHour().toInt()
                 if (it.data.flag == 2) {
                     ToastUtils.showShort(activity, getString(R.string.recvice_error))
                 } else if (hour < it.data.config.start || hour > it.data.config.end) {
                     ToastUtils.showShort(
                         activity,
                         "UKT" + "${getString(R.string.recvice_time_tip)}" + "${it.data.config.start}-${it.data.config.end}"
                     )
                 } else {
//                 弹窗
                     activity?.let { it1 ->
                         ReminderDialog(it1,it.data)
                             .setCancelable(true)
                             .setOnRequestListener(object :ReminderDialog.RequestListener{
                                 override fun onRequest() {
                                     getUtk()
                                 }
                             })
                             .show()
                     }
                 }
             }else{
                 ErrorCode.showErrorMsg(activity,it.status)
             }
         })
     }
    }
    private fun getUtk() {
        var uid=""
        UserConfig.singleton?.accountBean?.uid?.let {
          uid=it
        }
        var map= mapOf("uid" to uid)
        viewModel.getUtk(map).observe(this, Observer {
            if (it.status==200)
                ToastUtils.showShort(activity, getString(R.string.recvice_success))
                 else ErrorCode.showErrorMsg(activity,it.status)
        })
    }
    private fun initData() {

        //下方列表
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter=adapter
        smartRefreshLayout.setOnRefreshListener {
            getData()

            getNotice()
        }
        smartRefreshLayout.autoRefresh()
    }
  fun  getData(){
      viewModel.run {
          getHomeList().observe(this@HomeFragment, Observer {
              smartRefreshLayout.finishRefresh()
            if (it.status==200){
                homeEntity=it.data
                try {
                    updataUI()
                }catch (e :Exception){
                    e.stackTrace
                }

            }  else{
                ErrorCode.showErrorMsg(activity,it.status)
            }
          })
      }
  }
  fun updataUI(){
       homeEntity?.collectList?.let {
           if (it.size>0) {
               var autoPollAdapter = AutoPollAdapter(activity!!,it)
               //上方滚动
               noticeRecyclerView.layoutManager =
                   LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
               noticeRecyclerView.adapter = autoPollAdapter
               //启动滚动
               noticeRecyclerView.start()
           }
      }

      //总通量
      tvUtdm.text=BigDecimalUtils.roundDOWN(homeEntity?.amountList?.utdmAmount,2)
      tvUtk.text=BigDecimalUtils.roundDOWN(homeEntity?.amountList?.utkAmount,2)
      tvUtc.text=BigDecimalUtils.roundDOWN(homeEntity?.amountList?.utcAmount,2)
      tvUyt.text=BigDecimalUtils.roundDOWN(homeEntity?.amountList?.uytAmount,2)
      allCoinCounts
          .text= BigDecimalUtils.roundDOWN(homeEntity?.amountList?.utdmAmount,2)
      list.clear()
      list.add( HomeCoinItem("UTC","/USDT",
          BigDecimalUtils.roundDOWN( homeEntity?.priceConfig?.utcPrice!!,4),
        "¥ ${BigDecimalUtils.multiply(homeEntity?.priceConfig?.utcPrice,homeEntity?.price?.cnyPrice,4)}",
          homeEntity?.priceConfig?.utcPricePercentage!!))
      list.add( HomeCoinItem("UTK","/USDT",
          BigDecimalUtils.roundDOWN( homeEntity?.priceConfig?.utkPrice!!,4),
          "¥ ${BigDecimalUtils.multiply(homeEntity?.priceConfig?.utkPrice,homeEntity?.price?.cnyPrice,4)}",
          homeEntity?.priceConfig?.utkPricePercentage!!))
      list.add( HomeCoinItem("BTC","/USDT",
          BigDecimalUtils.roundDOWN( homeEntity?.price?.btcPrice!!,4),
          "¥ ${BigDecimalUtils.multiply(homeEntity?.price?.btcPrice,homeEntity?.price?.cnyPrice,4)}",
          homeEntity?.price?.btcPricePercentage!!))
      list.add( HomeCoinItem("ETH","/USDT",
          BigDecimalUtils.roundDOWN(  homeEntity?.price?.ethPrice!!,4),
          "¥ ${BigDecimalUtils.multiply(homeEntity?.price?.ethPrice,homeEntity?.price?.cnyPrice,4)}",
          homeEntity?.price?.ethPricePercentage!!))
//      list.add( HomeCoinItem("UYT","/BTC",
//          BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.btcDollarPrice!!,1),
//          "¥ ${ BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.btcRmbrPrice!!,1)}",homeEntity?.priceTradingPair?.btcPercentage!!))
//      list.add( HomeCoinItem("UYT","/ETH",
//          BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.ethDollarPrice!!,4),
//          "¥ ${BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.ethRmbrPrice!!,4)}",homeEntity?.priceTradingPair?.ethPercentage!!))
      list.add( HomeCoinItem("UYT","/USDT",
          BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.usdtDollarPrice!!,4),
          "¥ ${BigDecimalUtils.roundDOWN(  homeEntity?.priceTradingPair?.usdtRmbrPrice!!,4)}",homeEntity?.priceTradingPair?.usdtPercentage!!))
      adapter.setNewData(list)
//      //金币图标
      if (homeEntity?.machList!=null) {
          for (i in homeEntity?.machList!!.indices){
              if (i==0) {
                  homeEntity?.machList!![0].phone?.let {
                      if (it.length > 5) {
                          tvPhone.text = "${it.subSequence(
                              0,
                              3
                          )}****${it.subSequence(it.length - 4, it.length)}"
                      }
                  }
                  homeEntity?.machList!![0].email?.let {
                          tvPhone.text = it
                  }
                  when ( homeEntity?.machList!![0].type) {
                      1 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type1)
                      }
                      2 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type2)
                      }
                      3 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type3)
                      }
                      4 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type4)
                      }
                      5 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type5)
                      }
                      6 -> {
                          tvMineType?.text = getString(R.string.mining_machine_type6)
                      }
                  }
              }else if(i==1){
                  homeEntity?.machList!![1].phone?.let {
                      if (it.length > 5) {
                          tvPhone1.text = "${it.subSequence(
                              0,
                              3
                          )}****${it.subSequence(it.length - 4, it.length)}"
                      }
                  }
                  homeEntity?.machList!![1].email?.let {
                      tvPhone.text = it
                  }
                  when ( homeEntity?.machList!![1].type) {
                      1 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type1)
                      }
                      2 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type2)
                      }
                      3 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type3)
                      }
                      4 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type4)
                      }
                      5 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type5)
                      }
                      6 -> {
                          tvMineType1?.text = getString(R.string.mining_machine_type6)
                      }
                  }
              }else if (i==3){
                  homeEntity?.machList!![2].phone?.let {
                      if (it.length > 5) {
                          tvPhone2.text = "${it.subSequence(
                              0,
                              3
                          )}****${it.subSequence(it.length - 4, it.length)}"
                      }
                  }
                  homeEntity?.machList!![2].email?.let {
                      tvPhone.text = it
                  }
                  when ( homeEntity?.machList!![2].type) {
                      1 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type1)
                      }
                      2 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type2)
                      }
                      3 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type3)
                      }
                      4 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type4)
                      }
                      5 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type5)
                      }
                      6 -> {
                          tvMineType2?.text = getString(R.string.mining_machine_type6)
                      }
                  }
              }

          }
      }
  }
    //公告通知
    fun getNotice(){
        viewModel.run {
            getNotice().observe(this@HomeFragment, Observer {
                try {

                    if (it?.data?.notice != null && it?.data?.notice?.size > 0) {
                        for (i in it?.data?.notice) {
                            val view: View = layoutInflater.inflate(R.layout.item_notice, null)
                            var content = view.findViewById<TextView>(R.id.content)
                            content.text = i.title
                            vfNoticeScroll.addView(view)
                            view.setOnClickListener {
                                i.id?.let {
                                    LaunchConfig.startNoticeActivity(activity!!, it)
                                }

                            }
                        }
                        vfNoticeScroll.setFlipInterval(4000)
                        vfNoticeScroll.startFlipping()
                    }
                }catch (e:Exception){
                    e.stackTrace
                }
            })
        }
    }
}