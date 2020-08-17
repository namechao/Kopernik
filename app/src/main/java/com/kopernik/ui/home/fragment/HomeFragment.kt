package com.kopernik.ui.home.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.dialog.ReminderDialog
import com.kopernik.ui.home.adadpter.AutoPollAdapter
import com.kopernik.ui.home.adadpter.HomeAdapter
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

        var  list1= arrayListOf("爱的发的更多更多故事的感受到","sdagsegdgdsgsdgsdg","asgsgagagaasfsafasa")
        val autoPollAdapter = activity?.let {
            AutoPollAdapter(
                it,
                list1
                )
        }
        noticeRecyclerView.adapter = autoPollAdapter
        //启动滚动
        noticeRecyclerView.start()
    var  list= arrayListOf("10月1日起，达到Cha.in节点福利门槛用户的额外福利为节点所得用户投资","下个会早点哦","你没找吧")
        for (i in list) {
            val view: View = layoutInflater.inflate(R.layout.item_notice, null)
            var content=view.findViewById<TextView>(R.id.content)
            content.text=i
            vfNoticeScroll.addView(view)

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Guanggao", "点击了" + i);
                }
            });*/
        }
        vfNoticeScroll.setFlipInterval(2000);
        vfNoticeScroll.startFlipping();
        /* if(view_flipper.getDisplayedChild()==0){
             view_flipper.getCurrentView().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View arg0) {
                     Log.d("Guanggao", "点击了");
                 }
             });*/
        //悬浮按钮
        ivFloat.setOnClickListener {
            activity?.let { it1 ->
                ReminderDialog(it1)
                    .setCancelable(true)
                    .setMsg("1")
                    .show()
            }

        }
        //下方列表
        recyclerView.layoutManager=LinearLayoutManager(activity)
        var adapter= HomeAdapter()
        recyclerView.adapter=adapter
        var data= arrayListOf("saga","asfafaas","afafasgagaga","dfdadfafa","asdsagassasa","asgasgagaga")
        adapter.setNewData(data)
        val translateAnimation = TranslateAnimation(0f, 0f, 80f, 50f)
        translateAnimation.duration = 1000
        translateAnimation.repeatCount = Animation.INFINITE
        translateAnimation.repeatMode = Animation.REVERSE
        clGold.animation = translateAnimation //这里iv就是我们要执行动画的item，例如一个imageView

        translateAnimation.start()
    }



}