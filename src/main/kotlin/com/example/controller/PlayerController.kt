package com.example.controller

import com.example.service.PlayerService
import com.example.model.PlayerDto
import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import io.micronaut.http.annotation.*
import jakarta.validation.Valid

@Controller("/players")
open class PlayerController(private val playerService: PlayerService) {

    @Get
    fun getAllPlayers() : List<PlayerDto> {
        return playerService.findAllPlayerForActiveTournament()
    }

    @Get("/{nickname}")
    fun getOnePlayer(@PathVariable nickname: String) : PlayerDto {
        return playerService.findOnePlayerForActiveTournament(nickname)
    }

    @Post
    fun createPlayer(@Body player: PlayerDtoCreate) {
        playerService.createPlayer(player)
    }

    @Put("/{nickname}")
    open fun updatePlayer(@PathVariable nickname: String, @Valid @Body player: PlayerDtoUpdate) {
        playerService.updatePlayer(nickname, player)
    }

    @Delete
    open fun deleteAllPlayers() {
        playerService.deleteAllPlayers()
    }

}