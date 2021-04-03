package com.example.androiddevchallenge.app

import android.app.Application
import com.example.androiddevchallenge.core.data.DogsRepositoryImpl
import com.example.androiddevchallenge.core.utils.ServiceLocator

class App : Application() {

    val dogsRepository: DogsRepositoryImpl
        get() = ServiceLocator.provideDogsRepository(this)

}