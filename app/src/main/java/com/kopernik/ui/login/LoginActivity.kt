package com.kopernik.ui.login

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.gyf.immersionbar.ImmersionBar
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.ChoseAreaCodeDialog
import com.kopernik.app.dialog.UDMTDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        ImmersionBar.with(this).reset().init()
        return super.onCreateView(name, context, attrs)
    }
    override fun layoutId()=R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
        forgetPsw.setOnClickListener {
            LaunchConfig.startForgetPasswordActivity(this)
        }
        confirmBtn.setOnClickListener {
            LaunchConfig.startMainAc(this)
        }
        register.setOnClickListener {
            LaunchConfig.startRegisterActivity(this)
        }
        //协议
        userProtocol.setOnClickListener {
          LaunchConfig.startUserProtocolActivity(this)
        }
        tvPhoneHead.setOnClickListener {
            var doialog = ChoseAreaCodeDialog.newInstance(1)
            doialog!!.setOnRequestListener(object : ChoseAreaCodeDialog.RequestListener {
                override fun onRequest(type: Int, params: String) {

                }
            })
            doialog!!.show(supportFragmentManager, "doialog")
        }

    }

    override fun initData() {

    }
}