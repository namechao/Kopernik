package com.kopernik.ui.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.ui.login.adapter.MnemonicAdapter
import com.kopernik.ui.login.adapter.MnemonicInputAdapter
import com.kopernik.app.utils.SpacesItemDecoration
import java.util.*

class UserProtocolActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
  companion object{
      var MNEMONIC_EXTRA = "mnemonicStr"
  }


    override fun layoutId()=R.layout.activity_user_protocol

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.user_protocol_title))
    }

    override fun initData() {

    }

}