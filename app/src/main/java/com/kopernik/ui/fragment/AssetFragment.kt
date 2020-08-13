package com.kopernik.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.CompoundButton
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ReminderDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.AssetDetailsActivity
import com.kopernik.ui.asset.OtherAssetDetailsActivity
import com.kopernik.ui.asset.adapter.AssetAdapter
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.entity.AssetItemBean
import com.kopernik.ui.asset.viewModel.AssetViewModel
import kotlinx.android.synthetic.main.fragment_asset.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal
import kotlin.collections.ArrayList


class AssetFragment : BaseFragment<AssetViewModel,ViewDataBinding>() {
    private var assetAdapter: AssetAdapter? = null
    private var assetData: AssetBean? = null
    private var assetShow = true
    private var isFragmentShow = true
    private var mEventBus=EventBus.getDefault()
    companion object{
        fun newInstance() = AssetFragment()
    }
    override fun layoutId() =R.layout.fragment_asset

    //5s 刷新一次资产
    var handler = Handler()
    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (UserConfig.singleton?.getAccount() == null) {
                initUnLoginData()
            } else if (isFragmentShow && UserConfig.singleton?.getAccount() != null && assetShow
            ) {
                getAsset()
            }
            handler.postDelayed(this, 5000)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        userIv.setOnClickListener(View.OnClickListener { v: View? ->
            mEventBus.post(
                LocalEvent<Any>(LocalEvent.openSetting)
            )
        })
        assetAdapter = AssetAdapter(listOf())
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = assetAdapter
        recyclerView.addItemDecoration(
            PinnedHeaderItemDecoration.
            Builder(1).create()
        )
        //配置是资产显示
        if (UserConfig.singleton?.getAccount() == null) {
            initUnLoginData()
        } else {
            assetShow = UserConfig.singleton!!.assetShow
            eyeCb.isChecked = assetShow
            assetShow(assetShow)
        }

        eyeCb.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            UserConfig.singleton?.assetShow=isChecked
            if (UserConfig.singleton?.getAccount() ==null) return@setOnCheckedChangeListener
            assetShow = isChecked
            checkShow(isChecked)
        }
        assetAdapter?.setOnItemClickListener{ adapter, view, position ->
            if ((adapter.getData()
                    .get(position) as AssetItemBean).getItemType() === AssetItemBean.TYPE_DATA
            ) {
                if (UserConfig.singleton?.getAccount() == null || assetData==null) return@setOnItemClickListener
                val title: String? =
                    (adapter.data[position] as AssetItemBean).tokenName
                title?.let {
                    //点击进入资产详细界面
                    when {
                        it == "UYT" -> {
                            LaunchConfig.startAssetDetailsAc(
                                activity!!,
                                AssetDetailsActivity.UYT,
                                title
                            )
                        }
                        it == "U-BTC" -> {
                            LaunchConfig.startOtherAssetDetailsAc(
                                activity!!,
                                OtherAssetDetailsActivity.BTC,
                                title
                            )
                        }
                        it.contains("U-ETH") -> {
                            LaunchConfig.startOtherAssetDetailsAc(
                                activity!!,
                                OtherAssetDetailsActivity.ETH,
                                title
                            )
                        }
                        it == "U-USDT" -> {
                            LaunchConfig.startOtherAssetDetailsAc(
                                activity!!,
                                OtherAssetDetailsActivity.USDT,
                                title
                            )
                        }
                        it == "U-HT" -> {
                            LaunchConfig.startOtherAssetDetailsAc(
                                activity!!,
                                OtherAssetDetailsActivity.HT,
                                title
                            )
                        }
                    }
                }
            }
        }
        handler.postDelayed(runnable, 5000)
        activity?.let {
            ReminderDialog(it)
                .setCancelable(false)
                .setTitle(this.getString(R.string.export_reminder))
                .setMsg(this.getString(R.string.export_reminder_content))
                .setButton(this.getString(R.string.i_remember), null)
                .show()
        }
    }





