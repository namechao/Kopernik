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
import kotlinx.android.synthetic.main.fragment_my.*
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
      //设置
        setting.setOnClickListener {
            activity?.let {LaunchConfig.startSettingActivityAc(it) }
        }
        //实名认证
        realNameAuth.setOnClickListener {
            activity?.let { LaunchConfig.startRealNameAuthenticationActivity(it) }
        }
       //修改登录密码
        changeLoginPsw.setOnClickListener {
            activity?.let { LaunchConfig.startForgetPasswordActivity(it) }
        }
        //修改交易密码
        tradePsw.setOnClickListener {
            activity?.let { LaunchConfig.startTradePasswordActivity(it) }
        }
        //关于我们
        aboutUs.setOnClickListener {
            activity?.let { LaunchConfig.startAboutUsActivityAc(it) }
        }
        //google验证
        googleAuthen.setOnClickListener {
            activity?.let { LaunchConfig.startGoogleVerifyActivity(it) }
        }
     //邀请朋友
    inviteFriends.setOnClickListener {
            activity?.let { LaunchConfig.startInviteFriendsActivity(it) }
        }
    }

    override fun onEvent(event: LocalEvent<Any>) {

    }


}