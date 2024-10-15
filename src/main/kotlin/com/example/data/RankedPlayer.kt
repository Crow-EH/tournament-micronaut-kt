package com.example.data

import io.micronaut.core.annotation.Introspected
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Introspected
data class RankedPlayer(
        val nickname: String,
        val score: Int,
        val rank: Int)