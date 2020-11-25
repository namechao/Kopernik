package com.kopernik.ui.asset


import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.ui.asset.adapter.ChoseCoinAdapter
import com.kopernik.ui.asset.entity.AssetItemEntity
import com.kopernik.ui.asset.entity.CoinType
import com.kopernik.ui.asset.viewModel.UTCAssetViewModel
import kotlinx.android.synthetic.main.activity_chose_coin_type.*

class ChoseCoinTypeActivity : NewFullScreenBaseActivity<UTCAssetViewModel, ViewDataBinding>() {
    override fun layoutId()=R.layout.activity_chose_coin_type

   var choseType="1"
    var data=ArrayList<CoinType>()
    override fun initView(savedInstanceState: Bundle?) {
        ivCancel.setOnClickListener {
            finish()
        }
        intent?.getStringExtra("choseType")?.let {
            choseType=it
        }
        if (choseType=="1") {
            data.add(CoinType(R.mipmap.ic_usdt, "USDT"))
//            data.add(CoinType(R.mipmap.ic_utc,"UTC"))
//        data.add(CoinType(R.mipmap.ic_utk,"UTK"))
//        data.add(CoinType(R.mipmap.ic_utdm,"UTDM"))
            data.add(CoinType(R.mipmap.ic_uyt, "UYT"))
            data.add(CoinType(R.mipmap.ic_uyt_test, "UYT_TEST"))
        }else if(choseType=="2"){
            data.add(CoinType(R.mipmap.ic_usdt, "USDT"))
            data.add(CoinType(R.mipmap.ic_utc,"UTC"))
//            data.add(CoinType(R.mipmap.ic_uyt, "UYT"))
            data.add(CoinType(R.mipmap.ic_uyt_test, "UYT_TEST"))
        }else if(choseType=="3"){
            data.add(CoinType(R.mipmap.ic_usdt, "USDT"))
            data.add(CoinType(R.mipmap.ic_utk,"UTK"))
            data.add(CoinType(R.mipmap.ic_utdm,"UTDM"))
            data.add(CoinType(R.mipmap.ic_uyt, "UYT"))
            data.add(CoinType(R.mipmap.ic_uyt_test, "UYT_TEST"))
        }
        var adpter= ChoseCoinAdapter(data)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adpter
        adpter.setOnItemClickListener { adapter, view, position ->
            var item=adapter.getItem(position) as CoinType
            var intent=Intent()
               intent.putExtra("CoinType",item.coinType)
            if (item.coinType=="USDT"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
               finish()
            }else if (item.coinType=="UYT_TEST"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
                finish()
            }else if (item.coinType=="UYT"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
                finish()
            }else if (item.coinType=="UTK"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
                finish()
            }else if (item.coinType=="UTC"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
                finish()
            }else if (item.coinType=="UTDM"){
                setResult(DepositMoneyActivity.RESULTCODE,intent)
                finish()
            }
        }
    }

    override fun initData() {


    }

}