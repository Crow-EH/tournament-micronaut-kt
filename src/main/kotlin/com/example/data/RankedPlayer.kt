package com.example.data

import io.micronaut.core.annotation.Introspected

@Introspected
data class RankedPlayer(
    val nickname: String,
    val score: Int,
    val rank: Int,
)
