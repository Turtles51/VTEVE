package com.eve.pricewatcher.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_cache_items")
data class CustomCacheItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val category: String,
    val addedAt: String
)
