package com.eve.pricewatcher.database.dao

import androidx.room.*
import com.eve.pricewatcher.database.entities.HistoryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY triggeredAt DESC")
    fun getAll(): Flow<List<HistoryEntry>>

    @Query("SELECT * FROM history ORDER BY triggeredAt DESC LIMIT 100")
    suspend fun getLast100(): List<HistoryEntry>

    @Insert
    suspend fun insert(entry: HistoryEntry)

    @Query("DELETE FROM history")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM history")
    suspend fun getCount(): Int
}
