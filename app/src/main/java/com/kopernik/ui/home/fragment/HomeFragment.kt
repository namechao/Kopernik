package com.kopernik.ui.home.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.baseweb.BaseWebViewFragment
import com.kopernik.app.events.LocalEvent
import kotlinx.android.synthetic.main.fragment_home.*
import com.kopernik.data.api.Api

/**
 *
 * @ProjectName:    UYT
 * @Package:        com.kopernik.ui.home.fragment
 * @ClassName:      HomeFragment
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/8 2:35 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/8 2:35 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open class HomeFragment: NewBaseFragment<NoViewModel, ViewDataBinding>() {

    companion object{
        fun newInstance() = HomeFragment()

    }
    override fun layoutId()= R.layout.fragment_home
    override fun initView(savedInstanceState: Bundle?) {

    }



}