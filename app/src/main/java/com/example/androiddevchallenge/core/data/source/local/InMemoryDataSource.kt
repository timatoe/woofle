package com.example.androiddevchallenge.core.data.source.local

import androidx.lifecycle.*
import com.example.androiddevchallenge.core.data.Result
import com.example.androiddevchallenge.core.data.Result.*
import com.example.androiddevchallenge.core.data.source.DogsDataSource
import com.example.androiddevchallenge.core.models.Dog

object InMemoryDataSource : DogsDataSource {

    private val _dogs = mutableListOf<Dog>()

    private val _dogsObservable = MutableLiveData<List<Dog>>()

    override fun observeDog(dogId: Int): LiveData<Dog> = Transformations.map(_dogsObservable) { dogs ->
        dogs.find { dog -> dog.id == dogId }
    }

    override fun observeDogs(): LiveData<List<Dog>> = _dogsObservable

    override suspend fun getDog(dogId: Int): Result<Dog> {
        val dog = _dogs.find { dog -> dog.id == dogId }
        return dog?.let { Success(it) } ?: Error(Exception("Dog not found!"))
    }

    override suspend fun getDogs(): Result<List<Dog>> {
        return Success(_dogs)
    }

    override fun addDogs(dogs: List<Dog>) {
        _dogs.addAll(dogs)
        _dogsObservable.postValue(dogs)
    }

}