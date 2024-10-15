package com.example.service

import com.example.data.Player
import com.example.data.PlayerRepository
import com.example.model.PlayerDto
import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Singleton

@Singleton
open class PlayerService(private val playerRepository: PlayerRepository) {
    @Transactional(readOnly = true)
    open fun findAllPlayerForActiveTournament(): List<PlayerDto> {
        return playerRepository.findAllRanked().map {
            PlayerDto(it.nickname, it.score, it.rank)
        }
    }

    @Transactional(readOnly = true)
    open fun findOnePlayerForActiveTournament(nickname: String): PlayerDto {
        return playerRepository.findByIdRanked(nickname)
            .map { PlayerDto(it.nickname, it.score, it.rank) }
            .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Player $nickname does not exist") }
    }

    @Transactional
    open fun createPlayer(player: PlayerDtoCreate) {
        playerRepository.save(Player(player.nickname))
    }

    @Transactional
    open fun updatePlayer(
        nickname: String,
        playerUpdate: PlayerDtoUpdate,
    ) {
        val player =
            playerRepository.findById(nickname)
                .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Player $nickname does not exist") }
        player.score = playerUpdate.score
        playerRepository.update(player)
    }

    @Transactional
    open fun deleteAllPlayers() {
        playerRepository.deleteAll()
    }
}
