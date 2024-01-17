package ru.netology.yandexmaps.repository

import androidx.lifecycle.map
import ru.netology.yandexmaps.dao.PlaceDao
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.entity.PlaceEntity
import javax.inject.Inject

class PlaceRepositoryImplementation @Inject constructor(
    private val dao: PlaceDao,
) : PlaceRepository {

    override fun getAll() = dao.getAll().map { list ->
        list.map {
            it.toDto()
        }
    }

    override suspend fun savePlace(place: Place) {
        dao.insert(PlaceEntity.fromDto(place))
    }

    override fun removePlace(place: Place) {
        place.name?.let { dao.removeByName(it) }
    }

    override fun editPlace(place: Place) {
        dao.updateName(place.name, place.latitude, place.longitude)
    }

}