package ru.netology.yandexmaps.db


import androidx.room.Database
import androidx.room.RoomDatabase
import ru.netology.yandexmaps.dao.PlaceDao
import ru.netology.yandexmaps.entity.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1)

abstract class AppDb : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}