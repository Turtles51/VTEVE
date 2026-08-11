package com.eve.pricewatcher.database.dao

import androidx.room.*
import com.eve.pricewatcher.database.entities.Settings

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE key = :key")
    suspend fun getByKey(key: String): Settings?

    @Query("SELECT value FROM settings WHERE key = :key")
    suspend fun getValue(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Settings)

    @Query("DELETE FROM settings WHERE key = :key")
    suspend fun deleteByKey(key: String)

    @Query("DELETE FROM settings")
    suspend fun deleteAll()
}
