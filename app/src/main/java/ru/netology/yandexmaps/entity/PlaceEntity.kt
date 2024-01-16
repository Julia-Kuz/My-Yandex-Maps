package ru.netology.yandexmaps.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.yandexmaps.dto.Place


@Entity(tableName = "PlaceEntity")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDto() = Place (name, latitude, longitude)


    companion object {
        fun fromDto(dto: Place) :PlaceEntity {
            return PlaceEntity( dto.name, dto.latitude, dto.longitude)
        }
    }
}


fun List<PlaceEntity>.toDto(): List<Place> = map(PlaceEntity::toDto)
fun List<Place>.toEntity(): List<PlaceEntity> = map(PlaceEntity::fromDto)
