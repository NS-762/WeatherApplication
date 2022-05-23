package com.example.weatherapplication.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentSettingsBinding
import com.example.weatherapplication.ui.base.BaseFragment
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override val titleRes = R.string.fragment_settings_title

    private var arrayAmountDays = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.settingsComponent().create().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        viewModel.apply {
            amountDaysInForecastLiveData.observe(viewLifecycleOwner, ::setAmountDaysInForecast)
            temperatureUnitsLiveData.observe(viewLifecycleOwner, ::setSavedTemperatureUnits)
            pressureUnitsLiveData.observe(viewLifecycleOwner, ::setSavedPressureUnits)
            start()
        }

        initView()

        return binding.root
    }

    private fun initView() {
        arrayAmountDays = resources.getStringArray(R.array.amount_days_in_forecast)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayAmountDays
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_drop_down)
        binding.amountDaysSpinner.adapter = adapter
    }

    private fun setAmountDaysInForecast(amountDaysInForecast: Int) {
        val indexSelectedItems = arrayAmountDays.indexOf(amountDaysInForecast.toString())
        binding.amountDaysSpinner.setSelection(indexSelectedItems)
    }

    private fun setSavedTemperatureUnits(temperatureUnits: String) {
        binding.temperatureSwitch.selectedValue = temperatureUnits
    }

    private fun setSavedPressureUnits(pressureUnits: String) {
        binding.pressureSwitch.selectedValue = pressureUnits
    }

    private fun saveData() {
        viewModel.saveData(
            binding.temperatureSwitch.selectedValue,
            binding.pressureSwitch.selectedValue,
            binding.amountDaysSpinner.selectedItem.toString().toInt()
        )
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}