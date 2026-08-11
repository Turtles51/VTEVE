package com.eve.pricewatcher.database.dao

import androidx.room.*
import com.eve.pricewatcher.database.entities.CustomCacheItem

@Dao
interface CustomCacheDao {
    @Query("SELECT * FROM custom_cache_items ORDER BY id")
    suspend fun getAll(): List<CustomCacheItem>

    @Query("SELECT * FROM custom_cache_items WHERE id = :id")
    suspend fun getById(id: Int): CustomCacheItem?

    @Query("SELECT name FROM custom_cache_items WHERE id = :id")
    suspend fun getNameById(id: Int): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CustomCacheItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CustomCacheItem>)

    @Query("DELETE FROM custom_cache_items")
    suspend fun deleteAll()
}
