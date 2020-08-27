package com.kopernik.app.dialog

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.kopernik.R
import com.kopernik.app.widget.RoundProgressBar
import com.kopernik.ui.asset.util.OnClickFastListener
import java.util.*


class UTCSynthetiseProgerssDialog : DialogFragment(){
    private var progress=0

    private var counts = 0
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_utc_synthetise_progress)
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window
//        window!!.setWindowAnimations(R.style.AnimBottom)
        window.setBackgroundDrawableResource(R.color.transparent)
        val lp = window.attributes
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //        lp.height =  getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.attributes = lp
        val bundle = arguments
        counts = bundle!!.getInt("counts")
        initView(dialog)
        return dialog
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun initView(dialog: Dialog) {
        var  timer=Timer()
       var roundProgressBar=dialog.findViewById<RoundProgressBar>(R.id.roundProgressBar)
       var ivProgressBarBg=dialog.findViewById<ImageView>(R.id.ivProgressBarBg)
        var flProgress=dialog.findViewById<FrameLayout>(R.id.flProgress)
       var  clCoinSuccess=dialog.findViewById<ConstraintLayout>(R.id.clCoinSuccess)
       var ivCoin=dialog.findViewById<ImageView>(R.id.ivCoin)
       var tvGetCoin=dialog.findViewById<TextView>(R.id.tvGetCoin)
       var tvIKnow=dialog.findViewById<TextView>(R.id.tvIKnow)
       var ivGetCoinBg=dialog.findViewById<ImageView>(R.id.ivGetCoinBg)
        tvIKnow?.setOnClickListener { dismiss() }
        roundProgressBar?.setTextSize(120f)
        roundProgressBar?.setProgress(1)
        //旋转背景动画
        var rotationAnimation=ObjectAnimator.ofFloat(ivProgressBarBg,"rotation",0.0f,360.0f,0.0f,-360.0f)
        rotationAnimation.duration=10000
        rotationAnimation.repeatCount=-1
        rotationAnimation.start()
        tvGetCoin.text="${getString(R.string.utc_get_utc_counts)}$counts${getString(R.string.utc_per)}UTC"
       var animatorSet =  AnimatorSet();
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(ivCoin,"scaleX",0f,1.3f,1f)
                .setDuration(2000),
            ObjectAnimator.ofFloat(ivCoin, "scaleY",0f,1.3f,1f)
                .setDuration(2000),
            ObjectAnimator.ofFloat(ivGetCoinBg,"scaleX",0f,1.3f,1f)
                .setDuration(2000),
            ObjectAnimator.ofFloat(ivGetCoinBg, "scaleY",0f,1.3f,1f)
                .setDuration(2000),
            ObjectAnimator.ofFloat(tvGetCoin, "alpha",0f,1f).setDuration(2000),
            ObjectAnimator.ofFloat(tvIKnow, "alpha",0f,1f).setDuration(2000)
        );
        //动态进度条
        timer.schedule(object :TimerTask(){
            override fun run() {
              activity?.runOnUiThread {
                progress++
                  if (progress==100){
                      timer.cancel()
                      clCoinSuccess?.visibility=View.VISIBLE
                      flProgress.visibility=View.GONE
                      animatorSet.start()
                  }else{
                      roundProgressBar?.setProgress(progress)
                  }
              }
            }

        },100,50)
    }

    //点击确定按钮回调到页面进行网络请求处理
    var clickFastListener: OnClickFastListener = object : OnClickFastListener() {
        override fun onFastClick(v: View) {
            listener?.let { it.onRequest("") }
            dismiss()
        }
    }

    private var listener:RequestListener?=null
   open fun setOnRequestListener(requestListener: RequestListener){
        listener=requestListener
    }
    interface RequestListener {
        fun onRequest(params:String)
    }

    companion object {
        fun newInstance(counts: Int): UTCSynthetiseProgerssDialog {
            val fragment = UTCSynthetiseProgerssDialog()
            val bundle = Bundle()
            bundle.putInt("counts", counts)
            fragment.arguments = bundle
            return fragment
        }
    }
}