package com.example.task5.data

import com.example.task5.api.data.Cat

data class CatResponse(
    val cats: List<Cat>,
    val count: Int,
    val limit: Int,
    val page: Int,
)
