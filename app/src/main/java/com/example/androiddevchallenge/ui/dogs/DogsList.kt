package com.example.androiddevchallenge.ui.dogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.core.models.Dog
import com.example.androiddevchallenge.ui.common.DogImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun DogsList(
    dogs: List<Dog>,
    onDogClick: (Int) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier.statusBarsPadding()) {
        items(dogs) { dog ->
            DogItem(dog, onDogClick)
        }
    }
}

@Composable
private fun DogItem(
    dog: Dog,
    onDogClick: (Int) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = { onDogClick(dog.id) })
                .padding(8.dp)
        ) {
            DogImage(
                imageUrl = dog.imageUrl,
                elevation = 4.dp,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = dog.name,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

