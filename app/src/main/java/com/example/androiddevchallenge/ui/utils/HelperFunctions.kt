package com.example.androiddevchallenge.ui.utils;

import kotlin.math.roundToInt

fun lerp(start: Int, stop: Int, fraction: Float): Int {
        return start + ((stop - start) * fraction.toDouble()).roundToInt()
}