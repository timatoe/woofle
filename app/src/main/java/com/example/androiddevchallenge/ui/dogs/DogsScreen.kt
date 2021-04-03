package com.example.androiddevchallenge.ui.dogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier

@Composable
fun DogsScreen(
    dogsViewModel: DogsViewModel,
    onDogClick: (Int) -> Unit
) {
    val viewState: DogsViewState by dogsViewModel.dogsViewState.observeAsState(DogsViewState.Loading)
    DogsView(
        viewState,
        onDogClick
    )
}

@Composable
private fun DogsView(
    viewState: DogsViewState,
    onDogClick: (Int) -> Unit
) {
    Scaffold {
        val modifier = Modifier.padding(it)
        Surface(modifier.fillMaxSize()) {
            Box {
                when (viewState) {
                    DogsViewState.Loading -> {
                    }
                    is DogsViewState.Complete -> DogsList(
                        viewState.dogs,
                        onDogClick,
                        modifier
                    )
                    DogsViewState.Error -> {
                    }
                }
            }
        }
    }
}

