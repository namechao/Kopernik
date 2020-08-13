package com.kopernik.ui.account

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
import com.kopernik.app.config.LaunchConfig
import com.kopernik.ui.account.adapter.MnemonicAdapter
import com.kopernik.ui.account.adapter.MnemonicInputAdapter
import com.kopernik.app.utils.SpacesItemDecoration
import com.kopernik.ui.account.bean.Mnemonic
import kotlinx.android.synthetic.main.activity_mnemonic_confirm.*
import java.util.*

class MnemonicConfirmActivity : NewBaseActivity<NoViewModel, ViewDataBinding>() {
  companion object{
      var MNEMONIC_EXTRA = "mnemonicStr"
  }
    private val inputMnemonicList: MutableList<String> =
        ArrayList()
    private val confirmMnemonicList: MutableList<String> =
        ArrayList()
    private val tempList: MutableList<String> =
        ArrayList()
    private var inputAdapter: MnemonicInputAdapter? =
        null
    private var mnemonicAdapter: MnemonicAdapter? = null
    private var mnemonicBean:Mnemonic?=null
    override fun layoutId()=R.layout.activity_mnemonic_confirm

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.user_create_title))
    }

    override fun initData() {
        mnemonicBean = intent.getSerializableExtra(MNEMONIC_EXTRA) as Mnemonic
        if (mnemonicBean==null||mnemonicBean?.mnemonic==null) return
        confirmMnemonicList.addAll(mnemonicBean?.mnemonic!!)
        tempList.addAll(mnemonicBean?.mnemonic!!)
        confirmMnemonicList.shuffle()

        inputAdapter = MnemonicInputAdapter(inputMnemonicList)
        mnemonicAdapter = MnemonicAdapter( confirmMnemonicList)

        val manager1: FlexboxLayoutManager = NoMoveFlexboxLayoutManager(this)
        manager1.flexDirection = FlexDirection.ROW
        manager1.flexWrap = FlexWrap.WRAP
        manager1.alignItems = AlignItems.STRETCH

        val manager2: FlexboxLayoutManager = NoMoveFlexboxLayoutManager(this)
        manager2.flexDirection = FlexDirection.ROW
        manager2.flexWrap = FlexWrap.WRAP
        manager2.alignItems = AlignItems.STRETCH

        inputRecyclerView.layoutManager = manager1
        mnemonicRecyclerView.layoutManager = manager2
        inputRecyclerView.addItemDecoration(SpacesItemDecoration(8))
        mnemonicRecyclerView.addItemDecoration(SpacesItemDecoration(8))

        inputRecyclerView.adapter = inputAdapter
        inputAdapter?.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
                confirmMnemonicList.add(inputMnemonicList[position])
                inputMnemonicList.remove(inputMnemonicList[position])
                mnemonicAdapter?.notifyDataSetChanged()
                inputAdapter?.notifyDataSetChanged()
                check()
            }

        mnemonicRecyclerView.adapter = mnemonicAdapter
        mnemonicAdapter?.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
                inputMnemonicList.add(confirmMnemonicList[position])
                confirmMnemonicList.remove(confirmMnemonicList[position])
                mnemonicAdapter?.notifyDataSetChanged()
                inputAdapter?.notifyDataSetChanged()
                check()
            }
        btConfirm.setOnClickListener(View.OnClickListener { v: View? ->
            LaunchConfig.startCreatedAccountAc(this, mnemonicBean)
            finish()
        })
    }
    private fun check() {
        if (inputMnemonicList.size > 0) {
            var isAllRight = true
            for (position in inputMnemonicList.indices) {
                if (tempList[position] != inputMnemonicList[position]) {
                    isAllRight = false
                }
            }
            if (isAllRight) {
                btConfirm.isEnabled = tempList.size == inputMnemonicList.size
            }
        } else {
            btConfirm.isEnabled = false
        }
    }
}