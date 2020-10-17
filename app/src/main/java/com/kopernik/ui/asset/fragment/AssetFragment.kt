package com.kopernik.ui.asset.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.adapter.AssetAdapter
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.asset.entity.AssetItemEntity
import com.kopernik.ui.asset.viewModel.AssetViewModel
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_asset.smartRefreshLayout
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal


class AssetFragment : BaseFragment<AssetViewModel,ViewDataBinding>() {
    private var assetAdapter: AssetAdapter? = null
    private var assetShow = true
    private var isFragmentShow = true
    private var mEventBus=EventBus.getDefault()
    private var asset: AssetEntity?=null
    var adapter=AssetAdapter(arrayListOf())
    var data=ArrayList<AssetItemEntity>()
    companion object{
        fun newInstance() = AssetFragment()
    }
    override fun layoutId() =R.layout.fragment_asset


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter=adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            var item =adapter.data[position] as AssetItemEntity
            when(item?.coinType){
                "UTC"-> {activity?.let { LaunchConfig.startUTCAssetActivity(it,asset?.utcAmount) }}
                "UTK"-> activity?.let { LaunchConfig.startUTKAssetActivity(it,asset?.utkAmount) }
                "UTDM"-> activity?.let { LaunchConfig.startUDMTAssetActivity(it,asset?.utdmAmount) }
                "UYT_TEST"-> activity?.let { LaunchConfig.startUYTTESTAssetActivity(it,asset?.uytAmount,"UYT") }
                "UYT"-> activity?.let { LaunchConfig.startUYTAssetActivity(it,asset?.uytProAmount,"UYTPRO") }
            }
        }
        smartRefreshLayout.setOnRefreshListener {
            getAsset()
        }
        smartRefreshLayout.autoRefresh()
    }

    override fun onResume() {
        super.onResume()
        smartRefreshLayout.autoRefresh()
    }
   private fun getAsset(){
       viewModel.run {
           getAsset().observe(this@AssetFragment, Observer {
               smartRefreshLayout.finishRefresh()
               if (it.status==200){
                   asset=it.data
                   data.clear()
                   data.add(AssetItemEntity(R.mipmap.ic_utc,"UTC",it.data.utcAmount,it.data.utcCny))
                   data.add(AssetItemEntity(R.mipmap.ic_utk,"UTK",it.data.utkAmount,it.data.utkCny))
                   data.add(AssetItemEntity(R.mipmap.ic_utdm,"UTDM",it.data.utdmAmount,it.data.utdmCny))
                   data.add(AssetItemEntity(R.mipmap.ic_uyt_test,"UYT_TEST",it.data.uytAmount,it.data.uytCny))
                   data.add(AssetItemEntity(R.mipmap.ic_uyt,"UYT",it.data.uytProAmount,it.data.uytProCny))
                   adapter.setNewData(data)
                   var totle= BigDecimalUtils.add(it.data.utcCny,it.data.utkCny).add(BigDecimalUtils.add(it.data.utdmCny,it.data.uytCny)).add(
                       BigDecimal( it.data.uytProCny)).toString()
                   assetTotal.text="≈ ${BigDecimalUtils.roundDOWN(totle,2)}"
               }else{
                   ErrorCode.showErrorMsg(activity,it.status)
               }
           })
       }
   }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            smartRefreshLayout.autoRefresh()
        }
    }



}