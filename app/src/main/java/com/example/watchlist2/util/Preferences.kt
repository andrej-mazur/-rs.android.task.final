package com.example.watchlist2.util

import android.content.SharedPreferences

class Preferences(
    private val sharedPreferences: SharedPreferences
) {

    fun isPrefDarkModeOn(): Boolean {
        return getPrefDarkMode() == PREF_VALUE_DARK_MODE_ON
    }

    fun isPrefDarkModeOff(): Boolean {
        return getPrefDarkMode() == PREF_VALUE_DARK_MODE_OFF
    }

    private fun getPrefDarkMode(): String {
        return sharedPreferences.getString(PREF_KEY_DARK_MODE, PREF_VALUE_DARK_MODE_OFF)!!
    }

    fun isPrefDarkModeKey(key: String): Boolean {
        return key == PREF_KEY_DARK_MODE
    }

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object {
        const val PREF_KEY_DARK_MODE = "pref_dark_mode"
        const val PREF_VALUE_DARK_MODE_ON = "ON"
        const val PREF_VALUE_DARK_MODE_OFF = "OFF"
    }
}