package com.artsam.presentation.ui.activity.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import com.artsam.presentation.R
import com.artsam.presentation.databinding.ActivityMainBinding
import com.artsam.presentation.ui.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.incAppBar.ivAppbarMenu.setOnClickListener(::showMenu)
    }

    private fun showMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.overflow_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->

            when (menuItem.itemId){
                R.id.settings -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, SettingsFragment())
                        .commit()
                }
            }

            return@setOnMenuItemClickListener true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }
}