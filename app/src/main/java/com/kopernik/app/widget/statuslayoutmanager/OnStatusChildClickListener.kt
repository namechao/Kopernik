package com.kopernik.app.widget.statuslayoutmanager

import android.view.View

/**
 * 状态布局中 view 的点击事件
 *
 * @author Bakumon
 * @date 2017/12/19
 * @since v1.0.0
 */
interface OnStatusChildClickListener {
    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    fun onEmptyChildClick(view: View?)

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    fun onErrorChildClick(view: View?)

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    fun onCustomerChildClick(view: View?)
}