package com.artsam.presentation.ui.fragment.settings

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.artsam.presentation.R
import com.artsam.presentation.ui.activity.help.HelpActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // Finding Preferences
        // To access an individual Preference use PreferenceFragmentCompat.findPreference()
        // This method searches the entire hierarchy for a Preference with the given key.
        val signaturePreference: EditTextPreference? = findPreference("signature")
        // do something with this preference

        val customSummaryPreference: EditTextPreference? = findPreference("custom_summary")

        // Customize an EditTextPreference dialog
        customSummaryPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }

        // Use a custom SummaryProvider
        customSummaryPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                "Not set"
            } else {
                "Length of saved value: " + text?.length
            }
        }

        /**
         * Implementing an OnPreferenceChangeListener allows you to listen for
         * the value of a Preference is about to change.
         * From there, you can validate if this change should occur.
         */
        customSummaryPreference?.setOnPreferenceChangeListener { preference, newValue ->
            println("preference: $preference is about to change to \"$newValue\"")
            return@setOnPreferenceChangeListener true
        }

        val startHelpActivity : Preference? = findPreference("start_new_activity")
        val intent = Intent(requireContext(), HelpActivity::class.java)
        startHelpActivity?.intent = intent


        // Control Preference visibility
    }
}
//        if(/*some feature*/) {
//            val forScrollingPreference: EditTextPreference? = findPreference("feedback1")
//            forScrollingPreference?.isVisible = true
//        }
