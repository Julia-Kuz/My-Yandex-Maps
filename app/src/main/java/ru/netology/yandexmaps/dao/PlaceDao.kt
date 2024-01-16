package ru.netology.yandexmaps.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.yandexmaps.entity.PlaceEntity

@Dao
interface PlaceDao {
    @Query("SELECT * FROM PlaceEntity ORDER BY name ASC")
    fun getAll(): LiveData<List<PlaceEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: PlaceEntity)

    @Query("DELETE FROM PlaceEntity WHERE name = :name")
    fun removeByName(name: String)

}