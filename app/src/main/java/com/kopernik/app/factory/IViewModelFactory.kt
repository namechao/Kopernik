package com.kopernik.app.factory

import com.kopernik.ui.account.viewmodel.SplashViewModel
import com.kopernik.ui.setting.viewModel.DefaultViewModel


interface IViewModelFactory {
    val splashViewModel: SplashViewModel?
    val defaultViewModel: DefaultViewModel?
}