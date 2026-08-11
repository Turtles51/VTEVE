package com.eve.pricewatcher.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object EveApiHelper {
    private const val ESI_BASE = "https://esi.evetech.net/latest"
    private const val USER_AGENT = "EvePriceWatcher/1.0 (contact: your@email.com)"

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    /**
     * Получить список всех регионов
     */
    suspend fun getRegions(): List<Int> {
        val url = "$ESI_BASE/universe/regions/"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return emptyList()

        val json = JSONArray(response.body?.string() ?: return emptyList())
        val regions = mutableListOf<Int>()
        for (i in 0 until json.length()) {
            regions.add(json.getInt(i))
        }
        return regions
    }

    /**
     * Получить минимальную цену продажи (Sell Order) для товара в регионе
     */
    suspend fun getMinSellPrice(regionId: Int, itemId: Int): Double? {
        val url = "$ESI_BASE/markets/$regionId/orders/?order_type=sell&type_id=$itemId"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null

        val json = JSONArray(response.body?.string() ?: return null)
        if (json.length() == 0) return null

        var minPrice = Double.MAX_VALUE
        for (i in 0 until json.length()) {
            val price = json.getJSONObject(i).getDouble("price")
            if (price < minPrice) minPrice = price
        }
        return minPrice
    }

    /**
     * Получить максимальную цену покупки (Buy Order) для товара в регионе
     */
    suspend fun getMaxBuyPrice(regionId: Int, itemId: Int): Double? {
        val url = "$ESI_BASE/markets/$regionId/orders/?order_type=buy&type_id=$itemId"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null

        val json = JSONArray(response.body?.string() ?: return null)
        if (json.length() == 0) return null

        var maxPrice = Double.MIN_VALUE
        for (i in 0 until json.length()) {
            val price = json.getJSONObject(i).getDouble("price")
            if (price > maxPrice) maxPrice = price
        }
        return maxPrice
    }

    /**
     * Получить название предмета по ID через ESI
     */
    suspend fun getItemName(itemId: Int): String? {
        val url = "$ESI_BASE/universe/names/"
        val jsonBody = JSONArray().apply { put(itemId) }

        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .post(okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), jsonBody.toString()))
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null

        val json = JSONArray(response.body?.string() ?: return null)
        if (json.length() == 0) return null

        return json.getJSONObject(0).getString("name")
    }

    /**
     * Проверить актуальный Build Number
     */
    suspend fun getCurrentBuildNumber(): Int? {
        val url = "https://developers.eveonline.com/static-data/tranquility/latest.json"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", USER_AGENT)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) return null

        val json = JSONObject(response.body?.string() ?: return null)
        return json.getJSONObject("sde").getInt("build")
    }

    /**
     * Текущее время в формате для базы
     */
    fun getCurrentTime(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(Date())
    }
}
