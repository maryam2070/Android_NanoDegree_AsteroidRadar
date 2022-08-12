package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params)  {

    override suspend fun doWork(): Result {
    val database =
        AsteroidDatabase. getInstance(applicationContext)
    val repository = AsteroidRepository(database)

        return try {
            repository.refreshList()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}