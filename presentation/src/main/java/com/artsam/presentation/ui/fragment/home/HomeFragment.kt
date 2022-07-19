package com.artsam.presentation.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.artsam.presentation.R
import com.artsam.presentation.databinding.FragmentHomeBinding
import com.artsam.presentation.mdviewer.ui.activity.MdviewerActivity
import com.artsam.presentation.ui.activity.settings.SettingsActivity
import com.artsam.presentation.utils.RxExamples

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_home)

        binding.btnStartSettingsActivity.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
        binding.btnAppSpecificFileStorage.setOnClickListener {
            // workWithExternalStorage()
            // createDirInternal(applicationContext)
            // deleteDir(applicationContext)
        }
        binding.btnRxExample.setOnClickListener {
            // RxExamples.runSubjectExamples()
            RxExamples.skip()
        }
        binding.btnMdViewer.setOnClickListener {
            startActivity(Intent(requireContext(), MdviewerActivity::class.java))
        }
    }
}
