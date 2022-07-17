package com.artsam.presentation.ui.activity.help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.artsam.presentation.R
import com.artsam.presentation.databinding.ActivityMdviewerBinding

class MdviewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMdviewerBinding
    private var isAppBarVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mdviewer)

        binding.root.setOnClickListener{
            moveAppBar()
        }
    }

    private fun moveAppBar(){
        binding.appBar.apply {
            isAppBarVisible = when(isAppBarVisible) {
                true -> {
                    animate().y(-200f).setDuration(500).start()
                    false
                }
                false -> {
                    animate().y(0f).setDuration(500).start()
                    true
                }
            }
        }
    }
}