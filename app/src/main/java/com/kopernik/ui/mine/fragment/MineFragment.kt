package com.kopernik.ui.mine.fragment

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.utils.BigDecimalUtils
import com.kopernik.ui.mine.viewModel.MineViewModel
import kotlinx.android.synthetic.main.fragment_trade.*
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : NewBaseFragment<MineViewModel, ViewDataBinding>() {
    var ishow=false
    var isOpenSound=false
    companion object{
        fun newInstance() = MineFragment()
    }
    override fun layoutId()= R.layout.fragment_trade
    var player = MediaPlayer()
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        lottieAnimationView.imageAssetsFolder = "mines";
        lottieAnimationView.setAnimation("mines.json");

        val assetManager: AssetManager = resources.assets
        try {
            val fileDescriptor =
                assetManager.openFd("mining.mp3")
            player.setDataSource(
                fileDescriptor.fileDescriptor,
                fileDescriptor.startOffset,
                fileDescriptor.startOffset
            )
            player.prepare()
            player.isLooping=true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //合成utc
        synthesis.setOnClickListener {
            activity?.let { it1 -> LaunchConfig.startSynthetiseUTCActivity(it1) }
        }//购买矿机
        buyMine.setOnClickListener {
            activity?.let { it1 -> LaunchConfig.startPurchaseMiningMachineryActivity(it1) }
        }
        incomeRecord.setOnClickListener {
            activity?.let { it1 -> LaunchConfig.startUTDMRevenueRecordActivity(it1) }
        }
        ivSound.setOnClickListener {
            if (!isOpenSound){
                isOpenSound=true
                //打开音乐
                player.start()
                ivSound.setImageResource(R.mipmap.ic_open_sound)
            }else{
                //关闭音乐
                isOpenSound=false
                player.pause()
                ivSound.setImageResource(R.mipmap.ic_close_sound)
            }
        }
        smartRefreshLayout.setOnRefreshListener {
            getData()
        }
        smartRefreshLayout.autoRefresh()
    }
    //获取收益数据
    fun getData(){
        viewModel.run {
            viewModel.getUtdmTotal().observe(this@MineFragment, Observer {
                smartRefreshLayout.finishRefresh()
                income.text=BigDecimalUtils.roundDOWN(it?.data?.amount,2)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        lottieAnimationView.playAnimation();
    }

    override fun onPause() {
        super.onPause()
        lottieAnimationView.cancelAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lottieAnimationView.cancelAnimation()
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden&&isOpenSound){
            //关闭音乐
            isOpenSound=false
            ivSound.setImageResource(R.mipmap.ic_close_sound)
            player.pause()
        }
        if (hidden){
            lottieAnimationView.pauseAnimation()
        }else{
            smartRefreshLayout.autoRefresh()
            lottieAnimationView.playAnimation();
        }
    }

}