package com.artsam.presentation.ui.fragment.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.artsam.presentation.R

class NotificationsSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.notifications_preferences, rootKey)
    }
}