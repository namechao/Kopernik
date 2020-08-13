package com.kopernik.ui.fragment

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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EcologyFragment : NewBaseFragment<NodeViewModel, ViewDataBinding>() {
    private val mFragments = ArrayList<Fragment>()
    private var mTitles: Array<String>? = null

    companion object {
        fun newInstance() = EcologyFragment()
    }

    override fun layoutId() = R.layout.fragment_node
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mTitles = arrayOf(
            getString(R.string.verify_nodes),
            getString(R.string.sync_nodes),
            getString(R.string.quit_nodes)
        )
        if (UserConfig.singleton?.getAccount()== null || !StringUtils.isEmpty(
                UserConfig.singleton?.getAccount()?.nodeHash
            )
        ) {
            nodeRegisterTv.visibility = View.GONE
        } else {
            nodeRegisterTv.visibility = View.VISIBLE
        }
        mFragments.add(NodeListFragment.newInstance(1))
        mFragments.add(NodeListFragment.newInstance(2))
        mFragments.add(NodeListFragment.newInstance(3))
        val pagerAdapter = PagerAdapter(childFragmentManager)
        viewpager.adapter = pagerAdapter
        tabLayout.setViewPager(viewpager)

        tabLayout.onPageSelected(0)
        tabLayout.getTitleView(0).textSize = 20F
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(position: Int) {
                for (i in mTitles!!.indices) {
                    if (position == i) { //选中的字体大小
                        tabLayout.getTitleView(i).textSize = 20F
                    } else {
                        tabLayout.getTitleView(i).textSize = 17F
                    }
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        nodeRegisterTv.setOnClickListener { checkRegisterInfo() }
        myVoteLL.setOnClickListener(View.OnClickListener {
            if (UserConfig.singleton?.getAccount() == null) {
                activity?.let { it1 -> LaunchConfig.startChooseAccountAc(it1) }
                return@OnClickListener
            }
            activity?.let { it1 -> LaunchConfig.startMyVoteAc(it1) }
        })
    }

    override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.switchNodeFragment)) {
            val code = event.data as Int
            viewpager.setCurrentItem(code)
            tabLayout.onPageSelected(code)
        }
    }

    private fun checkRegisterInfo() {
        viewModel.run {
            getRegisterInfo().observe(activity!!, androidx.lifecycle.Observer {
                if (it.status == 200) {
                    if (!it.data.isOk) {
                        ToastUtils.showShort(
                            activity,
                            getString(R.string.can_not_created_node)
                        )
                        return@Observer
                    }
                    LaunchConfig.startNodeRegisterAc(activity!!, it.data)
                } else {
                    ErrorCode.showErrorMsg(activity, it.status)
                }
            })
        }
    }

    inner class PagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles?.get(position).toString()
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }
}