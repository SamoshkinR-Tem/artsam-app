package com.artsam.presentation.ui.activity.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.artsam.presentation.R
import com.artsam.presentation.databinding.ActivityMainBinding
import com.artsam.presentation.ui.activity.settings.SettingsActivity
import com.artsam.presentation.utils.RxExamples

class MainActivity : AppCompatActivity(){

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnStartSettingsActivity.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.btnAppSpecificFileStorage.setOnClickListener {
//            workWithExternalStorage()
//            createDirInternal(applicationContext)
//            deleteDir(applicationContext)
        }
        binding.btnRxExample.setOnClickListener {
//            RxExamples.runSubjectExamples()
            RxExamples.skip()
        }
    }
}