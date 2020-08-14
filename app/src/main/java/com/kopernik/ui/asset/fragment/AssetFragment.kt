package com.kopernik.ui.asset.fragment

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


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }







}