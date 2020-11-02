package com.kopernik.ui.Ecology.fragment

import android.view.KeyEvent
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import com.kopernik.R
import com.kopernik.app.baseweb.BaseWebViewFragment
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.viewModel.NodeViewModel
import org.jetbrains.annotations.Nullable


class EcologyFragment : BaseWebViewFragment<NodeViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = EcologyFragment()
    }


    protected fun init() {}

    @NonNull
    override fun getAgentWebParent(): ViewGroup? {
        return mRootView?.findViewById(R.id.container)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb != null && mAgentWeb!!.handleKeyEvent(
                keyCode,
                event
            )
        ) true else super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            getAgentWeb()!!.urlLoader.loadUrl(getUrl())
        }
    }

    override fun onResume() {
        super.onResume()
        getAgentWeb()!!.urlLoader.loadUrl(getUrl())
    }

    @Nullable
    override fun getUrl(): String? {
        return Api.trade
    }

    override fun layoutId(): Int {
        return R.layout.fragment_ecology
    }
}