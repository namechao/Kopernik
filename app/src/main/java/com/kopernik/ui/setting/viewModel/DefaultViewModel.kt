package com.kopernik.ui.setting.viewModel

import com.kopernik.app.factory.IViewModel

class DefaultViewModel : IViewModel {
   override fun onDestroy() {
        clearDisposable()
    }
}