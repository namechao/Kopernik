package com.kopernik.ui.mine

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.BuildConfig
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.base.NewFullScreenBaseActivity
import com.kopernik.app.dialog.UDMTDialog

import kotlinx.android.synthetic.main.activity_snythetise_utc.*
import kotlinx.android.synthetic.main.activity_snythetise_utc.ivSound
import kotlinx.android.synthetic.main.activity_snythetise_utc.lottieAnimationView
import kotlinx.android.synthetic.main.activity_udmt_asset.*
import kotlinx.android.synthetic.main.fragment_trade.*
import java.io.IOException


class SynthetiseUTCActivity : NewFullScreenBaseActivity<NoViewModel,ViewDataBinding>() {
    var isOpenSound=false
    override fun layoutId()=R.layout.activity_snythetise_utc
    var player = MediaPlayer()
    override fun initView(savedInstanceState: Bundle?) {
        lottieAnimationView.imageAssetsFolder = "synthetise";
        lottieAnimationView.setAnimation("synthetise.json");

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
//        llSnythetiseUTC.setOnClickListener {
//            ///兑换
//                var dialog = UDMTDialog.newInstance(1)
//            dialog!!.setOnRequestListener(object : UDMTDialog.RequestListener {
//                    override fun onRequest(type: Int, params: String) {
//
//                    }
//                })
//            dialog!!.show(supportFragmentManager, "withdrawRecommed")
//        }
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
    }

    override fun onResume() {
        super.onResume()
        lottieAnimationView.playAnimation()
    }
    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        lottieAnimationView.cancelAnimation()
    }

    override fun initData() {

    }
}