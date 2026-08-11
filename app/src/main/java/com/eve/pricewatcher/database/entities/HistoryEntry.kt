package com.eve.pricewatcher.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemId: Int,
    val itemName: String,
    val orderType: String,      // "sell" или "buy"
    val regionId: Int,
    val regionName: String,
    val price: Double,
    val threshold: Double,
    val triggeredAt: String     // Дата и время срабатывания
)
