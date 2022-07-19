package com.artsam.presentation.mdviewer.ui.fragment.mdhome

import android.os.Bundle
import android.view.View
import com.artsam.presentation.R
import com.artsam.presentation.databinding.FragmentMdhomeBinding
import com.artsam.presentation.mdviewer.ui.fragment.base.BaseFragment

class MdHomeFragment : BaseFragment<FragmentMdhomeBinding>(R.layout.fragment_mdhome) {

    private var isAppBarVisible = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener{
            showHideAppBar()
        }
    }

    private fun showHideAppBar(){
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