package com.kopernik.app.factory

import androidx.annotation.NonNull
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by sand on 2018/10/25.
 */
interface IViewModel {
    fun delayByString(
        seconds: Int,
        params: String
    ): Observable<String>? {
        return Observable.just(params)
            .delay(seconds.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delayByInt(
        seconds: Int,
        params: Int
    ): Observable<Int>? {
        return Observable.just(params)
            .delay(seconds.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun clearDisposable() {
        mCompositeDisposable.clear()
    }

    fun addDisposable(@NonNull disposable: Disposable?): Boolean {
        return mCompositeDisposable.add(disposable)
    }

    fun removeDisposable(@NonNull disposable: Disposable?): Boolean {
        return mCompositeDisposable.remove(disposable)
    }

    fun onDestroy()

    companion object {
        val mCompositeDisposable = CompositeDisposable()
    }
}