package com.kopernik.app.widget.statuslayoutmanager

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import com.kopernik.R

/**
 * 状态布局管理器
 *
 * @author Bakumon
 * @date 2017/12/18
 * @since v1.0.0
 */
class StatusLayoutManager private constructor(builder: Builder) {
    private val contentLayout: View

    @LayoutRes
    private val loadingLayoutID: Int
    private var loadingLayout: View?
    private val loadingText: String?

    @IdRes
    private val emptyClickViewId: Int

    @LayoutRes
    private val emptyLayoutID: Int
    private var emptyLayout: View?
    private val emptyText: String?
    private val emptyClickViewText: String?
    private val emptyClickViewTextColor: Int
    private val isEmptyClickViewVisible: Boolean

    @DrawableRes
    private val emptyImgID: Int

    @IdRes
    private val errorClickViewId: Int

    @LayoutRes
    private val errorLayoutID: Int
    private var errorLayout: View?
    private val errorText: String?
    private val errorClickViewText: String?
    private val errorClickViewTextColor: Int
    private val isErrorClickViewVisible: Boolean

    @DrawableRes
    private val errorImgID: Int
    private val defaultBackgroundColor: Int
    private val onStatusChildClickListener: OnStatusChildClickListener?
    private val replaceLayoutHelper: ReplaceLayoutHelper
    private var inflater: LayoutInflater? = null
    private fun inflate(@LayoutRes resource: Int): View {
        if (inflater == null) {
            inflater = LayoutInflater.from(contentLayout.context)
        }
        return inflater!!.inflate(resource, null)
    }
    ///////////////////////////////////////////
    /////////////////原有布局////////////////////
    ///////////////////////////////////////////
    /**
     * 显示原有布局
     *
     * @since v1.0.0
     */
    fun showSuccessLayout() {
        replaceLayoutHelper.restoreLayout()
    }
    ///////////////////////////////////////////
    ////////////////加载中布局///////////////////
    ///////////////////////////////////////////
    /**
     * 创建加载中布局
     */
    private fun createLoadingLayout() {
        if (loadingLayout == null) {
            loadingLayout = inflate(loadingLayoutID)
        }
        if (loadingLayoutID == DEFAULT_LOADING_LAYOUT_ID) {
            loadingLayout!!.setBackgroundColor(defaultBackgroundColor)
        }
        if (!TextUtils.isEmpty(loadingText)) {
            val loadingTextView =
                loadingLayout!!.findViewById<TextView>(R.id.status_layout_manager_tv_status_loading_content)
            if (loadingTextView != null) {
                loadingTextView.text = loadingText
            }
        }
    }

    /**
     * 获取加载中布局
     *
     * @return 加载中布局
     * @since v1.0.0
     */
    fun getLoadingLayout(): View? {
        createLoadingLayout()
        return loadingLayout
    }

