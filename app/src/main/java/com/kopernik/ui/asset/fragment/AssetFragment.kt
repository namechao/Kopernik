package com.kopernik.ui.asset.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.asset.adapter.AssetAdapter
import com.kopernik.ui.asset.entity.AssetEntity
import com.kopernik.ui.asset.entity.AssetItemEntity
import com.kopernik.ui.asset.viewModel.AssetViewModel
import kotlinx.android.synthetic.main.fragment_asset.*
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
            when(position){
                0-> {activity?.let { LaunchConfig.startUTCAssetActivity(it) }}
                1-> activity?.let { LaunchConfig.startUTKAssetActivity(it) }
                2-> activity?.let { LaunchConfig.startUDMTAssetActivity(it) }
                3-> activity?.let { LaunchConfig.startUYTAssetActivity(it) }
            }
        }
        smartRefreshLayout.setOnRefreshListener {
            viewModel.run {
                getAsset().observeForever {
                    smartRefreshLayout.finishRefresh()
                    if (it.status==200){
                        data.clear()
                        data.add(AssetItemEntity(R.mipmap.ic_utc,"UTC",it.data.utcAmount,it.data.utcCny))
                        data.add(AssetItemEntity(R.mipmap.ic_utk,"UTK",it.data.utkAmount,it.data.utcCny))
                        data.add(AssetItemEntity(R.mipmap.ic_utdm,"UTDM",it.data.utdmCny,it.data.utdmCny))
                        data.add(AssetItemEntity(R.mipmap.ic_uyt,"UYT",it.data.uytAmount,it.data.utdmCny))
                        adapter.setNewData(data)
                        var totle=it.data.utcCny+it.data.utcCny+it.data.utdmCny+it.data.utdmCny
                        assetTotal.text="≈ ${BigDecimalUtils.roundDOWN(totle,2)}"
                    }
                }
            }
        }
    }







}