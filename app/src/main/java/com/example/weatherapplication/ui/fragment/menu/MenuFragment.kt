package com.example.weatherapplication.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentMenuBinding
import com.example.weatherapplication.ui.base.BaseFragment

/**
 * @author Kalmykova Natalia
 */
class MenuFragment : BaseFragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding
        get() = _binding!!

    override val titleRes = R.string.fragment_menu_title

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.apply {
            chooseCity.setOnClickListener { navigateToChooseCityFragment() }
            settings.setOnClickListener { navigateToSettingsFragment() }
        }

        return binding.root
    }

    private fun navigateToSettingsFragment() {
        findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
    }

    private fun navigateToChooseCityFragment() {
        findNavController().navigate(R.id.action_menuFragment_to_chooseCityFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}