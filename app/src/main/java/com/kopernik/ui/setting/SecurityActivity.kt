package com.kopernik.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyProperties
import android.view.View
import android.widget.CheckBox
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.base.NewBaseActivity
import com.kopernik.app.config.LaunchConfig
import com.kopernik.app.config.UserConfig
import com.kopernik.app.dialog.FingerprintDialog
import com.kopernik.app.dialog.UYTAlertDialog
import com.kopernik.app.dialog.UYTAlertDialog2
import com.kopernik.app.events.LocalEvent
import com.kopernik.app.utils.ToastUtils
import com.kopernik.app.utils.fingerprint.FingerprintHelper
import com.kopernik.ui.asset.util.OnClickFastListener
import kotlinx.android.synthetic.main.activity_security.*

class SecurityActivity : NewBaseActivity<NoViewModel,ViewDataBinding>(),
    FingerprintDialog.AuthenticationCallback {
    private var fingerprintCb: CheckBox? = null
    override fun layoutId()= R.layout.activity_security

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_safe_center))
        mEventBus.post(LocalEvent<Any>(LocalEvent.openSetting))
        modifyNickSpt.setOnClickListener({ v -> LaunchConfig.startModifyNickAc(this )})
        modifyPassSpt.setOnClickListener({ v -> LaunchConfig.startModifyPasswordAc(this) })
        exportWordsSpt.setOnClickListener({ v -> LaunchConfig.startExportMnemonicAc(this) })

        fingerprintCb = fingerprintSpt.getCheckBox()
//        UserConfig.singleton?.isUseFingerprint?.let { fingerprintCb?.setChecked(it) }
        //0 支持指纹但是没有录入指纹； 1：有可用指纹； -1，手机不支持指纹
        FingerprintHelper.getInstance().init(getActivity())
        if (UserConfig.singleton?.accountBean == null ||
            FingerprintHelper.getInstance().checkFingerprintAvailable(getActivity()) == -1
        ) {
            fingerprintSpt.setVisibility(View.GONE)
        }
        fingerprintCb?.setOnClickListener(useFingerprint)
        fingerprintSpt?.setOnClickListener(useFingerprint)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    var useFingerprint =
        View.OnClickListener {
//            fingerprintCb!!.isChecked = UserConfig.singleton?.isUseFingerprint!!
            if (FingerprintHelper.getInstance()
                    .checkFingerprintAvailable(getActivity()) == 0
            ) {
                UYTAlertDialog2(this)
                    .setTitle(getString(R.string.hint))
                    .setMsg(getString(R.string.no_input_fingerprint))
                    .setButton(getString(R.string.confirm), null)
                    .show()
                return@OnClickListener
            }
//            if (!UserConfig.singleton?.isUseFingerprint!!) {
//                LaunchConfig.startVerifyPwAcForResult(this, 100)
//            } else {
//                UYTAlertDialog(this)
//                    .setTitle(getString(R.string.hint))
//                    .setMsg(getString(R.string.close_fingerprint_notice))
//                    .setNegativeButton(getString(R.string.cancel), null)
//                    .setPositiveButton(
//                        getString(R.string.not_in_service),
//                        object : OnClickFastListener() {
//                            override fun onFastClick(v: View?) {
//                                FingerprintHelper.getInstance().init(this@SecurityActivity)
//                                FingerprintHelper.getInstance().closeAuthenticate()
//                                UserConfig.singleton?.isUseFingerprint=false
//                                fingerprintCb!!.isChecked = false
//                                ToastUtils.showShort(
//                                    getActivity(),
//                                    getString(R.string.stop_success)
//                                )
//                            }
//                        })
//                    .show()
//            }
        }

    override fun initData() {

    }
    override fun onResume() {
        super.onResume()
//        fingerprintCb!!.isChecked = UserConfig.singleton?.isUseFingerprint!!
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data?.hasExtra("pw")!!) {
                val dialog: FingerprintDialog =
                    FingerprintDialog.newInstance(1, data.getStringExtra("pw"))
                dialog.setAuthenticationCallback(this)
                dialog.show(supportFragmentManager, "fingerprint")
            }
        }
    }

   override fun onAuthenticationSucceeded(purpose: Int, value: String) {
        if (purpose == KeyProperties.PURPOSE_ENCRYPT) {
//            UserConfig.singleton?.isUseFingerprint=true
            fingerprintCb!!.isChecked = true
            ToastUtils.showShort(getActivity(), getString(R.string.use_fingerprint_success))
        }
    }
}