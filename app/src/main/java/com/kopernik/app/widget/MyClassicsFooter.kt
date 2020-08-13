package com.kopernik.app.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.kopernik.R
import skin.support.widget.SkinCompatSupportable

class MyClassicsFooter @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ClassicsFooter(context, attrs, defStyleAttr), SkinCompatSupportable {
    private var mAccentColor = 0
    private var mPrimaryColor = 0
    private fun applyColor() {
        setAccentColor(mAccentColor)
        setPrimaryColor(mPrimaryColor)
    }

    override fun applySkin() {
        applyColor()
    }

    init {
        mAccentColor = ContextCompat.getColor(context!!, R.color.refresh_accent)
        mPrimaryColor = ContextCompat.getColor(context!!, R.color.refresh_primary)
        applyColor()
    }
}