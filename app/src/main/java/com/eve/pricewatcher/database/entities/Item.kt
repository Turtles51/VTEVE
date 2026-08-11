package com.eve.pricewatcher.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemId: Int,                // ID товара
    val itemName: String,           // Название товара
    val orderType: String,          // "sell" или "buy"
    val threshold: Double,          // Порог цены
    val intervalMinutes: Int,       // Интервал проверки в минутах
    val active: Boolean = true,     // Активен ли товар
    val lastPrice: Double? = null,  // Последняя известная цена
    val lastRegion: String? = null, // Последний регион
    val lastUpdate: String? = null  // Время последнего обновления
)
