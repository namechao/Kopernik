package com.kopernik.ui.asset.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.BaseFragment
import com.kopernik.R
import com.kopernik.app.config.LaunchConfig
import com.kopernik.ui.asset.adapter.AssetAdapter
import com.kopernik.ui.asset.entity.AssetBean
import com.kopernik.ui.asset.viewModel.AssetViewModel
import kotlinx.android.synthetic.main.fragment_asset.*
import org.greenrobot.eventbus.EventBus


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


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        var adapter=AssetAdapter()
        recyclerView.adapter=adapter
        var  list= arrayListOf("ssdsadsa","asdsadad","Asdsdsa","asdsda")
        adapter.setNewData(list)
        adapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> activity?.let { LaunchConfig.startUTCAssetActivity(it) }
                1-> activity?.let { LaunchConfig.startUTKAssetActivity(it) }
                2-> activity?.let { LaunchConfig.startUDMTAssetActivity(it) }
            }
        }
    }







}