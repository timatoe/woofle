package com.example.androiddevchallenge.core.data

import com.example.androiddevchallenge.core.data.source.DogsDataSource
import com.example.androiddevchallenge.core.models.Dog

class DogsRepositoryImpl(
    private val inMemoryDataSource: DogsDataSource
) : DogsRepository {

    override fun observeDog(dogId: Int) = inMemoryDataSource.observeDog(dogId)

    override fun observeDogs() = inMemoryDataSource.observeDogs()

    override suspend fun getDog(dogId: Int) = inMemoryDataSource.getDog(dogId)

    override suspend fun getDogs() = inMemoryDataSource.getDogs()

    override fun addDogs(dogs: List<Dog>) {
        inMemoryDataSource.addDogs(dogs)
    }
}