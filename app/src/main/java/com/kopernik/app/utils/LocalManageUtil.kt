package com.kopernik.app.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.kopernik.app.config.UserConfig
import com.kopernik.common.LanguageConstant
import java.util.*

/**
 * 多语言管理
 */
object LocalManageUtil {
    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    fun getSetLanguageLocale(context: Context): Locale {
        return LanguageConstant(context).localMaps[UserConfig.singleton?.languageTag]!!
    }

    fun saveSelectLanguage(context: Context, select: Int) {
        UserConfig.singleton?.languageTag=select
        setApplicationLanguage(context)
    }

    fun setLocal(context: Context): Context {
        return updateResources(
            context,
            getSetLanguageLocale(context)
        )
    }

    private fun updateResources(
        context: Context,
        locale: Locale
    ): Context {
        var context = context
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    /**
     * 设置语言类型
     */
    fun setApplicationLanguage(context: Context) {
        val resources =
            context.applicationContext.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        val locale =
            getSetLanguageLocale(context)
        config.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
            context.applicationContext.createConfigurationContext(config)
            Locale.setDefault(locale)
        }
        resources.updateConfiguration(config, dm)
    }

//        public static void saveSystemCurrentLanguage(Context context) {
//            Locale locale;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                locale = LocaleList.getDefault().get(0);
//            } else {
//                locale = Locale.getDefault();
//            }
//            Log.d(TAG, locale.getLanguage());
//            UserConfig.getSingleton().setSystemLocal(locale);
//        }
    fun onConfigurationChanged(context: Context) {
//        saveSystemCurrentLanguage(context);
        setLocal(context)
        setApplicationLanguage(context)
    }
}