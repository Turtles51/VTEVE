package com.eve.pricewatcher.utils

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class Scheduler(private val context: Context) {

    companion object {
        private const val WORK_NAME = "price_check_work"
    }

    /**
     * Запустить фоновые проверки цен
     */
    fun startScheduling(intervalMinutes: Int) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<PriceCheckWorker>(
            intervalMinutes.toLong(),
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
    }

    /**
     * Остановить фоновые проверки
     */
    fun stopScheduling() {
        WorkManager.getInstance(context)
            .cancelUniqueWork(WORK_NAME)
    }

    /**
     * Запустить ручную проверку (немедленно)
     */
    fun runManualCheck() {
        val workRequest = OneTimeWorkRequestBuilder<PriceCheckWorker>()
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)
    }

    /**
     * Проверить, запущен ли планировщик
     */
    fun isScheduled(): Boolean {
        val workInfos = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork(WORK_NAME)
            .get()

        return workInfos.any { it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING }
    }

    /**
     * PriceCheckWorker — фоновый работник для проверки цен
     */
    class PriceCheckWorker(
        context: Context,
        params: WorkerParameters
    ) : CoroutineWorker(context, params) {

        override suspend fun doWork(): Result {
            // TODO: Реализовать логику проверки цен
            // 1. Получить все активные товары из базы
            // 2. Для каждого товара проверить все регионы
            // 3. Если цена достигла порога — отправить уведомление
            // 4. Сохранить актуальные цены в базу

            return Result.success()
        }
    }
}
