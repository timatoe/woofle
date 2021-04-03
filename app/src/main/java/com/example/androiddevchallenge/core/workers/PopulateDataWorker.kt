package com.example.androiddevchallenge.core.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androiddevchallenge.app.App
import com.example.androiddevchallenge.core.models.Dog
import com.example.androiddevchallenge.core.utils.DOGS_DATA_FILENAME
import com.example.androiddevchallenge.core.utils.ServiceLocator
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import kotlinx.coroutines.coroutineScope
import okio.Okio
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.buffer
import okio.source


class PopulateDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(DOGS_DATA_FILENAME).use { inputStream ->
                JsonReader.of(inputStream.source().buffer()).use { jsonReader ->
                    val moshi = Moshi.Builder().build()
                    val dogListType = Types.newParameterizedType(List::class.java, Dog::class.java)
                    val dogsAdapter: JsonAdapter<List<Dog>> = moshi.adapter(dogListType)
                    val dogs = dogsAdapter.fromJson(jsonReader)
                    dogs?.let {
                        App().dogsRepository.addDogs(it)
                    }
                    Result.success()
                }
            }
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}