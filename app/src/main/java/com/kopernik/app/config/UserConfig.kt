package com.kopernik.app.config

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.kopernik.ui.login.bean.AccountBean
import com.kopernik.ui.login.bean.User
import java.util.*

class UserConfig {
    private val TAG_ACCOUNT = "account_data"
    private val TAG_MASTER_URL = "master_url"
    private val TAG_TRADE_URL = "trade_url"
    private val TAG_NODE_LOGO_URL = "node_logo_url"
    private val TAG_ASSET_SHWOW_URL = "asset_show_url"
    private val TAG_LANGUAGE_URL = "language_url"
    private val TAG_RELOAD_HOME_WEB = "reload_home_web"
    private val TAG_RELOAD_TRAD_WEB = "reload_trad_web"
    private val TAG_USE_FINGERPRINT = "use_fingerprint"
    private val TAG_NOTICE = "use_notice"
    private val TAG_ALL_ACCOUNT = "all_account"
    private val TAG_PASSWORD = "password"
    private val TAG_MNEMONIC = "mnemonic"
    private val systemLocal = Locale.SIMPLIFIED_CHINESE
    var masterUrl: String?
        get() = MMKV.defaultMMKV().decodeString(TAG_MASTER_URL, "http://www.baidu.com")
        set(master) {
            MMKV.defaultMMKV().encode(TAG_MASTER_URL, master)
        }

    var tradeUrl: String?
        get() = MMKV.defaultMMKV().decodeString(TAG_TRADE_URL, "http://www.sina.com")
        set(trade) {
            MMKV.defaultMMKV().encode(TAG_TRADE_URL, trade)
        }

    var accountString: String?
        get() = MMKV.defaultMMKV().decodeString(TAG_ACCOUNT, "")
        set(data) {
            MMKV.defaultMMKV().remove(TAG_ACCOUNT)
            MMKV.defaultMMKV().encode(TAG_ACCOUNT, data)
        }

//     var nodeLogo:String?
//      get() = MMKV.defaultMMKV().decodeString(TAG_NODE_LOGO_URL, "")
//      set(logo) = MMKV.defaultMMKV().encode(TAG_NODE_LOGO_URL, logo)


    var assetShow: Boolean
        get() = MMKV.defaultMMKV().decodeBool(TAG_ASSET_SHWOW_URL, true)
        set(b) {
            MMKV.defaultMMKV().encode(TAG_ASSET_SHWOW_URL, b)
        }

    var languageTag: Int
        get() = MMKV.defaultMMKV().decodeInt(TAG_LANGUAGE_URL, 1)
        set(tag) {
            MMKV.defaultMMKV().encode(TAG_LANGUAGE_URL, tag)
        }

    var isReloadHomeWeb: Boolean
        get() = MMKV.defaultMMKV().decodeBool(TAG_RELOAD_HOME_WEB, true)
        set(b) {
            MMKV.defaultMMKV().encode(TAG_RELOAD_HOME_WEB, b)
        }

    var tradePassword: String?
        get() {
            var psw=MMKV.defaultMMKV().decodeString(TAG_RELOAD_TRAD_WEB, "")
            if (psw.isNullOrBlank()) return accountBean?.salePwd else return psw
        }
        set(b) {
            MMKV.defaultMMKV().encode(TAG_RELOAD_TRAD_WEB, b)
        }


    var noticeId: Int
        get() = MMKV.defaultMMKV().decodeInt(TAG_NOTICE, -1)
        set(id) {
            MMKV.defaultMMKV().encode(TAG_NOTICE, id)
        }

    var allAccount: String?
        get() = MMKV.defaultMMKV().decodeString(TAG_ALL_ACCOUNT, "")
        set(data) {
            MMKV.defaultMMKV().encode(TAG_ALL_ACCOUNT, data)
        }
    var password: String?
        get() = MMKV.defaultMMKV().decodeString(TAG_PASSWORD, "")
        set(data) {
            MMKV.defaultMMKV().encode(TAG_PASSWORD, data)
        }

    var accountBean:User?
       set(value) {
           MMKV.defaultMMKV().remove(TAG_ACCOUNT)
           MMKV.defaultMMKV().encode(TAG_ACCOUNT, Gson().toJson(value))
       }
       get() {
          var accountString= MMKV.defaultMMKV().decodeString(TAG_ACCOUNT, "")
           var accountBean:User?=null
           if (accountString.isNotEmpty()) {
               accountBean = Gson().fromJson<User>(accountString, User::class.java)
           }
          return accountBean
       }

    fun clear(){
        if (accountBean!=null) {
            accountBean = null
        }

    }
    companion object {
        @Volatile
        private var userConfig: UserConfig? = null
        @JvmStatic
        val singleton: UserConfig?
            get() {
                if (userConfig == null) {
                    synchronized(UserConfig::class.java) {
                        if (userConfig == null) {
                            userConfig = UserConfig()
                        }
                    }
                }
                return userConfig
            }
    }



}