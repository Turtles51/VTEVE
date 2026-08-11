package com.eve.pricewatcher.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.eve.pricewatcher.R

class NotificationHelper(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val CHANNEL_ID = "price_alerts"
    private val CHANNEL_NAME = "Ценовые оповещения"
    private val NOTIFICATION_ID = 1001

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Уведомления о достижении целевой цены"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendPriceAlert(
        itemName: String,
        orderType: String, // "sell" или "buy"
        price: Double,
        threshold: Double,
        regionName: String
    ) {
        val icon = if (orderType == "sell") "📉" else "📈"
        val orderTypeText = if (orderType == "sell") "ПРОДАЖУ" else "ПОКУПКУ"
        val conditionText = if (orderType == "sell") "дешевле" else "дороже"

        val title = "$icon $itemName: найден ордер на $orderTypeText!"
        val content = "Цена: $price ISK ($conditionText $threshold ISK)\nРегион: $regionName"

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun sendCacheUpdateNotification(message: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("📦 Обновление кэша")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID + 1, notification)
    }
}
