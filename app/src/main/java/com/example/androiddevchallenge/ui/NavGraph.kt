package com.example.androiddevchallenge.ui

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.androiddevchallenge.ui.utils.Navigator
import kotlinx.parcelize.Parcelize

sealed class Destination : Parcelable {
    @Parcelize
    object Dogs : Destination()

    @Immutable
    @Parcelize
    data class DogDetail(val dogId: Int) : Destination()
}

class Actions(navigator: Navigator<Destination>) {
    val selectDog: (Int) -> Unit = { dogId: Int ->
        navigator.navigate(Destination.DogDetail(dogId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
