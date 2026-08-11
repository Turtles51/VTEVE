package com.eve.pricewatcher.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.eve.pricewatcher.database.dao.*
import com.eve.pricewatcher.database.entities.*

@Database(
    entities = [Item::class, Region::class, CustomCacheItem::class, HistoryEntry::class, Settings::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun regionDao(): RegionDao
    abstract fun customCacheDao(): CustomCacheDao
    abstract fun historyDao(): HistoryDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eve_cache.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
