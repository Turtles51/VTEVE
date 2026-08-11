package com.eve.pricewatcher.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object BZip2Helper {
    private const val CACHE_URL = "https://www.fuzzwork.co.uk/dump/sqlite-latest.sqlite.bz2"
    private const val DB_NAME = "eve_cache.db"
    private const val TEMP_FILE_NAME = "temp_cache.bz2"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun downloadAndExtract(
        context: Context,
        onProgress: (Int, String) -> Unit = { _, _ -> }
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            onProgress(0, "Скачивание кэша...")
            val tempFile = File(context.cacheDir, TEMP_FILE_NAME)

            val request = Request.Builder()
                .url(CACHE_URL)
                .header("User-Agent", "EvePriceWatcher/1.0")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext false

            val body = response.body ?: return@withContext false
            val contentLength = body.contentLength()
            val inputStream = body.byteStream()

            val buffer = ByteArray(8192)
            var bytesRead: Int
            var totalBytesRead = 0L

            FileOutputStream(tempFile).use { outputStream ->
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead

                    if (contentLength > 0) {
                        val progress = ((totalBytesRead * 100) / contentLength).toInt()
                        onProgress(progress, "Скачивание: $progress%")
                    }
                }
            }
            inputStream.close()

            onProgress(90, "Распаковка...")
            val dbFile = File(context.filesDir, DB_NAME)

            FileInputStream(tempFile).use { fileInputStream ->
                BufferedInputStream(fileInputStream).use { bufferedInputStream ->
                    BZip2CompressorInputStream(bufferedInputStream).use { bz2InputStream ->
                        FileOutputStream(dbFile).use { outputStream ->
                            val decompressBuffer = ByteArray(8192)
                            var decompressBytesRead: Int
                            while (bz2InputStream.read(decompressBuffer).also { decompressBytesRead = it } != -1) {
                                outputStream.write(decompressBuffer, 0, decompressBytesRead)
                            }
                        }
                    }
                }
            }

            tempFile.delete()
            onProgress(100, "Готово!")
            return@withContext true

        } catch (e: Exception) {
            e.printStackTrace()
            onProgress(-1, "Ошибка: ${e.message}")
            return@withContext false
        }
    }

    fun isDatabaseExists(context: Context): Boolean {
        val dbFile = File(context.filesDir, DB_NAME)
        return dbFile.exists() && dbFile.length() > 0
    }

    fun getDatabaseSize(context: Context): Long {
        val dbFile = File(context.filesDir, DB_NAME)
        return if (dbFile.exists()) dbFile.length() else 0
    }
}
