package com.example.data

import com.example.data.Player
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface PlayerRepository : CrudRepository<Player, String> {
    fun findAllOrderByScoreDesc(): List<Player>
}