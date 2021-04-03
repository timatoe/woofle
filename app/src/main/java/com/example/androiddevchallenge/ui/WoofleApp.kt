package com.example.androiddevchallenge.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.androiddevchallenge.ui.dogdetail.DogDetailScreen
import com.example.androiddevchallenge.ui.dogdetail.DogDetailViewModel
import com.example.androiddevchallenge.ui.dogs.DogsScreen
import com.example.androiddevchallenge.ui.dogs.DogsViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.utils.Navigator
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun WoofleApp(
    dogsViewModel: DogsViewModel,
    dogDetailViewModel: DogDetailViewModel,
    onBackPressedDispatcher: OnBackPressedDispatcher
) {
    val navigator: Navigator<Destination> = rememberSaveable(
        saver = Navigator.saver(onBackPressedDispatcher)
    ) {
        Navigator(Destination.Dogs, onBackPressedDispatcher)
    }

    val actions = remember(navigator) {
        Actions(navigator)
    }

    MyTheme {
        ProvideWindowInsets {
            Crossfade(navigator.current) {
                when (it) {
                    Destination.Dogs -> DogsScreen(
                        dogsViewModel,
                        actions.selectDog
                    )
                    is Destination.DogDetail -> DogDetailScreen(
                        it.dogId,
                        dogDetailViewModel,
                        actions.upPress
                    )
                }
            }
        }
    }
}