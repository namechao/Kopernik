package com.kopernik.ui.fragment

import android.view.KeyEvent
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.kopernik.R
import com.kopernik.app.baseweb.BaseWebViewFragment
import com.kopernik.app.events.LocalEvent
import kotlinx.android.synthetic.main.fragment_home.*
import com.kopernik.data.api.Api

/**
 *
 * @ProjectName:    UYT
 * @Package:        com.kopernik.ui.fragment
 * @ClassName:      HomeFragment
 * @Description:     java类作用描述
 * @Author:         zhanglichao
 * @CreateDate:     2020/7/8 2:35 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/7/8 2:35 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open class HomeFragment: BaseWebViewFragment<NoViewModel, ViewDataBinding>() {

    companion object{
        fun newInstance() = HomeFragment()

    }
    override fun layoutId()= R.layout.fragment_home
//    private var dialog: NodeQuitDialog? = null

    protected fun init() {}

    override fun onResume() {
        super.onResume()
//        checkAccountNode()
//        mAgentWeb?.urlLoader?.loadUrl(getUrl())
    }



    override fun onPause() {
        super.onPause()
//        if (dialog != null) dialog.dismiss()
    }

//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        if (!hidden) {
//            mAgentWeb!!.urlLoader.loadUrl(getUrl())
//        }
//    }

    override fun onStart() {
        super.onStart()
    }


    override  fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        mAgentWeb?.let {
//            if ( it.handleKeyEvent(
//                    keyCode,
//                    event
//                )) return true
//        }
       return super.onKeyDown(keyCode, event)
    }

    //TODO这是evenbus传过来的
    override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.reLoadWeb)) {
            getWebView()?.loadUrl(getUrl())
        }
    }

    @Nullable
    protected override fun getUrl(): String? {
        return Api.home
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun getWebView(): BridgeWebView? {
       return homeWebview
    }

}