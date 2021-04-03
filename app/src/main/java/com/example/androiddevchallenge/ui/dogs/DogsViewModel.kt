package com.example.androiddevchallenge.ui.dogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.core.data.DogsRepositoryImpl
import com.example.androiddevchallenge.core.models.Dog

class DogsViewModel(
    private val dogsRepository: DogsRepositoryImpl
): ViewModel() {
    private val _dogsViewState = MutableLiveData<DogsViewState>()
    val dogsViewState: LiveData<DogsViewState> = _dogsViewState

    private val _dogsObservable = dogsRepository.observeDogs()
    private val _dogsObserver = Observer<List<Dog>> {
        _dogsViewState.postValue(DogsViewState.Complete(it))
    }

    init {
        _dogsObservable.observeForever(_dogsObserver)
    }

    override fun onCleared() {
        _dogsObservable.removeObserver(_dogsObserver)
        super.onCleared()
    }
}

sealed class DogsViewState {
    object Loading: DogsViewState()
    data class Complete(val dogs: List<Dog>): DogsViewState()
    object Error: DogsViewState()
}