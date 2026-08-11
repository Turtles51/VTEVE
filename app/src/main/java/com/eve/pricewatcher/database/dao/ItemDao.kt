package com.eve.pricewatcher.database.dao

import androidx.room.*
import com.eve.pricewatcher.database.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY id DESC")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE active = 1")
    fun getActive(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getById(id: Long): Item?

    @Insert
    suspend fun insert(item: Item): Long

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM items")
    suspend fun deleteAll()

    @Query("UPDATE items SET lastPrice = :price, lastRegion = :region, lastUpdate = :time WHERE id = :id")
    suspend fun updatePrice(id: Long, price: Double, region: String, time: String)

    @Query("UPDATE items SET active = :active WHERE id = :id")
    suspend fun setActive(id: Long, active: Boolean)
}
