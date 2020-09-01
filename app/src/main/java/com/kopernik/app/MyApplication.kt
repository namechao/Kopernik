package com.kopernik.app

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import com.aleyn.mvvm.base.BaseApplication
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV
import com.kopernik.app.config.AppConfig
import com.kopernik.app.utils.LocalManageUtil
import com.tencent.bugly.crashreport.CrashReport


/**
 *   @auther : zhanglichao
 *   time   : 2012/07/07
 */
class MyApplication :BaseApplication(){

    companion object{
        private lateinit var instance:MyApplication
        fun instance()= instance

    }

    var mAttachBaseContext: Context? = null
    var currentActivity: Activity? = null //应用内最新打开的Activity
    override fun onCreate() {
        super.onCreate()
        instance=this
        LocalManageUtil.setApplicationLanguage(this)
        LogUtils.getConfig().run {
            isLogSwitch= AppConfig.logEnable
            setSingleTagSwitch(false)
        }
        MMKV.initialize(this)
        CrashReport.initCrashReport(getApplicationContext(), "45b5432dde", false);
//        initX5()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                currentActivity = activity
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                if (currentActivity?.javaClass?.name.equals(activity?.javaClass?.name)) {
                    currentActivity = null
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }
        })
    }

    override fun attachBaseContext(base: Context) {
        instance=this
        mAttachBaseContext=this
        MMKV.initialize(base)
        super.attachBaseContext(LocalManageUtil.setLocal(base))
    }
//    private fun initX5() {
//        // 在调用TBS初始化、创建WebView之前进行如下配置
//        val map = HashMap<Any, Any>()
//        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
//        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
//        QbSdk.initTbsSettings(map as MutableMap<String, Any>)
//        //非wifi情况下，主动下载x5内核
//        QbSdk.setDownloadWithoutWifi(true)
//        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//        val cb: PreInitCallback = object : PreInitCallback {
//            override fun onViewInitFinished(arg0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                if(arg0){
//                    Log.d("tencentX5","x5内核加载成功")
//                }else{
//                    Log.d("tencentX5","x5内核加载内核失败自动加载系统内核")
//                }
//            }
//
//            override fun onCoreInitFinished() {
//                Log.d("tencentX5","内核加载完成")
//            }
//        }
//        //x5内核初始化接口
//        QbSdk.initX5Environment(applicationContext, cb)
//    }
override fun onConfigurationChanged(newConfig: Configuration?): Unit {
    super.onConfigurationChanged(newConfig)
    LocalManageUtil.onConfigurationChanged(applicationContext)
}
}