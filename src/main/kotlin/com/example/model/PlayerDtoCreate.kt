package com.example.model

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class PlayerDtoCreate(@field:NotBlank val nickname: String)