package com.example.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PlayerDto(
        val nickname: String,
        val score: Int)