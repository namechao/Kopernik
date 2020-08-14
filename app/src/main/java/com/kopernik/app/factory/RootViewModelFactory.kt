package com.kopernik.app.factory

import com.kopernik.ui.login.viewmodel.SplashViewModel
import com.kopernik.ui.setting.viewModel.DefaultViewModel


class RootViewModelFactory : IViewModelFactory {
    override val splashViewModel: SplashViewModel
        get() = SplashViewModel(-1)

    override val defaultViewModel: DefaultViewModel
        get() = DefaultViewModel()
}