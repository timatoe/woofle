package com.example.androiddevchallenge.core.utils

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androiddevchallenge.core.data.DogsRepositoryImpl
import com.example.androiddevchallenge.core.data.source.local.InMemoryDataSource
import com.example.androiddevchallenge.core.workers.PopulateDataWorker

object ServiceLocator {

    @Volatile
    var dogsRepository: DogsRepositoryImpl? = null

    fun provideDogsRepository(context: Context): DogsRepositoryImpl {
        synchronized(this) {
            return dogsRepository ?: dogsRepository ?: createDogsRepository(context)
        }
    }

    private fun createDogsRepository(context: Context): DogsRepositoryImpl {
        val newRepo = DogsRepositoryImpl(InMemoryDataSource)
        dogsRepository = newRepo
        enqueuePopulateDataWorker(context)
        return newRepo
    }

    private fun enqueuePopulateDataWorker(context: Context) {
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<PopulateDataWorker>().build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }
}