package com.opninterviewservice.testapp.settings

import android.content.Context
import android.content.SharedPreferences
import com.opninterviewservice.testapp.App
import com.opninterviewservice.testapp.interfaces.Settings


//This File Created at 20.10.2020 10:21.
class PrefSettingsProvider: Settings {

    private val PREFERENCES_FILE = "AppPref"
    private val PREF_FOR_CLOUD_TIMEOUT = "cloudTimeOut"


    override var networkResponseTimeOut: Long
        get() = getPreferences().getLong(PREF_FOR_CLOUD_TIMEOUT, DEFAULT_NETWORK_TIMEOUT)
        set(value) {
            setLongPref(PREF_FOR_CLOUD_TIMEOUT, value)
        }


    private fun clearPref(name: String){
        getPreferences().edit()
            .remove(name)
            .apply()
    }

    private fun setIntPref(key: String, value: Int) {
        getPreferences().edit()
            .putInt(key, value)
            .apply()
    }

    private fun setLongPref(key: String, value: Long) {
        getPreferences().edit()
            .putLong(key, value)
            .apply()
    }

    private fun setBooleanPref(key: String, value: Boolean) {
        getPreferences().edit()
            .putBoolean(key, value)
            .apply()
    }

    private fun setStringPref(key: String, value: String) {
        getPreferences().edit()
            .putString(key, value)
            .apply()
    }

    private fun setStringSetPref(key: String, value: Set<String>) {
        getPreferences().edit()
            .putStringSet(key, value)
            .apply()
    }

    private fun getPreferences(): SharedPreferences {
        return App.instance.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
    }
}