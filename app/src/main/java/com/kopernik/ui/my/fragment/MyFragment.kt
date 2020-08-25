package com.kopernik.ui.my.fragment

import android.content.Intent
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
import com.kopernik.ui.MainActivity
import com.kopernik.ui.login.LoginActivity
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
        UserConfig.singleton?.accountBean?.phone?.let {
            if (it.length>5){
                tvPhoneNumber.text="${it.subSequence(0,3)} ****${it.subSequence(it.length-4,it.length)}"
            }

        }
        UserConfig.singleton?.accountBean?.email?.let {
            tvPhoneNumber.text=it
        }
        UserConfig.singleton?.accountBean?.uid?.let {
            tvId.text="ID："+it
        }
        //是否实名
        if (UserConfig.singleton?.accountBean?.name.isNullOrEmpty()){
            realNameAuth.setRightString(getString(R.string.no_verify))
        }else{
            realNameAuth.setRightString(getString(R.string.had_verified))
        }
        //交易密码
        if (UserConfig.singleton?.accountBean?.salePwd.isNullOrEmpty()){
            tradePsw.setRightString(getString(R.string.no_setting))
        }else{
            tradePsw.setRightString(getString(R.string.had_settting))
        }
      //设置
        setting.setOnClickListener {
            activity?.let {LaunchConfig.startSettingActivityAc(it) }
        }
        //实名认证
        realNameAuth.setOnClickListener {
            if (UserConfig.singleton?.accountBean?.name.isNullOrEmpty())
            activity?.let { LaunchConfig.startRealNameAuthenticationActivity(it) }
        }
       //修改登录密码
        changeLoginPsw.setOnClickListener {

            //已登录
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    activity?.let { LaunchConfig.startForgetPasswordActivity(it,1,2) }
                }else{
                    activity?.let { LaunchConfig.startForgetPasswordActivity(it,2,2) }
                }
            }


        }
        //修改交易密码
        tradePsw.setOnClickListener {
            //已登录
            if (UserConfig.singleton?.accountBean!=null){
                if (!UserConfig.singleton?.accountBean?.phone.isNullOrEmpty()){
                    if (UserConfig.singleton?.accountBean?.salePwd.isNullOrEmpty()){
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,1) }
                    }else{
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,2) }
                    }

                }else{
                    if (UserConfig.singleton?.accountBean?.salePwd.isNullOrEmpty()){
                    activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,1) }
                    }else{
                        activity?.let { LaunchConfig.startTradePasswordActivity(it, 1,2) }
                    }
                }
            }

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
        exitLogin.setOnClickListener {
            UserConfig.singleton?.accountString=null
            val intent =
                Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onEvent(event: LocalEvent<Any>) {

    }


}