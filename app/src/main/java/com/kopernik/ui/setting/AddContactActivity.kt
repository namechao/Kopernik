package com.kopernik.ui.setting

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.KeyboardUtils
import com.kopernik.app.utils.ToastUtils
import com.kopernik.ui.setting.viewModel.AddContactViewModel
import com.xuexiang.xqrcode.XQRCode
import kotlinx.android.synthetic.main.activity_add_contact.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

class AddContactActivity : NewBaseActivity<AddContactViewModel, ViewDataBinding>(),
    PermissionCallbacks {

    companion object {

        const val ADD = 1
        const val MODIFY = 2
        private const val REQUEST_CODE = 1
        private const val REQUEST_CODE_QRCODE_PERMISSIONS = 2
    }

    private var addressEt: EditText? = null
    private var remarkEt: EditText? = null
    private var scanIv: ImageView? = null
    private var contactId = 0
    private var type = 0
    private val perms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun layoutId()=R.layout.activity_add_contact

    override fun initView(savedInstanceState: Bundle?) {
        type = intent.getIntExtra("type", -1)
        if (type == -1) finish()
        setTitle(getString(R.string.add_contact_addr))
        addressEt = addressInputLL.findViewById(R.id.input_et)
        remarkEt = remarkInputLL.findViewById(R.id.input_et)
        scanIv = addressInputLL.findViewById(R.id.input_clear_iv)

        addressEt?.hint = getString(R.string.add_contact_account_addr)
        remarkEt?.hint = getString(R.string.add_contact_name)

        addressInputLL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE
        remarkInputLL.findViewById<TextView>(R.id.edit_require_tv).visibility = View.GONE

        addressEt?.addTextChangedListener(textWatcher)
        remarkEt?.addTextChangedListener(textWatcher)

        scanIv?.setImageResource(R.mipmap.icon_camera)
        scanIv?.visibility = View.VISIBLE
        addressEt?.maxLines = 2
        if (type == AddContactActivity.MODIFY) {
            addressEt?.setText(intent.getStringExtra("address"))
            remarkEt?.setText(intent.getStringExtra("remark"))
            contactId = intent.getIntExtra("contactId", -1)
        }
        okBtn.setOnClickListener { v: View? ->
            if (remarkEt?.text.toString().length > 12) {
                ToastUtils.showShort(getActivity(), getString(R.string.add_contact_name_error))
                return@setOnClickListener
            }
            //TODO 这边加键盘管理
            KeyboardUtils.hideSoftKeyboard(this)
            modifyContact()
        }
        scanIv?.setOnClickListener { v: View? ->
            if (!EasyPermissions.hasPermissions(this, *perms)) {
                requestCodeQRCodePermissions()
                return@setOnClickListener
            }
            XQRCode.startScan(this, REQUEST_CODE)
        }
    }

    //通过type判断是够为添加 还是修改
    private fun modifyContact() {
        var map = mutableMapOf(
            "label" to remarkEt?.text.toString().trim(),
            "chain" to "UYT",
            "addressHash" to addressEt!!.text.toString().trim()
        )
        if (type == MODIFY) {
            map["id"] = contactId.toString()
        }
        viewModel.addContact(map).observe(this, Observer {
            if (it.status == 200) {
                if (type == ADD) {
                    ToastUtils.showShort(
                        getActivity(),
                        resources.getString(R.string.add_success)
                    )
                } else {
                    ToastUtils.showShort(
                        getActivity(),
                        resources.getString(R.string.tip_change_success)
                    )
                }
                mEventBus.post(LocalEvent<Any>(LocalEvent.reLoadContact))
                finish()
            } else {
                ToastUtils.showShort(getActivity(), it.errorMsg)
            }
        })
    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            if (addressEt!!.text.toString().isNotEmpty()) {
                scanIv!!.visibility = View.GONE
            } else {
                scanIv!!.visibility = View.VISIBLE
            }
            okBtn.isEnabled = addressEt!!.text.toString().isNotEmpty() && remarkEt!!.text.toString().isNotEmpty()
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            handleScanResult(data)
        }
    }

    /**
     * 处理二维码扫描结果
     * @param data
     */
    private fun handleScanResult(data: Intent?) {
        if (data != null) {
            val bundle = data.extras
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    val result = bundle.getString(XQRCode.RESULT_DATA)
                    addressEt!!.setText(result)
                    scanIv!!.visibility = View.GONE
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    ToastUtils.showShort(getActivity(), getString(R.string.decode_qrcode_error))
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults, this
        )
    }

   override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        XQRCode.startScan(this, REQUEST_CODE)
    }

   override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.qrcode_permissions),
            REQUEST_CODE_QRCODE_PERMISSIONS,
            perms.toString()
        )
    }
    override fun initData() {

    }
}