///BTC:8位   ETH：8位    USDT 6位    UYT 4位    CNY  2位     百分比的数据：2位  
//UYT 委托数量 4位
  fun getAsset() {
        viewModel.run {
            getAsset().observe(this@AssetFragment, Observer {
                //如果闭眼就就返回
              if (it.status==200){
                  assetData = it.data
                  if(!assetShow) return@Observer
                  setData(it.data)
              } else {
                      ErrorCode.showErrorMsg(activity,it.status)
              }
            })
        }

}

 fun initUnLoginData() {
     val datas: MutableList<AssetItemBean> = ArrayList<AssetItemBean>()
     datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, this.getString(R.string.title_asset_dns)))
     //UYT
     val uytItem = AssetItemBean(AssetItemBean.TYPE_DATA)
     uytItem.tokenName = "UYT"
     uytItem.balance = "--"
     uytItem.freeze = "--"
     uytItem.cny = "--"
     uytItem.logoResId = R.mipmap.dns
     datas.add(uytItem)
     datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, getString(R.string.title_cross_asset)))
     //D-BTC
     val btcItem = AssetItemBean(AssetItemBean.TYPE_DATA)
     btcItem.tokenName = "U-BTC"
     btcItem.balance = "--"
     btcItem.freeze = "--"
     btcItem.cny = "--"
     btcItem.logoResId = R.mipmap.btc
     datas.add(btcItem)
     //D-ETH
    val ethItem = AssetItemBean(AssetItemBean.TYPE_DATA)
    ethItem.tokenName="U-ETH"
    ethItem.balance="--"
    ethItem.freeze="--"
    ethItem.cny="--"
    ethItem.logoResId=R.mipmap.eth
    datas.add(ethItem)
    //D-USDT
    val usdtItem = AssetItemBean(AssetItemBean.TYPE_DATA)
    usdtItem.tokenName="U-USDT"
    usdtItem.balance="--"
    usdtItem.freeze="--"
    usdtItem.cny="--"
    usdtItem.logoResId=R.mipmap.erc
    datas.add(usdtItem)
    //HT
    val htItem = AssetItemBean(AssetItemBean.TYPE_DATA)
    htItem.tokenName="U-HT"
    htItem.balance="--"
    htItem.freeze="--"
    htItem.cny="--"
    htItem.logoResId=R.mipmap.ht_icon
    datas.add(htItem)
    assetAdapter?.setNewData(datas as List<AssetItemBean>)
     assetSumTv.text = "--"
}

    //点击眼睛按钮时走的方法
    fun checkShow(b: Boolean) {
        if (b) {
            assetData?.let {
                setData(it)
            }
        } else {
            assetHide()
        }
    }

    //显示数据
    private fun assetShow(b: Boolean) {
        getAsset()
        if (b) {
            assetNoData()
        } else {
            assetHide()
        }
    }

    //隐藏数据
    private fun assetHide() {
        //          if (assetBean == null) return
        val datas: MutableList<AssetItemBean> =
            ArrayList<AssetItemBean>()
        datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, getString(R.string.title_asset_dns)))
        //UYT
        val uytItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        uytItem.tokenName = "UYT"
        uytItem.balance = "*****"
        uytItem.freeze = "*****"
        uytItem.cny = "*****"
        uytItem.logoResId = R.mipmap.dns
        datas.add(uytItem)
        datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, getString(R.string.title_cross_asset)))
        //D-BTC
        val btcItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        btcItem.tokenName = "U-BTC"
        btcItem.balance = "*****"
        btcItem.freeze = "*****"
        btcItem.cny = "*****"
        btcItem.logoResId = R.mipmap.btc
        datas.add(btcItem)
        //D-ETH
        val ethItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        ethItem.tokenName = "U-ETH"
        ethItem.balance = "*****"
        ethItem.freeze = "*****"
        ethItem.cny = "*****"
        ethItem.logoResId = R.mipmap.eth
        datas.add(ethItem)
        //D-USDT
        val usdtItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        usdtItem.tokenName = "U-USDT"
        usdtItem.balance = "*****"
        usdtItem.freeze = "*****"
        usdtItem.cny = "*****"
        usdtItem.logoResId = R.mipmap.erc
        datas.add(usdtItem)
        //ht
        val htItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        htItem.tokenName = "U-HT"
        htItem.balance = "*****"
        htItem.freeze = "*****"
        htItem.cny = "*****"
        htItem.logoResId = R.mipmap.ht_icon
        datas.add(htItem)
        assetAdapter?.setNewData(datas)
        assetSumTv.setText("***")
    }


    //由于初始化json太慢所以初始化一个页面
    fun assetNoData(){
        if (assetData!=null) return
        val datas: MutableList<AssetItemBean> =
            ArrayList<AssetItemBean>()
        datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, getString(R.string.title_asset_dns)))
        //UYT
        val uytItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        uytItem.tokenName = "UYT"
        uytItem.balance = "0.0000"
        uytItem.freeze = "0.00000000"
        uytItem.cny = "≈ 0.00 CNY"
        uytItem.logoResId = R.mipmap.dns
        datas.add(uytItem)
        datas.add(AssetItemBean(AssetItemBean.TYPE_HEADER, getString(R.string.title_cross_asset)))
        //D-BTC
        val btcItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        btcItem.tokenName = "U-BTC"
        btcItem.balance = "0.00000000"
        btcItem.freeze = "0.00000000"
        btcItem.cny = "≈ 0.00 CNY"
        btcItem.logoResId = R.mipmap.btc
        datas.add(btcItem)
        //D-ETH
        val ethItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        ethItem.tokenName = "U-ETH"
        ethItem.balance = "0.00000000"
        ethItem.freeze = "0.00000000"
        ethItem.cny = "≈ 0.00 CNY"
        ethItem.logoResId = R.mipmap.eth
        datas.add(ethItem)
        //D-USDT
        val usdtItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        usdtItem.tokenName = "U-USDT"
        usdtItem.balance = "0.000000"
        usdtItem.freeze = "0.00000000"
        usdtItem.cny = "≈ 0.00 CNY"
        usdtItem.logoResId = R.mipmap.erc
        datas.add(usdtItem)
        //ht
        val htItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        htItem.tokenName = "U-HT"
        htItem.balance = "0.000000"
        htItem.freeze = "0.00000000"
        htItem.cny = "≈ 0.00 CNY"
        htItem.logoResId = R.mipmap.ht_icon
        datas.add(htItem)
        assetAdapter?.setNewData(datas)
        assetSumTv.text = "0.00"


    }

    //设置数据通用类
    private fun setData(assetBean: AssetBean) {
        val datas: MutableList<AssetItemBean> =
            java.util.ArrayList()
        //UYT资产数据重组
        datas.add(
            AssetItemBean(
                AssetItemBean.TYPE_HEADER,
                getString(R.string.title_asset_dns)
            )
        )

        val uytItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        //币名
        uytItem?.tokenName = "UYT"
        //可用余额
        uytItem.balance = (
                BigDecimalUtils.roundDOWN(
                    assetBean?.capitalUyt?.balance, 4
                )
                )
        //总冻结=转账+投票
        uytItem.freeze =
            BigDecimal(assetBean?.capitalUyt?.transactionFreeze!!)
                .add(BigDecimal(assetBean?.capitalUyt?.voteFreeze!!)).toPlainString()
        //可用约等于人民币
        uytItem?.cny =
            "≈ " + BigDecimalUtils.roundDOWN(assetBean?.dnsCny, 2).toString() + " CNY"
        //图标
        uytItem.logoResId = R.mipmap.dns
        datas.add(uytItem)
        //跨链资产交易额度
        datas.add(
            AssetItemBean(
                AssetItemBean.TYPE_HEADER,
                getString(R.string.title_cross_asset)
            )
        )
        //D-BTC提现+转账+挖矿
        val btcItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        btcItem.tokenName="U-BTC"
        btcItem.balance=
            BigDecimalUtils.roundDOWN(
                assetBean?.capitalBtc?.balance, 8
            )

        btcItem?.freeze=
            BigDecimal(assetBean?.capitalBtc?.withdrawalFreeze!!)
                .add(BigDecimal(assetBean?.capitalBtc?.transactionFreeze))
                .add(BigDecimal(assetBean?.btcmining)
                ).toPlainString()

        btcItem.cny=
            "≈ " + BigDecimalUtils.roundDOWN(assetBean?.btcCny, 2).toString() + " CNY"

        btcItem.logoResId=R.mipmap.btc
        datas.add(btcItem)
        //D-ETH
        val ethItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        ethItem.tokenName="U-ETH"
        ethItem.balance=
            BigDecimalUtils.roundDOWN(
                assetBean?.capitalEth?.balance, 8
            )

        ethItem?.freeze =
            BigDecimal(assetBean?.capitalEth?.withdrawalFreeze!!)
                .add(BigDecimal(assetBean?.capitalEth?.transactionFreeze))
                .add(BigDecimal(assetBean?.ethmining)).toPlainString()

        ethItem.cny=
            "≈ " + BigDecimalUtils.roundDOWN(assetBean?.ethCny, 2).toString() + " CNY"

        ethItem.logoResId=R.mipmap.eth

        datas.add(ethItem)
        //D-USDT
        val usdtItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        usdtItem.tokenName="U-USDT"
        usdtItem.balance=
            BigDecimalUtils.roundDOWN(
                assetBean?.capitalUsdt?.balance, 6
            )

        usdtItem.freeze=
            BigDecimalUtils.roundDOWN(
                BigDecimal(assetBean?.capitalUsdt?.withdrawalFreeze!!)
                    .add(BigDecimal(assetBean?.capitalUsdt?.transactionFreeze))
                    .add(BigDecimal(assetBean?.usdtmining)).toPlainString(),
                6
            )

        usdtItem.cny=
            "≈ " + BigDecimalUtils.roundDOWN(assetBean?.usdtCny, 2)
                .toString() + " CNY"

        usdtItem.logoResId=R.mipmap.erc
        datas.add(usdtItem)
        //HT
        val htItem = AssetItemBean(AssetItemBean.TYPE_DATA)
        htItem.tokenName="U-HT"
        htItem.balance=
            BigDecimalUtils.roundDOWN(
                assetBean?.capitalHt?.balance, 6
            )

        htItem.freeze=
            BigDecimalUtils.roundDOWN(
                BigDecimal(assetBean?.capitalHt?.withdrawalFreeze!!)
                    .add(BigDecimal(assetBean?.capitalHt?.transactionFreeze!!))
                    .add(BigDecimal(assetBean?.htmining)).toPlainString(), 6
            )

        htItem.cny=
            "≈ " + BigDecimalUtils.roundDOWN(assetBean?.htCny, 2).toString() + " CNY"
        htItem.logoResId=R.mipmap.ht_icon
        datas.add(htItem)
        assetAdapter?.setNewData(datas)
        assetSumTv.setText(BigDecimalUtils.roundDOWN(assetBean?.countCny, 2))
    }

    override fun onResume() {
            super.onResume()
            if (UserConfig.singleton?.getAccount()!= null &&
                UserConfig.singleton?.assetShow != assetShow) {
                assetShow = UserConfig?.singleton?.assetShow!!
                eyeCb.isChecked = assetShow;
                assetShow(assetShow);
            }
            isFragmentShow = true;
        }
    //界面销毁时清除handler
    override  fun onDestroy() {
        super.onDestroy()
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable)
            handler.removeCallbacksAndMessages(null)
        }
    }
    //隐藏时
   override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    isFragmentShow = !hidden
       getAsset()
  }

    override fun onPause() {
        super.onPause()
        isFragmentShow = false
    }
    //注册evenbus
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!mEventBus.isRegistered(this)){
            mEventBus.register(this)
        }
    }

    @Subscribe(
        sticky = true,
        threadMode = ThreadMode.MAIN
    )
    fun onEvent(event: LocalEvent<Any>) {
    }
    //注销evenbus
    override fun onDestroyView() {
        super.onDestroyView()
        if (mEventBus.isRegistered(this)){
            mEventBus.unregister(this)
        }
    }
}