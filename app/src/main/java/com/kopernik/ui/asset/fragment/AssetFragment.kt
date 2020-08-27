package com.kopernik.ui.asset.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.adapter.AssetAdapter
import com.kopernik.ui.asset.entity.AssetItemEntity
import com.kopernik.ui.asset.viewModel.AssetViewModel
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_asset.smartRefreshLayout
import org.greenrobot.eventbus.EventBus


class AssetFragment : BaseFragment<AssetViewModel,ViewDataBinding>() {
    private var assetAdapter: AssetAdapter? = null
    private var assetShow = true
    private var isFragmentShow = true
    private var mEventBus=EventBus.getDefault()
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
                "UTC"-> {activity?.let { LaunchConfig.startUTCAssetActivity(it) }}
                "UTK"-> activity?.let { LaunchConfig.startUTKAssetActivity(it) }
                "UTDM"-> activity?.let { LaunchConfig.startUDMTAssetActivity(it) }
                "UYT"-> activity?.let { LaunchConfig.startUYTAssetActivity(it) }
            }
        }
        smartRefreshLayout.setOnRefreshListener {
            getAsset()
        }
        smartRefreshLayout.autoRefresh()
    }

   private fun getAsset(){
       viewModel.run {
           getAsset().observe(this@AssetFragment, Observer {
               smartRefreshLayout.finishRefresh()
               if (it.status==200){
                   data.clear()
                   data.add(AssetItemEntity(R.mipmap.ic_utc,"UTC",it.data.utcAmount,it.data.utcCny))
                   data.add(AssetItemEntity(R.mipmap.ic_utk,"UTK",it.data.utkAmount,it.data.utcCny))
                   data.add(AssetItemEntity(R.mipmap.ic_utdm,"UTDM",it.data.utdmCny,it.data.utdmCny))
                   data.add(AssetItemEntity(R.mipmap.ic_uyt,"UYT",it.data.uytAmount,it.data.utdmCny))
                   adapter.setNewData(data)
                   var totle= BigDecimalUtils.add(it.data.utcCny,it.data.utkCny).add(BigDecimalUtils.add(it.data.utdmCny,it.data.utdmCny)).toString()
                   assetTotal.text="≈ ${BigDecimalUtils.roundDOWN(totle,2)}"
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