    /**
     * 显示加载中布局
     *
     * @since v1.0.0
     */
    fun showLoadingLayout() {
        createLoadingLayout()
        replaceLayoutHelper.showStatusLayout(loadingLayout)
    }
    ///////////////////////////////////////////
    ////////////////空数据布局///////////////////
    ///////////////////////////////////////////
    /**
     * 创建空数据布局
     */
    private fun createEmptyLayout() {
        if (emptyLayout == null) {
            emptyLayout = inflate(emptyLayoutID)
        }
        if (emptyLayoutID == DEFAULT_EMPTY_LAYOUT_ID) {
            emptyLayout!!.setBackgroundColor(defaultBackgroundColor)
        }

        // 点击事件回调
        val view = emptyLayout!!.findViewById<View>(emptyClickViewId)
        if (view != null && onStatusChildClickListener != null) {
            // 设置点击按钮点击时事件回调
            view.setOnClickListener { view -> onStatusChildClickListener.onEmptyChildClick(view) }
        }

        // 设置默认空数据布局的提示文本
        if (!TextUtils.isEmpty(emptyText)) {
            val emptyTextView =
                emptyLayout!!.findViewById<TextView>(R.id.status_layout_manager_tv_status_empty_content)
            if (emptyTextView != null) {
                emptyTextView.text = emptyText
            }
        }

        // 设置默认空数据布局的图片
        val emptyImageView =
            emptyLayout!!.findViewById<ImageView>(R.id.status_layout_manager_iv_status_empty_img)
        emptyImageView?.setImageResource(emptyImgID)
        val emptyClickViewTextView =
            emptyLayout!!.findViewById<TextView>(DEFAULT_EMPTY_CLICKED_ID)
        if (emptyClickViewTextView != null) {
            // 设置点击按钮的文本和可见性
            if (isEmptyClickViewVisible) {
                emptyClickViewTextView.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(emptyClickViewText)) {
                    emptyClickViewTextView.text = emptyClickViewText
                }
                emptyClickViewTextView.setTextColor(emptyClickViewTextColor)
            } else {
                emptyClickViewTextView.visibility = View.GONE
            }
        }
    }

    /**
     * 获取空数据布局
     *
     * @return 空数据布局
     * @since v1.0.0
     */
    fun getEmptyLayout(): View? {
        createEmptyLayout()
        return emptyLayout
    }

    /**
     * 显示空数据布局
     *
     * @since v1.0.0
     */
    fun showEmptyLayout() {
        createEmptyLayout()
        replaceLayoutHelper.showStatusLayout(emptyLayout)
    }
    ///////////////////////////////////////////
    /////////////////出错布局////////////////////
    ///////////////////////////////////////////
    /**
     * 创建出错布局
     */
    private fun createErrorLayout() {
        if (errorLayout == null) {
            errorLayout = inflate(errorLayoutID)
        }
        if (errorLayoutID == DEFAULT_ERROR_LAYOUT_ID) {
            errorLayout!!.setBackgroundColor(defaultBackgroundColor)
        }
        val view = errorLayout!!.findViewById<View>(errorClickViewId)
        if (view != null && onStatusChildClickListener != null) {
            // 设置点击按钮点击时事件回调
            view.setOnClickListener { view -> onStatusChildClickListener.onErrorChildClick(view) }
        }

        // 设置默认出错布局的提示文本
        if (!TextUtils.isEmpty(errorText)) {
            val errorTextView =
                errorLayout!!.findViewById<TextView>(R.id.status_layout_manager_tv_status_error_content)
            if (errorTextView != null) {
                errorTextView.text = errorText
            }
        }

        // 设置默认出错布局的图片
        val errorImageView =
            errorLayout!!.findViewById<ImageView>(R.id.status_layout_manager_iv_status_error_image)
        errorImageView?.setImageResource(errorImgID)
        val errorClickViewTextView =
            errorLayout!!.findViewById<TextView>(DEFAULT_ERROR_CLICKED_ID)
        if (errorClickViewTextView != null) {
            // 设置点击按钮的文本和可见性
            if (isErrorClickViewVisible) {
                errorClickViewTextView.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(errorClickViewText)) {
                    errorClickViewTextView.text = errorClickViewText
                }
                errorClickViewTextView.setTextColor(errorClickViewTextColor)
            } else {
                errorClickViewTextView.visibility = View.GONE
            }
        }
    }

    /**
     * 获取出错布局
     *
     * @return 出错布局
     * @since v1.0.0
     */
    fun getErrorLayout(): View? {
        createErrorLayout()
        return errorLayout
    }

    /**
     * 显示出错布局
     *
     * @since v1.0.0
     */
    fun showErrorLayout() {
        createErrorLayout()
        replaceLayoutHelper.showStatusLayout(errorLayout)
    }
    ///////////////////////////////////////////
    ////////////////自定义布局///////////////////
    ///////////////////////////////////////////
    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @since v1.0.0
     */
    fun showCustomLayout(customLayout: View) {
        replaceLayoutHelper.showStatusLayout(customLayout)
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义状态布局 ID
     * @return 通过 customLayoutID 生成的 View
     * @since v1.0.0
     */
    fun showCustomLayout(@LayoutRes customLayoutID: Int): View {
        val customerView = inflate(customLayoutID)
        showCustomLayout(customerView)
        return customerView
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayout 自定义布局
     * @param clickViewID  可点击 View ID
     * @since v1.0.0
     */
    fun showCustomLayout(
        customLayout: View,
        @IdRes vararg clickViewID: Int
    ) {
        replaceLayoutHelper.showStatusLayout(customLayout)
        if (onStatusChildClickListener == null) {
            return
        }
        for (aClickViewID in clickViewID) {
            val clickView =
                customLayout.findViewById<View>(aClickViewID) ?: return

            // 设置点击按钮点击时事件回调
            clickView.setOnClickListener { view ->
                onStatusChildClickListener.onCustomerChildClick(
                    view
                )
            }
        }
    }

    /**
     * 显示自定义状态布局
     *
     * @param customLayoutID 自定义布局 ID
     * @param clickViewID    点击按钮 ID
     * @since v1.0.0
     */
    fun showCustomLayout(
        @LayoutRes customLayoutID: Int,
        @IdRes vararg clickViewID: Int
    ): View {
        val customLayout = inflate(customLayoutID)
        showCustomLayout(customLayout, *clickViewID)
        return customLayout
    }

    class Builder( val contentLayout: View) {

        @LayoutRes
         var loadingLayoutID: Int
         var loadingLayout: View? = null
         var loadingText: String? = null

        @IdRes
         var emptyClickViewId: Int

        @LayoutRes
         var emptyLayoutID: Int
         var emptyLayout: View? = null
         var emptyText: String? = null
         var emptyClickViewText: String? = null
         var emptyClickViewTextColor: Int
         var isEmptyClickViewVisible: Boolean

        @DrawableRes
         var emptyImgID: Int

        @IdRes
         var errorClickViewId: Int

        @LayoutRes
         var errorLayoutID: Int
         var errorLayout: View? = null
         var errorText: String? = null
         var errorClickViewText: String? = null
         var errorClickViewTextColor: Int
         var isErrorClickViewVisible: Boolean

        @DrawableRes
         var errorImgID: Int
         var defaultBackgroundColor: Int
         var onStatusChildClickListener: OnStatusChildClickListener? = null
        ///////////////////////////////////////////
        ////////////////加载中布局///////////////////
        ///////////////////////////////////////////
        /**
         * 设置加载中布局
         *
         * @param loadingLayoutID 加载中布局 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setLoadingLayout(@LayoutRes loadingLayoutID: Int): Builder {
            this.loadingLayoutID = loadingLayoutID
            return this
        }

        /**
         * 设置加载中布局
         *
         * @param loadingLayout 加载中布局
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setLoadingLayout(loadingLayout: View): Builder {
            this.loadingLayout = loadingLayout
            return this
        }

        /**
         * 设置默认加载中布局提示文本
         *
         * @param loadingText 加载中布局提示文本
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultLoadingText(loadingText: String?): Builder {
            this.loadingText = loadingText
            return this
        }

        /**
         * 设置默认加载中布局提示文本
         *
         * @param loadingTextStrID 加载中布局提示文本 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultLoadingText(@StringRes loadingTextStrID: Int): Builder {
            loadingText = contentLayout.context.resources.getString(loadingTextStrID)
            return this
        }
        ///////////////////////////////////////////
        ////////////////空数据布局///////////////////
        ///////////////////////////////////////////
        /**
         * 设置空数据布局
         *
         * @param emptyLayoutResId 空数据布局 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setEmptyLayout(@LayoutRes emptyLayoutResId: Int): Builder {
            emptyLayoutID = emptyLayoutResId
            return this
        }

        /**
         * 设置空数据布局
         *
         * @param emptyLayout 空数据布局
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setEmptyLayout(emptyLayout: View): Builder {
            this.emptyLayout = emptyLayout
            return this
        }

        /**
         * 设置空数据布局点击按钮 ID
         *
         * @param emptyClickViewResId 空数据布局点击按钮 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setEmptyClickViewID(@IdRes emptyClickViewResId: Int): Builder {
            emptyClickViewId = emptyClickViewResId
            return this
        }

        /**
         * 设置默认空数据布局点击按钮文本
         *
         * @param emptyClickViewText 点击按钮文本
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyClickViewText(emptyClickViewText: String?): Builder {
            this.emptyClickViewText = emptyClickViewText
            return this
        }

        /**
         * 设置默认空数据布局点击按钮文本
         *
         * @param emptyClickViewTextID 点击按钮文本 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyClickViewText(@StringRes emptyClickViewTextID: Int): Builder {
            emptyClickViewText =
                contentLayout.context.resources.getString(emptyClickViewTextID)
            return this
        }

        /**
         * 设置默认空数据布局点击按钮文本颜色
         *
         * @param emptyClickViewTextColor 点击按钮文本颜色
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyClickViewTextColor(emptyClickViewTextColor: Int): Builder {
            this.emptyClickViewTextColor = emptyClickViewTextColor
            return this
        }

        /**
         * 设置默认空数据布局点击按钮是否可见
         *
         * @param isEmptyClickViewVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyClickViewVisible(isEmptyClickViewVisible: Boolean): Builder {
            this.isEmptyClickViewVisible = isEmptyClickViewVisible
            return this
        }

        /**
         * 设置空数据布局图片
         *
         * @param emptyImgID 空数据布局图片 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyImg(@DrawableRes emptyImgID: Int): Builder {
            this.emptyImgID = emptyImgID
            return this
        }
        ///////////////////////////////////////////
        /////////////////出错布局////////////////////
        ///////////////////////////////////////////
        /**
         * 设置空数据布局提示文本
         *
         * @param emptyText 空数据布局提示文本
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyText(emptyText: String?): Builder {
            this.emptyText = emptyText
            return this
        }

        /**
         * 设置空数据布局提示文本
         *
         * @param emptyTextStrID 空数据布局提示文本 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultEmptyText(@StringRes emptyTextStrID: Int): Builder {
            emptyText = contentLayout.context.resources.getString(emptyTextStrID)
            return this
        }

        /**
         * 设置出错布局
         *
         * @param errorLayoutResId 出错布局 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setErrorLayout(@LayoutRes errorLayoutResId: Int): Builder {
            errorLayoutID = errorLayoutResId
            return this
        }

        /**
         * 设置出错布局
         *
         * @param errorLayout 出错布局
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setErrorLayout(errorLayout: View): Builder {
            this.errorLayout = errorLayout
            return this
        }

        /**
         * 设置出错布局点击按钮 ID
         *
         * @param errorClickViewResId 出错布局点击按钮 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setErrorClickViewID(@IdRes errorClickViewResId: Int): Builder {
            errorClickViewId = errorClickViewResId
            return this
        }

        /**
         * 设置出错布局提示文本
         *
         * @param errorText 出错布局提示文本
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorText(errorText: String?): Builder {
            this.errorText = errorText
            return this
        }

        /**
         * 设置出错布局提示文本
         *
         * @param errorTextStrID 出错布局提示文本 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorText(@StringRes errorTextStrID: Int): Builder {
            errorText = contentLayout.context.resources.getString(errorTextStrID)
            return this
        }

        /**
         * 设置默认出错布局点击按钮文本
         *
         * @param errorClickViewText 点击按钮文本
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorClickViewText(errorClickViewText: String?): Builder {
            this.errorClickViewText = errorClickViewText
            return this
        }

        /**
         * 设置默认出错布局点击按钮文本
         *
         * @param errorClickViewTextID 点击按钮文本 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorClickViewText(@StringRes errorClickViewTextID: Int): Builder {
            errorClickViewText =
                contentLayout.context.resources.getString(errorClickViewTextID)
            return this
        }

        /**
         * 设置默认出错布局点击按钮文本颜色
         *
         * @param errorClickViewTextColor 点击按钮文本颜色
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorClickViewTextColor(errorClickViewTextColor: Int): Builder {
            this.errorClickViewTextColor = errorClickViewTextColor
            return this
        }

        /**
         * 设置出错布局点击按钮可见行
         *
         * @param isErrorClickViewVisible true：可见 false：不可见
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorClickViewVisible(isErrorClickViewVisible: Boolean): Builder {
            this.isErrorClickViewVisible = isErrorClickViewVisible
            return this
        }

        /**
         * 设置出错布局图片
         *
         * @param errorImgID 出错布局图片 ID
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultErrorImg(@DrawableRes errorImgID: Int): Builder {
            this.errorImgID = errorImgID
            return this
        }

        /**
         * 设置默认布局的背景颜色，包括加载中、空数据和出错布局
         *
         * @param defaultBackgroundColor 默认布局的背景颜色
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setDefaultLayoutsBackgroundColor(defaultBackgroundColor: Int): Builder {
            this.defaultBackgroundColor = defaultBackgroundColor
            return this
        }

        /**
         * 设置点击事件监听器
         *
         * @param listener 点击事件监听器
         * @return 状态布局 Build 对象
         * @since v1.0.0
         */
        fun setOnStatusChildClickListener(listener: OnStatusChildClickListener?): Builder {
            onStatusChildClickListener = listener
            return this
        }

        /**
         * 创建状态布局管理器
         *
         * @return 状态布局管理器
         * @since v1.0.0
         */
        @CheckResult
        fun build(): StatusLayoutManager {
            return StatusLayoutManager(this)
        }

        /**
         * 创建状态布局 Build 对象
         *
         * @param contentLayout 原有布局，内容布局
         * @since v1.0.0
         */
        init {
            // 设置默认布局
            loadingLayoutID = DEFAULT_LOADING_LAYOUT_ID
            emptyLayoutID = DEFAULT_EMPTY_LAYOUT_ID
            errorLayoutID = DEFAULT_ERROR_LAYOUT_ID
            // 默认布局图片
            emptyImgID = DEFAULT_EMPTY_IMG_ID
            errorImgID = DEFAULT_ERROR_IMG_ID
            // 设置默认点击点击view id
            emptyClickViewId = DEFAULT_EMPTY_CLICKED_ID
            errorClickViewId = DEFAULT_ERROR_CLICKED_ID
            // 设置默认点击按钮属性
            isEmptyClickViewVisible = true
            emptyClickViewTextColor = contentLayout.context.resources
                .getColor(DEFAULT_CLICKED_TEXT_COLOR)
            isErrorClickViewVisible = true
            errorClickViewTextColor = contentLayout.context.resources
                .getColor(DEFAULT_CLICKED_TEXT_COLOR)
            // 设置默认背景色
            defaultBackgroundColor = contentLayout.context.resources
                .getColor(DEFAULT_BACKGROUND_COLOR)
        }
    }

    companion object {
        /**
         * 三种默认布局 ID
         */
        private const val DEFAULT_LOADING_LAYOUT_ID =
            R.layout.layout_status_layout_manager_loading
        private const val DEFAULT_EMPTY_LAYOUT_ID =
            R.layout.layout_status_layout_manager_empty
        private const val DEFAULT_ERROR_LAYOUT_ID =
            R.layout.layout_status_layout_manager_error

        /**
         * 默认布局中可点击的 view ID
         */
        private const val DEFAULT_EMPTY_CLICKED_ID =
            R.id.status_layout_manager_bt_status_empty_click
        private const val DEFAULT_ERROR_CLICKED_ID =
            R.id.status_layout_manager_bt_status_error_click

        /**
         * 默认颜色
         */
        private const val DEFAULT_CLICKED_TEXT_COLOR =
            R.color.status_layout_manager_click_view_text_color
        private const val DEFAULT_BACKGROUND_COLOR =
            R.color.status_layout_manager_background_color

        /**
         * 默认图片
         */
        private const val DEFAULT_EMPTY_IMG_ID = R.mipmap.status_layout_manager_ic_empty
        private const val DEFAULT_ERROR_IMG_ID = R.mipmap.status_layout_manager_ic_error
    }

    init {
        contentLayout = builder.contentLayout
        loadingLayoutID = builder.loadingLayoutID
        loadingLayout = builder.loadingLayout
        loadingText = builder.loadingText
        emptyClickViewId = builder.emptyClickViewId
        emptyLayoutID = builder.emptyLayoutID
        emptyLayout = builder.emptyLayout
        emptyText = builder.emptyText
        emptyClickViewText = builder.emptyClickViewText
        emptyClickViewTextColor = builder.emptyClickViewTextColor
        isEmptyClickViewVisible = builder.isEmptyClickViewVisible
        emptyImgID = builder.emptyImgID
        errorClickViewId = builder.errorClickViewId
        errorLayoutID = builder.errorLayoutID
        errorLayout = builder.errorLayout
        errorText = builder.errorText
        errorClickViewText = builder.errorClickViewText
        errorClickViewTextColor = builder.errorClickViewTextColor
        isErrorClickViewVisible = builder.isErrorClickViewVisible
        errorImgID = builder.errorImgID
        defaultBackgroundColor = builder.defaultBackgroundColor
        onStatusChildClickListener = builder.onStatusChildClickListener
        replaceLayoutHelper = ReplaceLayoutHelper(contentLayout)
    }
}