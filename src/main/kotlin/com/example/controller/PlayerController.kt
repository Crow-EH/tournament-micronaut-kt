package com.example.controller

import com.example.controller.PlayerController.Companion.PATH
import com.example.model.PlayerDto
import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import com.example.service.PlayerService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Tag(name = PATH)
@Controller(PATH)
open class PlayerController(private val playerService: PlayerService) {
    companion object {
        const val PATH = "/players"
    }

    @Get
    fun getAllPlayers(): List<PlayerDto> {
        return playerService.findAllPlayerForActiveTournament()
    }

    @Get("/{nickname}")
    fun getOnePlayer(
        @PathVariable nickname: String,
    ): PlayerDto {
        return playerService.findOnePlayerForActiveTournament(nickname)
    }

    @Post
    open fun createPlayer(
        @Valid @Body player: PlayerDtoCreate,
    ) {
        playerService.createPlayer(player)
    }

    @Put("/{nickname}")
    open fun updatePlayer(
        @PathVariable nickname: String,
        @Valid @Body player: PlayerDtoUpdate,
    ) {
        playerService.updatePlayer(nickname, player)
    }

    @Delete
    fun deleteAllPlayers() {
        playerService.deleteAllPlayers()
    }
}
