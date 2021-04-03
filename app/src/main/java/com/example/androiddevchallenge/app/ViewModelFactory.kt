package com.example.androiddevchallenge.app

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.androiddevchallenge.core.data.DogsRepositoryImpl
import com.example.androiddevchallenge.ui.dogdetail.DogDetailViewModel
import com.example.androiddevchallenge.ui.dogs.DogsViewModel

class ViewModelFactory constructor(
    private val dogsRepository: DogsRepositoryImpl,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(DogsViewModel::class.java) ->
                DogsViewModel(dogsRepository)
            isAssignableFrom(DogDetailViewModel::class.java) ->
                DogDetailViewModel(dogsRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}