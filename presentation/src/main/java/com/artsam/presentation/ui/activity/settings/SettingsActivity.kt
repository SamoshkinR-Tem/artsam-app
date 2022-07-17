package com.artsam.presentation.ui.activity.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import com.artsam.presentation.R
import com.artsam.presentation.databinding.ActivitySettingsBinding
import com.artsam.presentation.ui.fragment.settings.SettingsFragment

class SettingsActivity: AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener  {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        sharedPref = getPreferences(Context.MODE_PRIVATE)   // returns sharedPref FILE linked exactly for current activity
        // sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getCurrentTheme())

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.incAppBar.ivAppbarMenu.setOnClickListener(::showMenu)
    }

    private fun getCurrentTheme() = sharedPref.getString(getString(R.string.current_theme), getString(R.string.theme_light))!!

    private fun setTheme(theme: String) {
        when (theme) {
            getString(R.string.theme_light) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            getString(R.string.theme_dark) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> {}
        }
    }

    /**
     * @see <a href = "https://material.io/components/menus/android#dropdown-menus">Dropdown menu examples</a>
     */
    private fun showMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.overflow_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.theme -> {
                    with(sharedPref.edit()) {

                        // save the preference
                        putString(
                            getString(R.string.current_theme), // preference key
                            // value for saving
                            when (getCurrentTheme()) {
                                getString(R.string.theme_light) -> getString(R.string.theme_dark)
                                else -> getString(R.string.theme_light)
                            }
                        )

                        apply()
                    }
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, SettingsFragment())
                        .commit()
                }
            }

            return@setOnMenuItemClickListener true
        }
        popup.setOnDismissListener {} // Respond to popup being dismissed.
        popup.show() // Show the popup menu.
    }

    /*
    * To prevent unintended garbage collection, you must store a strong reference to the listener.
    * When you call registerOnSharedPreferenceChangeListener(), the SharedPreferenceManager does not store
    * a strong reference to the listener. To address this, you can implement onSharedPreferenceChanged()
    * directly in your PreferenceFragmentCompat / AppCompatActivity.
    */
    override fun onResume() {
        super.onResume()
        getPreferences(Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        getPreferences(Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPref: SharedPreferences?, key: String?) {
        println("preference: $key is about to changed")
        when(key){
            getString(R.string.current_theme) -> {
                // recreate()
                val refresh = Intent(this, SettingsActivity::class.java)
                startActivity(refresh)
                finish()
            }
        }
    }
}