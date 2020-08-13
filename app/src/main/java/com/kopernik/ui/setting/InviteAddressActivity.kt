package com.kopernik.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aleyn.mvvm.base.NoViewModel
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.events.LocalEvent
import com.kopernik.ui.setting.entity.InviteIntegralBean
import com.kopernik.ui.setting.fragment.InviteAddressFragment
import kotlinx.android.synthetic.main.activity_invite_address.*
import java.util.*

class InviteAddressActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    private val mFragments: ArrayList<Fragment> = ArrayList<Fragment>()
    private var mTitles: Array<String>?=null
    override fun layoutId()=R.layout.activity_invite_address
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_invite_address))
        inviteIntegralLl.setVisibility(View.GONE)
        hidDiv()
    }

    override fun initData() {
        mTitles = arrayOf(
            getString(R.string.account_invite_address),
            getString(R.string.node_invite_account),
            getString(R.string.node_invite_node)
        )
        mFragments.add(InviteAddressFragment.newInstance(InviteAddressFragment.ACCOUNT_INVITE_ACCOUNT))
        mFragments.add(InviteAddressFragment.newInstance(InviteAddressFragment.NODE_INVITE_ACCOUNT))
        mFragments.add(InviteAddressFragment.newInstance(InviteAddressFragment.NODE_INVITE_NODE))
        viewPager.adapter = PagerAdapter(
            supportFragmentManager
        )
        tabLayout.setTabData(mTitles)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {}
        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
                if (position == 2 && inviteIntegralBean != null && inviteIntegralBean?.nodePacket == 1) {
                    inviteIntegralLl.visibility = View.VISIBLE
                } else {
                    inviteIntegralLl.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPager.currentItem = 0
    }
    private var inviteIntegralBean: InviteIntegralBean? = null
   override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.inviteIntegral)) {
            inviteIntegralBean = event.data as InviteIntegralBean
            val type = event.data2 as Int
            //1 租用节点包 需要显示   0 其他节点不需要显示
            if (inviteIntegralBean?.nodePacket == 1 && type == InviteAddressFragment.NODE_INVITE_NODE) {
                inviteIntegralLl.visibility = View.VISIBLE
                invitePointsSpt.setCenterString(inviteIntegralBean?.score.toString() + "")
                //                invitePowerSpt.setCenterString(bean.get);
                invitedThisMonthSpt.setCenterString(
                    inviteIntegralBean?.monthRecommend.toString() + ""
                )
                cumulativeInvitationSpt.setCenterString(
                    inviteIntegralBean?.totalRecommend.toString() + ""
                )
            } else {
                inviteIntegralLl.visibility = View.GONE
            }
        }
    }

    inner class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles?.get(position)!!
        }

        override fun getItem(position: Int): Fragment {
            return mFragments?.get(position)
        }
    }
}