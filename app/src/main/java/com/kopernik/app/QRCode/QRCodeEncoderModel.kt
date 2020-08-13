package com.kopernik.app.QRCode

import android.graphics.Bitmap
import com.xuexiang.xqrcode.XQRCode
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

object QRCodeEncoderModel {
    /**
     * 生成二维码
     * @param content
     * @return
     */
    fun EncodeQRCode(content: String?): Observable<Bitmap?> {
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<Bitmap?> ->
            emitter.onNext(
                XQRCode.createQRCodeWithLogo(content, null)
            )
        }).subscribeOn(Schedulers.io())
    }
}