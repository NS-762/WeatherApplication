package com.example.weatherapplication.ui.fragment.choosecity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentChooseCityBinding
import com.example.weatherapplication.model.CityModel
import com.example.weatherapplication.ui.base.BaseFragment
import com.example.weatherapplication.ui.fragment.choosecity.adapter.AllCitiesAdapter
import com.example.weatherapplication.ui.fragment.choosecity.adapter.ViewedCitiesAdapter
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class ChooseCityFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ChooseCityViewModel

    private var _binding: FragmentChooseCityBinding? = null
    private val binding: FragmentChooseCityBinding
        get() = _binding!!

    override val titleRes = R.string.fragment_choose_city_title

    private val allCitiesAdapter = AllCitiesAdapter()
    private val viewedCitiesAdapter = ViewedCitiesAdapter()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var jobLocationTimeout: Job? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) checkLocationPermission()
        }

    private val requestLocationSettingsLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    getMyLocationIfRequired()
                }
                Activity.RESULT_CANCELED -> {
                    /* ignored */
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.chooseCityComponent().create().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChooseCityViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        viewModel.apply {
            allCitiesLiveData.observe(viewLifecycleOwner, ::setItemsAllCities)
            viewedCitiesLiveData.observe(viewLifecycleOwner, ::setItemsViewedCities)
            navigateBackLiveData.observe(viewLifecycleOwner, ::navigateBack)
            loadingLiveData.observe(viewLifecycleOwner, ::setLoading)
            loadData()
            start()
        }

        initView()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            swipeRefresh.setOnRefreshListener { viewModel.reloadData() }

            allCitiesRecycler.adapter = allCitiesAdapter
            allCitiesAdapter.onItemClickListener = { city -> viewModel.onItemClickListener(city) }

            viewedCitiesRecycler.adapter = viewedCitiesAdapter
            viewedCitiesAdapter.onItemClickListener =
                { city -> viewModel.onItemClickListener(city) }

            citySearch.addTextChangedListener { newText ->
                viewedCitiesGroup.isVisible = newText.isNullOrEmpty()
                allCitiesRecycler.isVisible = !newText.isNullOrEmpty()
                allCitiesAdapter.filter.filter(newText)
            }

            byLocation.setOnClickListener {
                showEnableLocationSetting()

//                viewModel.getCityByCoordinates(
//                    "44.599804",
//                    "41.962885"
//                )
            }
        }
    }

    /** Проверить, включены ли службы определения местоположения */
    private fun showEnableLocationSetting() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val task = LocationServices.getSettingsClient(requireActivity())
            .checkLocationSettings(builder.build())

        task.addOnSuccessListener { response ->
            if (response?.locationSettingsStates?.isLocationPresent == true) {
                getMyLocationIfRequired()
            }
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    requestLocationSettingsLauncher.launch(
                        IntentSenderRequest.Builder(e.resolution).build()
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    /* ignored */
                }
            }
        }
    }

    private fun setItemsAllCities(items: List<CityModel>) {
        allCitiesAdapter.items = items
    }

    private fun setItemsViewedCities(items: List<CityModel>) {
        viewedCitiesAdapter.items = items
    }

    private fun setLoading(isLoading: Boolean) {
        binding.swipeRefresh.isRefreshing = isLoading
    }

    private fun processLocationLoading(isLoading: Boolean) {
        viewModel.processLocationLoading(isLoading)
    }

    private fun processLocationError() {
        viewModel.showError(getString(R.string.fragment_choose_location_timeout))
    }

    private fun navigateBack(unit: Unit) {
        findNavController().popBackStack(R.id.weatherFragment, false)
    }

    private fun getMyLocationIfRequired() {
        checkLocationPermission()
    }

    /** Проверка наличия разрешения на получение местоположения */
    private fun checkLocationPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                processLocationLoading(true)
                val mLocationRequest = LocationRequest.create().apply {
                    setExpirationDuration(LOCATION_REQUEST_DURATION)
                    interval = 60000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                val mLocationCallback: LocationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        for (location in locationResult.locations) {
                            location?.let {
                                jobLocationTimeout?.cancel()
                                viewModel.getCityByCoordinates(it)
                                fusedLocationClient.removeLocationUpdates(this)
                            }
                        }
                    }
                }
                fusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    fusedLocationClient.looper
                )
                setLocationTimeout()
            }
            else -> showAccessLocationDialog()
        }
    }

    /**
     * Показать диалог с просьбой предоставить доступ к получению местоположения устройства или
     * диалог с переходом в системные настройки приложения, если пользователь ранее отказался предоставлять доступ
     */
    private fun showAccessLocationDialog() {
        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            return
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.fragment_choose_access_location_title)
            .setMessage(R.string.fragment_choose_access_location)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                })
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setCancelable(false)
        builder.create().show()
    }

    private fun setLocationTimeout() {
        jobLocationTimeout = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            delay(LOCATION_TIMEOUT)
            processLocationLoading(false)
            processLocationError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOCATION_REQUEST_DURATION = 10000L
        private const val LOCATION_TIMEOUT = LOCATION_REQUEST_DURATION + 200L
    }
}