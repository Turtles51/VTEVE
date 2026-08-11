package com.eve.pricewatcher.database.dao

import androidx.room.*
import com.eve.pricewatcher.database.entities.Region

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions ORDER BY regionName")
    suspend fun getAll(): List<Region>

    @Query("SELECT * FROM regions WHERE regionId = :id")
    suspend fun getById(id: Int): Region?

    @Query("SELECT regionName FROM regions WHERE regionId = :id")
    suspend fun getNameById(id: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(region: Region)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(regions: List<Region>)

    @Query("DELETE FROM regions")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM regions")
    suspend fun getCount(): Int
}
