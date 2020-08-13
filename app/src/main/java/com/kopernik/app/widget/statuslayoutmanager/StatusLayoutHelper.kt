package com.kopernik.app.widget.statuslayoutmanager

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.kopernik.R

object StatusLayoutHelper {
    /**
     * 空界面按钮 隐藏
     * 出错按钮  显示
     * @param view
     * @param context
     * @param listener
     * @return
     */
    fun getDefaultStatusManager(
        view: View?,
        context: Context,
        listener: OnStatusChildClickListener?
    ): StatusLayoutManager {
        return StatusLayoutManager.Builder(view!!)
            .setDefaultLoadingText(context.resources.getString(R.string.loading))
            .setDefaultEmptyText(context.resources.getString(R.string.empty_data))
            .setDefaultEmptyImg(R.mipmap.status_layout_manager_ic_empty)
            .setDefaultEmptyClickViewText(context.resources.getString(R.string.retry2))
            .setDefaultEmptyClickViewTextColor(
                context.resources.getColor(R.color.status_bar)
            )
            .setDefaultEmptyClickViewVisible(false)
            .setOnStatusChildClickListener(listener)
            .setDefaultErrorText(context.resources.getString(R.string.loading_error))
            .setDefaultErrorImg(R.mipmap.status_layout_manager_ic_error)
            .setDefaultErrorClickViewText(context.resources.getString(R.string.retry))
            .setDefaultErrorClickViewTextColor(
                context.resources.getColor(R.color.status_bar)
            )
            .setDefaultErrorClickViewVisible(true)
            .setDefaultLayoutsBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            .build()
    }

    fun getDefaultStatusManager2(
        view: View?,
        context: Context,
        listener: OnStatusChildClickListener?
    ): StatusLayoutManager {
        return StatusLayoutManager.Builder(view!!)
            .setDefaultLoadingText(context.resources.getString(R.string.loading))
            .setDefaultEmptyText(context.resources.getString(R.string.empty_data))
            .setDefaultEmptyImg(R.mipmap.status_layout_manager_ic_empty)
            .setDefaultEmptyClickViewText(context.resources.getString(R.string.retry2))
            .setDefaultEmptyClickViewTextColor(
                context.resources.getColor(R.color.status_bar)
            )
            .setDefaultEmptyClickViewVisible(true)
            .setOnStatusChildClickListener(listener)
            .setDefaultErrorText(context.resources.getString(R.string.loading_error))
            .setDefaultErrorImg(R.mipmap.status_layout_manager_ic_error)
            .setDefaultErrorClickViewText(context.resources.getString(R.string.retry))
            .setDefaultErrorClickViewTextColor(
                context.resources.getColor(R.color.status_bar)
            )
            .setDefaultErrorClickViewVisible(true)
            .setDefaultLayoutsBackgroundColor(Color.WHITE)
            .build()
    }
}