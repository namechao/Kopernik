package com.kopernik.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.dialog.UYTAlertDialog
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.ToastUtils
import com.kopernik.app.widget.statuslayoutmanager.OnStatusChildClickListener
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutHelper
import com.kopernik.app.widget.statuslayoutmanager.StatusLayoutManager
import com.kopernik.ui.setting.adapter.ContactAdapter
import com.kopernik.ui.setting.entity.ContactBean
import com.kopernik.ui.setting.viewModel.ContactsViewModel
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : NewBaseActivity<ContactsViewModel, ViewDataBinding>(),
    OnStatusChildClickListener {

    private var statusLayoutManager: StatusLayoutManager? = null
    private var adapter: ContactAdapter? = null
    private var isForResult = false
    override fun layoutId() = R.layout.activity_contact
    override fun initView(savedInstanceState: Bundle?) {
        initTvFunctionHead(
            getString(R.string.title_modify_contact),
            getString(R.string.add_contact)
        )
        if (intent.hasExtra("forResult")) {
            isForResult = intent.getBooleanExtra("forResult", false)
        }
        statusLayoutManager = StatusLayoutHelper.getDefaultStatusManager(recyclerView, this, this)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = ContactAdapter(R.layout.item_contact)
        recyclerView.adapter = adapter
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            val bean: ContactBean.AddresslistBean = adapter.data[position] as ContactBean.AddresslistBean
            if (view.id === R.id.modify_spb) {
                LaunchConfig.startAddContactAc(
                    this,
                    AddContactActivity.MODIFY,
                    bean.id,
                    bean.addressHash,
                    bean.label
                )
            } else if (view.id === R.id.del_spb) {
                UYTAlertDialog(this)
                    .setTitle(getString(R.string.hint))
                    .setMsg(getString(R.string.whether_del) + " " + bean.label + " ?")
                    .setPositiveButton(
                        getString(R.string.confirm),
                        View.OnClickListener { delContact(bean.id) })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
            }
        }
        adapter?.setOnItemClickListener { adapter, view, position ->
            if (isForResult) {
                val item: ContactBean.AddresslistBean? = adapter.getItem(position) as ContactBean.AddresslistBean?
                val intent = Intent()
                intent.putExtra(
                    "contact",
                    if (item?.addressHash == null) "" else item.addressHash
                )
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun initData() {
        getContact()
    }
    override fun functionCall() {
        LaunchConfig.startAddContactAc(this, AddContactActivity.ADD, -1, null, null)
    }

   override fun onEvent(event: LocalEvent<Any>) {
        if (event.status_type.equals(LocalEvent.reLoadContact)) {
            getContact()
        }
    }

    //删除联系人
    private fun delContact(contactId: Int) {
        viewModel.delContact(contactId).observe(this, Observer {
            if (it.status == 200) {
                ToastUtils.showShort(getActivity(), getString(R.string.del_success))
                getContact()
            } else {
                ToastUtils.showShort(getActivity(), it.errorMsg)
            }


        })
    }

    //获取联系人
    private fun getContact() {
        viewModel.getContacts().observe(this, Observer {
            if (it.status === 200) {
                if (it.data == null || it.data.addresslist.isNullOrEmpty()
                ) {
                    statusLayoutManager!!.showEmptyLayout()
                } else {
                    statusLayoutManager!!.showSuccessLayout()
                    adapter!!.setNewData(it.data.addresslist)
                }
            } else {
                ToastUtils.showShort(this, it.errorMsg)
                statusLayoutManager!!.showErrorLayout()
            }


        })
    }

    override fun onEmptyChildClick(view: View?) {
        getContact()
    }

    override fun onErrorChildClick(view: View?) {
        getContact()
    }

    override fun onCustomerChildClick(view: View?) {}
}