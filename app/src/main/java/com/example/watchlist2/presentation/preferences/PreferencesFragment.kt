package com.example.watchlist2.presentation.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.watchlist2.R
import com.example.watchlist2.extension.setToolbarTitle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences, key: String ->
        if (key == "pref_dark_mode") {
            activity?.recreate()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        setToolbarTitle(R.string.settings)
        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    }

    override fun onStop() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        super.onStop()
    }
}