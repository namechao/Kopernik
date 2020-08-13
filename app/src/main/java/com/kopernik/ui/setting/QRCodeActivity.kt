package com.kopernik.ui.setting

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.aleyn.mvvm.base.NoViewModel
import com.kopernik.R
import com.kopernik.app.QRCode.QRCodeEncoderModel
import com.kopernik.app.base.NewBaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_q_r_code.*

class QRCodeActivity : NewBaseActivity<NoViewModel,ViewDataBinding>() {
    private var disposable: Disposable? = null
    override fun layoutId()=R.layout.activity_q_r_code

    override fun initView(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.title_qr_code))
        val content = intent.getStringExtra("content")
        if (content == null) {
            finish()
        } else {
            disposable = QRCodeEncoderModel.EncodeQRCode(content)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { bitmap -> qrcodeIv.setImageBitmap(bitmap) }
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) disposable!!.dispose()
    }
}