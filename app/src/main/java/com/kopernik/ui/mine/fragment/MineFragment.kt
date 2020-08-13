package com.kopernik.ui.mine.fragment

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.aleyn.mvvm.base.NoViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.baseweb.BaseWebViewFragment
import kotlinx.android.synthetic.main.fragment_trade.*
import com.kopernik.data.api.Api

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : NewBaseFragment<NoViewModel, ViewDataBinding>() {

    companion object{
        fun newInstance() = MineFragment()
    }
    override fun layoutId()= R.layout.fragment_trade

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}