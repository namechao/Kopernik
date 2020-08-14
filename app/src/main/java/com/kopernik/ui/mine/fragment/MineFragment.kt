package com.kopernik.ui.mine.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseFragment
import kotlinx.android.synthetic.main.fragment_trade.*

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : NewBaseFragment<NoViewModel, ViewDataBinding>() {
    var ishow=false
    companion object{
        fun newInstance() = MineFragment()
    }
    override fun layoutId()= R.layout.fragment_trade

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        lottieAnimationView.imageAssetsFolder = "mines";
        lottieAnimationView.setAnimation("mines.json");
        lottieAnimationView.loop(true);
    }

    override fun onResume() {
        super.onResume()
            lottieAnimationView.playAnimation();
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden){
            lottieAnimationView.pauseAnimation()
        }else{
            lottieAnimationView.playAnimation();
        }
    }

}