package com.kopernik.app.widget.statuslayoutmanager

import android.view.View

/**
 * @author Bakumon
 * @date 2017/12/22
 * @see OnStatusChildClickListener 的默认实现类
 *
 * @since v1.0.1
 */
class DefaultOnStatusChildClickListener : OnStatusChildClickListener {
    override fun onEmptyChildClick(view: View?) {}
    override fun onErrorChildClick(view: View?) {}
    override fun onCustomerChildClick(view: View?) {}
}