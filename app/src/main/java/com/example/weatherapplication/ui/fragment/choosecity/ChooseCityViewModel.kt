package com.example.weatherapplication.ui.fragment.choosecity

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.R
import com.example.weatherapplication.data.sharedprefs.AppPrefs
import com.example.weatherapplication.model.CityModel
import com.example.weatherapplication.ui.base.BaseViewModel
import com.example.weatherapplication.ui.fragment.choosecity.interactor.ChooseCityInteractor
import javax.inject.Inject

/**
 * @author Kalmykova Natalia
 */
class ChooseCityViewModel @Inject constructor(
    context: Context,
    private val interactor: ChooseCityInteractor,
    private val appPrefs: AppPrefs
) : BaseViewModel() {

    private val resources = context.resources

    private var _allCitiesLiveData = MutableLiveData<List<CityModel>>()
    var allCitiesLiveData: MutableLiveData<List<CityModel>> = _allCitiesLiveData

    private var _viewedCitiesLiveData = MutableLiveData<List<CityModel>>()
    var viewedCitiesLiveData: MutableLiveData<List<CityModel>> = _viewedCitiesLiveData

    private var _navigateBackLiveData = MutableLiveData<Unit>()
    var navigateBackLiveData: MutableLiveData<Unit> = _navigateBackLiveData

    private var viewedCities: MutableList<CityModel> = mutableListOf()

    override fun onStart() {
        /* no-op */
    }

    fun loadData() {
        getAllCities()
        getViewedCities()
    }

    private fun getAllCities() {
        handleLoading(resources.getString(R.string.download_error)) {
            doWork {
                val result = interactor.getAllCities()
                _allCitiesLiveData.postValue(result)
            }
        }
    }

    private fun getViewedCities() {
        handleLoading(resources.getString(R.string.download_error)) {
            doWork {
                val result = interactor.getViewedCities()
                viewedCities = result.toMutableList()
                _viewedCitiesLiveData.postValue(result)
            }
        }
    }

    /** Если город не найден, то прогноз погоды будет определяться по координатам */
    fun getCityByCoordinates(currentLocation: Location) {
        handleLoading(resources.getString(R.string.download_error)) {
            doWork {
                val result = interactor.getCityByCoordinates(
                    currentLocation.latitude,
                    currentLocation.longitude
                )
                if (result?.get(0)?.localNames?.ru == null) {
                    appPrefs.cityName = ""
                    appPrefs.latitude = currentLocation.latitude
                    appPrefs.longitude = currentLocation.longitude
                    _navigateBackLiveData.postValue(Unit)
                } else {
                    val cityName = result[0].localNames?.ru
                    processChosenCity(CityModel(cityName!!))
                }
            }
        }
    }

    fun reloadData() {
        loadData()
    }

    fun onItemClickListener(city: CityModel) {
        processChosenCity(city)
    }

    private fun processChosenCity(city: CityModel) {
        if (viewedCities.contains(city)) {
            viewedCities.remove(city)
        } else {
            if (viewedCities.size == MAX_VIEWED_CITIES) viewedCities.removeLast()
        }
        viewedCities.add(0, city)

        handleLoading(resources.getString(R.string.download_error)) {
            doWork {
                interactor.updateViewedCities(viewedCities)
                interactor.updateChosenCity(city)
                _navigateBackLiveData.postValue(Unit)
            }
        }
    }

    // Загрузка на время определения местоположения
    fun processLocationLoading(isLoading: Boolean) {
        _loadingLiveData.postValue(isLoading)
    }

    fun showError(message: String) {
        _errorLiveData.postValue(message)
    }

    companion object {
        private const val MAX_VIEWED_CITIES = 5
    }
}