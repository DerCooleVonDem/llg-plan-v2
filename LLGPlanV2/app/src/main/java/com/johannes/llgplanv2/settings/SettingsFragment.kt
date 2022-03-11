package com.johannes.llgplanv2.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.johannes.llgplanv2.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}