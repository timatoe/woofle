package com.example.androiddevchallenge.ui.dogdetail

import androidx.lifecycle.*
import com.example.androiddevchallenge.core.data.DogsRepositoryImpl
import com.example.androiddevchallenge.core.data.Result
import com.example.androiddevchallenge.core.models.Dog
import kotlinx.coroutines.launch

class DogDetailViewModel(
    private val dogsRepository: DogsRepositoryImpl
): ViewModel() {
    private val _dogDetailViewState = MutableLiveData<DogDetailViewState>()
    val dogDetailViewState: LiveData<DogDetailViewState> = _dogDetailViewState

    fun getDog(dogId: Int) {
        viewModelScope.launch {
            val getDogResult = dogsRepository.getDog(dogId)
            val dogDetailViewState = when (getDogResult) {
                is Result.Error -> DogDetailViewState.Error
                Result.Loading -> DogDetailViewState.Loading
                is Result.Success -> DogDetailViewState.Complete(getDogResult.data)
            }
            _dogDetailViewState.postValue(dogDetailViewState)
        }
    }
}

sealed class DogDetailViewState {
    object Loading: DogDetailViewState()
    data class Complete(val dog: Dog): DogDetailViewState()
    object Error: DogDetailViewState()
}