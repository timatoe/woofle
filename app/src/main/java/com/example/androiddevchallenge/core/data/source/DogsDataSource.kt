package com.example.androiddevchallenge.core.data.source

import androidx.lifecycle.LiveData
import com.example.androiddevchallenge.core.models.Dog
import com.example.androiddevchallenge.core.data.Result as Result

interface DogsDataSource {

    fun observeDog(dogId: Int): LiveData<Dog>

    fun observeDogs(): LiveData<List<Dog>>

    suspend fun getDog(dogId: Int): Result<Dog>

    suspend fun getDogs(): Result<List<Dog>>

    fun addDogs(dogs: List<Dog>)
}