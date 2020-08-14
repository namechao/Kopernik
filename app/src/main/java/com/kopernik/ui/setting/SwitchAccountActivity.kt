package com.kopernik.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.TimeConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.factory.ViewModelFactory
import com.kopernik.app.utils.APPHelper
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.login.bean.AccountListBean
import com.kopernik.ui.setting.viewModel.DefaultViewModel
import kotlinx.android.synthetic.main.activity_switch_account.*

class SwitchAccountActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {

    private var accountListBean: AccountListBean? = null
//    private val loadingView: LoadingView? = null
    override fun layoutId()=R.layout.activity_switch_account

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.switch_account))
        val adapter = AccountAdapter(accountListBean,this)
        recyclerView.adapter = adapter
        val footerView: View = LayoutInflater.from(getActivity())
            .inflate(R.layout.footer_account_view, null)
        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(0, APPHelper.dp2px(this, 10.toFloat()), 0, 0)
        footerView.layoutParams = params
        adapter.addFooterView(footerView)

        val gson = Gson()
        accountListBean =
            gson.fromJson(UserConfig.singleton?.allAccount, AccountListBean::class.java)
        adapter.setNewData(accountListBean?.accounts!!)

        footerView.findViewById<View>(R.id.created_account_spt).setOnClickListener { v: View? ->
            if (adapter.data != null && adapter.data.size == 5) {
                ToastUtils.showShort(getActivity(), getString(R.string.account_is_max))
                return@setOnClickListener
            }
            LaunchConfig.startChooseAccountAc(this)
        }
        adapter.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.account_name_spt) {
                val accountBeans: List<AccountBean> = adapter.data as List<AccountBean>
                val item: AccountBean = accountBeans[position]
                if (UserConfig.singleton?.accountBean != null &&
                    item.loginAcountHash == UserConfig.singleton?.accountBean?.loginAcountHash
                ) {
                    return@OnItemChildClickListener
                }
                showLoading()
                //设置当前使用的账户
                UserConfig.singleton?.accountString=gson.toJson(item)
                //记录上次的使用账户
                var lastAccountBean: AccountBean? = null
                var currentAccountBean: AccountBean? = null
                for (i in 0 until accountListBean?.behaviorAccounts?.size!!) {
                    if (accountListBean?.behaviorAccounts?.get(i)?.loginAcountHash
                            .equals(item.loginAcountHash)
                    ) {
                        currentAccountBean = accountListBean?.behaviorAccounts?.get(i)
                    }
                    if (accountListBean?.behaviorAccounts?.get(i)?.loginAcountHash
                            .equals(UserConfig.singleton?.accountBean?.loginAcountHash)
                    ) {
                        lastAccountBean = accountListBean?.behaviorAccounts?.get(i)
                    }
                    if (lastAccountBean != null && currentAccountBean != null) break
                }
                accountListBean?.behaviorAccounts?.remove(currentAccountBean)
                accountListBean?.behaviorAccounts?.remove(lastAccountBean)
                if (currentAccountBean != null) {
                    accountListBean?.behaviorAccounts?.add(0, currentAccountBean)
                }
                if (lastAccountBean != null) {
                    accountListBean?.behaviorAccounts?.add(0, lastAccountBean)
                }
                UserConfig.singleton?.allAccount = gson.toJson(accountListBean)
                getViewModel()?.addDisposable(
                    getViewModel()?.delayByString(TimeConfig.RESTART_APP, "tag")
                        ?.subscribe { tag: String ->
                            disMissWaitingDialog(
                                tag
                            )
                        }
                )
            }
        })
    }

    private fun disMissWaitingDialog(tag: String) {
        dismissLoading()
        UserConfig.singleton?.clear()
        mEventBus.post(LocalEvent<Any>(LocalEvent.restartApp))
        finish()
    }


    inner class AccountAdapter(accountListBean: AccountListBean?, context: Context) :
        BaseQuickAdapter<AccountBean, BaseViewHolder>(R.layout.item_account) {
        override fun convert(helper: BaseViewHolder, item: AccountBean) {
            val accountName: SuperTextView = helper.getView(R.id.account_name_spt)
            accountName.setLeftString(item.loginlabel)
            val currentAccount: AccountBean? = UserConfig.singleton?.accountBean
            if (currentAccount == null) {
                accountName.rightIconIV.visibility = View.INVISIBLE
                val lastAccountHash: String ?=
                    accountListBean?.behaviorAccounts?.get(0)?.loginAcountHash
                if (item.loginAcountHash == lastAccountHash) {
                    accountName.setRightString(this@SwitchAccountActivity.getString(R.string.last_use))
                        .useShape()
                } else {
                    accountName.setRightString("").useShape()
                }
            } else {
                accountName.setRightString("").useShape()
                if (item.loginAcountHash == currentAccount.loginAcountHash) {
                    accountName.rightIconIV.visibility = View.VISIBLE
                } else {
                    accountName.rightIconIV.visibility = View.GONE
                }
            }
            helper.addOnClickListener(R.id.account_name_spt)
        }
    }

    override fun initData() {

    }

    fun getViewModel(): DefaultViewModel? {
        return ViewModelFactory.factory().defaultViewModel
    }
}