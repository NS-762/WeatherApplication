package com.example.weatherapplication.ui.fragment.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentWeatherBinding
import com.example.weatherapplication.model.WeatherForDayModel
import com.example.weatherapplication.model.WeatherModel
import com.example.weatherapplication.ui.base.BaseFragment
import com.example.weatherapplication.ui.fragment.weather.adapter.WeatherAdapter
import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity




/**
 * @author Kalmykova Natalia
 */
class WeatherFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WeatherViewModel

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() = _binding!!

    override val isNavigateBackVisible = false

    private val adapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.weatherComponent().create().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        viewModel.apply {
            weatherLiveData.observe(viewLifecycleOwner, ::setWeather)
            loadingLiveData.observe(viewLifecycleOwner, ::setLoading)
            errorLiveData.observe(viewLifecycleOwner, ::setError)
            loadData()
            start()
        }

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemGestureInsets = insets.getInsets(WindowInsetsCompat.Type.systemGestures())
            WindowInsetsCompat.Builder()
                .setInsets(WindowInsetsCompat.Type.systemBars(), systemGestureInsets)
                .build()
        }
    }

    private fun initView() {
        binding.apply {
            swipeRefresh.setOnRefreshListener { viewModel.reloadData() }

            recyclerView.adapter = adapter
            adapter.onItemClicked = { setChosenWeather(it) }

            menu.setOnClickListener { navigateToMenuFragment() }
        }
    }

    private fun setWeather(weather: WeatherModel) {
        setCurrentWeather(weather.weatherByDays[0])
        setItems(weather.weatherByDays)
    }

    private fun setCurrentWeather(weather: WeatherForDayModel) {
        setChosenWeather(weather)
    }

    private fun setChosenWeather(weather: WeatherForDayModel) {
        binding.apply {
            temperature.text = weather.tempMax
            city.text =
                if (viewModel.cityName.isNullOrEmpty()) getString(R.string.fragment_weather_undefined_city) else viewModel.cityName
            date.text = weather.date
            description.text = weather.mainDescription
            wind.text = getString(
                R.string.fragment_weather_wind,
                weather.windSpeed,
                viewModel.getWindSpeedUnits()
            )
            pressure.text = getString(
                R.string.fragment_weather_pressure,
                weather.pressure,
                viewModel.pressureUnits
            )
            humidity.text = getString(R.string.fragment_weather_humidity, weather.humidity)
            weatherPicture.setImageResource(weather.weatherPicture)
        }
    }

    private fun setItems(items: List<WeatherForDayModel>) {
        processError(items.isEmpty())
        adapter.items = items
    }

    private fun setLoading(isLoading: Boolean) {
        binding.swipeRefresh.isRefreshing = isLoading
    }

    private fun setError(error: String) {
        processError(true)
    }

    private fun processError(isHasError: Boolean) {
        binding.weatherGroup.isVisible = !isHasError
        binding.error.isVisible = isHasError
    }

    private fun navigateToMenuFragment() {
        findNavController().navigate(R.id.action_weatherFragment_to_menuFragment)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}