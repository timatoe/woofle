package com.example.androiddevchallenge.ui.dogdetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.core.models.Dog
import com.example.androiddevchallenge.ui.common.DogImage
import com.example.androiddevchallenge.ui.utils.lerp
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun DogDetailScreen(
    dogId: Int,
    dogDetailViewModel: DogDetailViewModel,
    upPress: () -> Unit
) {
    val viewState: DogDetailViewState by dogDetailViewModel.dogDetailViewState.observeAsState(
        DogDetailViewState.Loading
    )
    dogDetailViewModel.getDog(dogId)
    DogDetailView(
        viewState,
        upPress
    )
}

@Composable
private fun DogDetailView(
    viewState: DogDetailViewState,
    upPress: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        when (viewState) {
            is DogDetailViewState.Complete -> {
                Header()
                Body(viewState.dog, scroll)
                Title(viewState.dog, scroll.value)
                Image(viewState.dog, scroll.value)
                Up(upPress)
            }
            DogDetailViewState.Error -> {
            }
            DogDetailViewState.Loading -> {
            }
        }
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.onPrimary)
    )
}

@Composable
private fun Body(
    dog: Dog,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.dog_details_description),
                        style = MaterialTheme.typography.overline,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = dog.description,
                        style = MaterialTheme.typography.body1,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun Title(
    dog: Dog,
    scroll: Int
) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .graphicsLayer { translationY = offset }
            .background(MaterialTheme.colors.onPrimary)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = dog.name,
            style = MaterialTheme.typography.h4,
            modifier = HzPadding,
        )
        Text(
            text = dog.feature,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = dog.adoptionFee,
            style = MaterialTheme.typography.h6,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        Divider()
    }
}

@Composable
private fun Image(
    dog: Dog,
    scroll: Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)

    CollapsingImageLayout(
        collapseFraction = collapseFraction,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        DogImage(
            imageUrl = dog.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFraction: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.place(imageX, imageY)
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}