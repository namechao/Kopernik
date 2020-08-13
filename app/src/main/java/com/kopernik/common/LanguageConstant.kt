package com.kopernik.common

import android.content.Context
import com.kopernik.R
import java.util.*

/**
 * Created by sand on 2018/10/30.
 * 国际化配置
 */
class LanguageConstant {
    private  val ZH_RCN = 1
    private  val EN = 2
    private  val KO = 3
    var languageMaps =
        HashMap<Int, String>()
    var localMaps =
        HashMap<Int, Locale>()

    constructor(context: Context){
        languageMaps[ZH_RCN] = context.resources.getString(R.string.chinese)
        languageMaps[EN] = context.resources.getString(R.string.english)
        languageMaps[KO] = context.resources.getString(R.string.korean)

        localMaps[ZH_RCN] = Locale.SIMPLIFIED_CHINESE
        localMaps[EN] = Locale.ENGLISH
        localMaps[KO] = Locale.KOREAN
    }
}