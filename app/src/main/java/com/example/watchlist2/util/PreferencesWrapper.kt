package com.example.watchlist2.util

import android.content.SharedPreferences

class PreferencesWrapper(
    private val sharedPreferences: SharedPreferences
) {
    fun getPrefDarkMode(): String {
        return sharedPreferences.getString("pref_dark_mode", "OFF")!!
    }
}