package ru.netology.yandexmaps.repository

import androidx.lifecycle.LiveData
import ru.netology.yandexmaps.dto.Place

interface PlaceRepository {
    fun getAll(): LiveData<List<Place>>
    suspend fun savePlace (place: Place)
    fun removePlace (place: Place)
    fun editPlace (place: Place)
}