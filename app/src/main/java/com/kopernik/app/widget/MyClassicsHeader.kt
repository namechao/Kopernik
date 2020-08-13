package com.kopernik.app.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.kopernik.R

class MyClassicsHeader @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ClassicsHeader(context, attrs, defStyleAttr) {
    private var mAccentColor = 0
    private var mPrimaryColor = 0

    //这个方法是设置header里面字体颜色
    override fun setAccentColor(accentColor: Int): ClassicsHeader {
        return super.setAccentColor(accentColor)
    }

    //header背景，以此类推
    override fun setPrimaryColor(primaryColor: Int): ClassicsHeader {
        return super.setPrimaryColor(primaryColor)
    }


    init {
        mAccentColor = ContextCompat.getColor(context!!, R.color.refresh_accent)
        mPrimaryColor = ContextCompat.getColor(context!!, R.color.refresh_primary)

    }
}