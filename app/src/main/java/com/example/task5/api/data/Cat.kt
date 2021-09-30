package com.example.task5.api.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat (
    val breeds : List<String>,
    val id : String,
    val url : String,
    val width : Int,
    val height : Int
)
