package com.eve.pricewatcher.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class Region(
    @PrimaryKey
    val regionId: Int,
    val regionName: String,
    val updatedAt: String = ""
)
