package com.example.service

import com.example.data.Player
import com.example.data.PlayerRepository
import com.example.model.PlayerDto
import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class PlayerService(private val playerRepository: PlayerRepository) {

    fun findAllPlayerForActiveTournament(): List<PlayerDto> {
        return playerRepository.findAll().map {
            PlayerDto(it.nickname, it.score)
        }
    }

    fun findOnePlayerForActiveTournament(nickname: String): PlayerDto {
        return playerRepository.findById(nickname)
                .map { PlayerDto(it.nickname, it.score) }
                .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Player $nickname does not exist") }
    }

    fun createPlayer(player: PlayerDtoCreate) {
        playerRepository.save(Player(player.nickname))
    }

    fun updatePlayer(nickname: String, playerUpdate: PlayerDtoUpdate) {
        val player = playerRepository.findById(nickname)
                .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Player $nickname does not exist") }
        player.score = playerUpdate.score
        playerRepository.update(player)
    }

    fun deleteAllPlayers() {
        playerRepository.deleteAll()
    }

}