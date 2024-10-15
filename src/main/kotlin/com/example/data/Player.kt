package com.example.data

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Player(
        @Id
        val nickname: String,
        var score: Int = 0)