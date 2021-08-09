package com.onething.asteroidradar.screens.list

import androidx.lifecycle.*
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import com.onething.asteroidradar.utils.DataException
import com.onething.asteroidradar.utils.getSevenDayTodayOnwards
import com.onething.asteroidradar.utils.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AsteroidFilterType {
    WEEKLY,
    TODAY,
    ALL
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AsteroidRepository
) : ViewModel() {

    val pictureOfDayUrl = Transformations.map(repository.getPictureOfDay()) {
        if (it?.mediaType == "image") it.url else ""
    }

    val pictureOfDayDescription = Transformations.map(repository.getPictureOfDay()) {
        it?.title ?: "PictureOfDay Descriptions"
    }

    private var currentFilterType = AsteroidFilterType.WEEKLY

    private val _loading = MutableLiveData<Boolean>()
    val loading = Transformations.map(_loading) {
        it
    }

    private val _loadData = MutableLiveData<Boolean>()

    val asteroidList = Transformations.switchMap(_loadData) {
        filterAsteroid()
    }

    init {
        _loadData.value = true
        refreshPictureOfDay()
        refreshAsteroidData()
    }

    private fun refreshAsteroidData() {
        viewModelScope.launch {
            _loading.value = true
            repository.fetchAsteroidData(
                getToday(),
                getSevenDayTodayOnwards()
            )
                .collect({
                    _loading.value = false
                }, {
                    _loading.value = false
                })
        }
    }

    private fun refreshPictureOfDay() {
        viewModelScope.launch {
            _loading.value = true
            repository.fetchImageOfTheDay().collect({
                _loading.value = false
            }, {
                _loading.value = false
            })
        }
    }

    fun setFilterType(requestFilterType: AsteroidFilterType) {
        currentFilterType = requestFilterType
        _loadData.value = true
    }

    private fun filterAsteroid(): LiveData<List<Asteroid>> {
        return when (currentFilterType) {
            AsteroidFilterType.TODAY -> {
                repository.getAsteroidWithClosetDate(getToday(), getToday())
            }
            AsteroidFilterType.ALL -> {
                repository.getAllSavedAsteroid()
            }
            AsteroidFilterType.WEEKLY -> {
                repository.getAsteroidWithClosetDate(getToday(), getSevenDayTodayOnwards())
            }
        }
    }

}