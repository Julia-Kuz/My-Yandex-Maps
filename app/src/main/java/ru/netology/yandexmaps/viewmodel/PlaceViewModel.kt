package ru.netology.yandexmaps.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.repository.PlaceRepository
import javax.inject.Inject


@HiltViewModel
class PlaceViewModel @Inject constructor (
    private val repository: PlaceRepository
    ) : ViewModel() {

    val data = repository.getAll()
    fun removePlace(place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removePlace(place)
            }
        }
    }

    fun savePlace(place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.savePlace(place)
            }
        }
    }

    fun editPlace (place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.editPlace(place)
            }
        }
    }

    var placeView : Place = Place("", 0.0, 0.0)
    val placeName : MutableLiveData <String> by lazy {
        MutableLiveData <String>()
    }

    var position: Point = Point(55.752004, 37.617734)

}