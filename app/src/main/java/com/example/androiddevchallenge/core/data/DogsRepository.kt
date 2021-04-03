package com.example.androiddevchallenge.core.data

import androidx.lifecycle.LiveData
import com.example.androiddevchallenge.core.models.Dog

interface DogsRepository {

    fun observeDog(dogId: Int): LiveData<Dog>

    fun observeDogs(): LiveData<List<Dog>>

    suspend fun getDog(dogId: Int): Result<Dog>

    suspend fun getDogs(): Result<List<Dog>>

    fun addDogs(dogs: List<Dog>)
}