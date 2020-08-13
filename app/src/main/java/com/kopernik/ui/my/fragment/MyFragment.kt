package com.kopernik.ui.my.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.network.http.ErrorCode
import com.kopernik.ui.Ecology.fragment.NodeListFragment
import com.kopernik.app.utils.StringUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.Ecology.viewModel.NodeViewModel
import kotlinx.android.synthetic.main.fragment_node.*
import java.util.*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyFragment : NewBaseFragment<NodeViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

    override fun layoutId() = R.layout.fragment_my
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


    }

    override fun onEvent(event: LocalEvent<Any>) {

    }


}