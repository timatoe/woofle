package com.example.androiddevchallenge.core.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dog(
    val id: Int,
    val name: String,
    val feature: String,
    val species: String,
    val imageUrl: String,
    val description: String,
    val location: String,
    val rescueGroup: String,
    val age: String,
    val adoptionFee: String,
    val desexed: Boolean,
    val vaccinated: Boolean,
    val wormed: Boolean,
    val microchipNumber: String,
)