package com.kopernik.ui.account.viewmodel

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kopernik.app.factory.IViewModel

class SplashViewModel(resId: Int) : IViewModel {
    private var mDrawbleRes = -1
    fun loadDefaultImage(view: ImageView) {
        if (mDrawbleRes != -1) {
            Glide.with(view.context).load(mDrawbleRes).into(view)
        }
    }

    override fun onDestroy() {
        clearDisposable()
    }

    init {
        mDrawbleRes = resId
    }
}