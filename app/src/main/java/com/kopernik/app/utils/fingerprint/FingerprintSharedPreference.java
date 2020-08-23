package com.kopernik.app.utils.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.kopernik.app.config.UserConfig;

public class FingerprintSharedPreference {

    final String dataKeyName = "data";
    final String IVKeyName = "IV";
    private SharedPreferences preferences;

    FingerprintSharedPreference(Context context) {
        preferences = context.getSharedPreferences("fingerprint", Activity.MODE_PRIVATE);
    }

    String getData(String keyName) {
        return preferences.getString("UserConfig.getSingleton().getAccount().getLoginAcountHash() + keyName", "");
    }

    boolean storeData(String key, String data) {
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(UserConfig.getSingleton().getAccount().getLoginAcountHash() + key, data);
        return editor.commit();
    }
}
