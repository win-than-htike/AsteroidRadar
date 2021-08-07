package com.onething.asteroidradar.screens.list

import androidx.lifecycle.*
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import com.onething.asteroidradar.utils.DataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AsteroidRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading = Transformations.map(_loading) {
        it
    }

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    init {
        getAsteroidData()
    }

    private fun getAsteroidData() {
        viewModelScope.launch {
            _loading.value = true
            repository.fetchAsteroidData()
                .collect({
                    _loading.value = false
                }, {
                    _loading.value = false
                    _asteroidList.value = it
                })
        }
    }

}