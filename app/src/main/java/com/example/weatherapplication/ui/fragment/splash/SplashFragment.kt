package com.example.weatherapplication.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentSplashBinding
import com.example.weatherapplication.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

/**
 * @author Kalmykova Natalia
 */
class SplashFragment : BaseFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        val anim = AnimationUtils.loadAnimation(requireContext(),R.anim.splash_anim)
        binding.image.startAnimation(anim)
        binding.appName.startAnimation(anim)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            // TODO: 09.03.2022 Исправить
            delay(2500)
            navigateToWeatherFragment()
        }

        return binding.root
    }

    private fun navigateToWeatherFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_weatherFragment)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}