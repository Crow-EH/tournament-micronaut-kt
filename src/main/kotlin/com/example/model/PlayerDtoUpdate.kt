package com.example.model

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Serdeable
data class PlayerDtoUpdate(@field:NotNull @field:Min(1) val score: Int)