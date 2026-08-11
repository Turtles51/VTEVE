package com.eve.pricewatcher.utils

import android.content.Context
import android.content.SharedPreferences
import com.eve.pricewatcher.database.AppDatabase
import com.eve.pricewatcher.database.entities.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CacheManager(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("eve_cache_prefs", Context.MODE_PRIVATE)
    private val db = AppDatabase.getInstance(context)

    companion object {
        const val KEY_CURRENT_BUILD = "current_build"
        const val KEY_LAST_UPDATE = "last_update"
        const val MIN_FREE_SPACE_MB = 2048L // 2 ГБ для загрузки
        const val WARN_FREE_SPACE_MB = 500L  // 500 МБ предупреждение
    }

    /**
     * Проверить, достаточно ли свободного места
     */
    fun hasEnoughSpace(): Boolean {
        val freeSpace = getFreeSpaceMB()
        return freeSpace >= MIN_FREE_SPACE_MB
    }

    /**
     * Проверить, не заканчивается ли место (предупреждение)
     */
    fun isLowSpace(): Boolean {
        val freeSpace = getFreeSpaceMB()
        return freeSpace < WARN_FREE_SPACE_MB
    }

    /**
     * Получить свободное место в МБ
     */
    fun getFreeSpaceMB(): Long {
        val stat = android.os.StatFs(context.filesDir.absolutePath)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return (blockSize * availableBlocks) / (1024 * 1024)
    }

    /**
     * Получить текущую версию кэша
     */
    fun getCurrentBuild(): Int {
        return prefs.getInt(KEY_CURRENT_BUILD, 0)
    }

    /**
     * Сохранить версию кэша
     */
    fun saveBuild(build: Int) {
        prefs.edit().putInt(KEY_CURRENT_BUILD, build).apply()
    }

    /**
     * Получить размер базы данных в МБ
     */
    fun getDatabaseSizeMB(): Long {
        val dbFile = File(context.filesDir, "../databases/eve_cache.db")
        return if (dbFile.exists()) dbFile.length() / (1024 * 1024) else 0
    }

    /**
     * Получить размер кэш-файлов в МБ
     */
    fun getCacheSizeMB(): Long {
        val cacheDir = context.cacheDir
        return cacheDir.listFiles()?.sumOf { it.length() } ?: 0 / (1024 * 1024)
    }

    /**
     * Очистить весь кэш (кроме базы данных)
     */
    suspend fun clearCache() {
        withContext(Dispatchers.IO) {
            // Очищаем временные файлы
            context.cacheDir.listFiles()?.forEach { it.delete() }

            // Очищаем custom кэш
            db.customCacheDao().deleteAll()

            // Очищаем историю
            db.historyDao().deleteAll()
        }
    }

    /**
     * Очистить базу данных полностью
     */
    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            db.clearAllTables()
        }
    }

    /**
     * Получить дату последнего обновления
     */
    fun getLastUpdate(): String {
        return prefs.getString(KEY_LAST_UPDATE, "Никогда") ?: "Никогда"
    }

    /**
     * Сохранить дату последнего обновления
     */
    fun saveLastUpdate(time: String) {
        prefs.edit().putString(KEY_LAST_UPDATE, time).apply()
    }
}
