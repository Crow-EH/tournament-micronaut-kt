package com.example.data

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface PlayerRepository : CrudRepository<Player, String> {

    @Query(value = "" +
            "SELECT nickname, score, rank() over (ORDER BY score DESC) as rank " +
            "FROM player " +
            "ORDER BY score DESC",
        nativeQuery = true)
    fun findAllRanked(): List<RankedPlayer>

    @Query(value = "" +
            "WITH ranked AS (SELECT nickname, rank() over (ORDER BY score DESC) as rank " +
            "  FROM player" +
            ")" +
            "SELECT player.nickname, player.score, ranked.rank " +
            "FROM player " +
            "LEFT JOIN ranked ON player.nickname = ranked.nickname " +
            "WHERE player.nickname = :nickname " +
            "ORDER BY score DESC",
        nativeQuery = true)
    fun findByIdRanked(nickname: String): Optional<RankedPlayer>
}