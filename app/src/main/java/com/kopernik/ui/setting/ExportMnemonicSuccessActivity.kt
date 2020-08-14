package com.kopernik.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.UserConfig
import com.kopernik.ui.login.adapter.MnemonicAdapter
import kotlinx.android.synthetic.main.activity_export_mnemonic_success.*

class ExportMnemonicSuccessActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    companion object{
        val MNEMONIC_EXTRA = "mnemoniclist"
    }

    private var adapter: MnemonicAdapter? = null
    override fun layoutId()=R.layout.activity_export_mnemonic_success

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_modify_export_words))
        val intent = intent
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.alignItems = AlignItems.STRETCH

        adapter = MnemonicAdapter(arrayListOf())
        recyclerView.setAdapter(adapter)
        recyclerView.setLayoutManager(flexboxLayoutManager)

        confirmTv.setOnClickListener(View.OnClickListener { v: View? -> finish() })
        UserConfig.singleton?.mnemonic?.let {
            adapter?.setNewData(it.mnemonic)
        }

    }

    override fun initData() {

    }

}