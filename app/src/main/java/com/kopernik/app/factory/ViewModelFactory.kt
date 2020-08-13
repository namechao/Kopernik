package com.kopernik.app.factory

/**
 * Created by sand on 2018/10/25.
 */
object ViewModelFactory {
    private val mFactory: IViewModelFactory = RootViewModelFactory()
    fun factory(): IViewModelFactory {
        return mFactory
    }
}