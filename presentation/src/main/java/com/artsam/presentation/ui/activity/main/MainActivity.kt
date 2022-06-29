package com.artsam.presentation.ui.activity.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.artsam.presentation.R
import com.artsam.presentation.databinding.ActivityMainBinding
import com.artsam.presentation.ui.activity.help.HelpActivity
import com.artsam.presentation.ui.activity.settings.SettingsActivity
import com.artsam.presentation.ui.fragment.settings.SettingsFragment

class MainActivity : AppCompatActivity(){

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnStartSettingsActivity.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }
}