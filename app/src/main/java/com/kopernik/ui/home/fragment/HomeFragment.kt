package com.kopernik.ui.home.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.ui.home.adadpter.AutoPollAdapter
import kotlinx.android.synthetic.main.fragment_home.*


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
    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(savedInstanceState: Bundle?) {
        noticeRecyclerView.layoutManager=LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        var  list= arrayListOf("爱的发的更多更多故事的感受到","sdagsegdgdsgsdgsdg","asgsgagagaasfsafasa")
        val autoPollAdapter = activity?.let {
            AutoPollAdapter(
                it,
                list
                )
        }
        noticeRecyclerView.setAdapter(autoPollAdapter)
        //启动滚动
        noticeRecyclerView.start()
        vfNoticeScroll.setInAnimation(context, R.anim.notice_in)
        vfNoticeScroll.setOutAnimation(context, R.anim.notice_out)
        val ionfo= arrayListOf("年后阿斯达啊ad阿达")
        for (i in  ionfo) {
            val view=LayoutInflater.from(activity).inflate(R.layout.item_notice, null)
            val titleTv: TextView = view.findViewById(R.id.tv_title) as TextView
            titleTv?.text = i
            vfNoticeScroll.addView(view)

        }
        vfNoticeScroll.startFlipping()

    }



}