package com.kopernik.ui.Ecology.fragment

import android.view.KeyEvent
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import com.kopernik.R
import com.kopernik.app.baseweb.BaseWebViewFragment
import com.kopernik.data.api.Api
import com.kopernik.ui.Ecology.viewModel.NodeViewModel
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.fragment_ecology.*
import org.jetbrains.annotations.Nullable


class EcologyFragment : BaseWebViewFragment<NodeViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance() = EcologyFragment()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ecologyWebview.loadUrl(getUrl())
        }
    }

    override fun onResume() {
        super.onResume()
        ecologyWebview.loadUrl(getUrl())
    }

    @Nullable
    override fun getUrl(): String? {
        return Api.trade
    }

    override fun layoutId(): Int {
        return R.layout.fragment_ecology
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        mAgentWeb?.let {
//            if ( it.handleKeyEvent(
//                    keyCode,
//                    event
//                )) return true
//        }
        return super.onKeyDown(keyCode, event)
    }
    override fun getWebView(): WebView? {
     return ecologyWebview
    }
}