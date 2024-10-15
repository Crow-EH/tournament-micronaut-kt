package com.example

import com.example.controller.PlayerController.Companion.PATH
import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@MicronautTest
class PlayerControllerTest(val spec: RequestSpecification) {
    @AfterEach
    fun cleanup() {
        spec.`when`()
            .delete(PATH)
            .then()
            .statusCode(200)
    }

    @Test
    fun fullScenario() {
        val nick1 = "bob"
        val nick2 = "tim"

        spec.getAllPlayers()
            .body("size()", equalTo(0))

        spec.createPlayer(nick1)

        spec.createPlayer(nick2)

        spec.getAllPlayers()
            .body("size()", equalTo(2))

        spec.getPlayer(nick1)
            .body("nickname", equalTo(nick1))
            .body("score", equalTo(0))

        spec.updatePlayerScore(nick1, 2)

        spec.getPlayer(nick1)
            .body("nickname", equalTo(nick1))
            .body("score", equalTo(2))

        spec.`when`()
            .delete(PATH)
            .then()
            .statusCode(200)

        spec.getAllPlayers()
            .body("size()", equalTo(0))
    }

    @Test
    fun shouldBeOrderedByScoreDescAndHaveRank() {
        val nick1 = "bob"
        val nick2 = "lea"
        val nick3 = "tim"

        spec.getAllPlayers()
            .body("size()", equalTo(0))

        spec.createPlayer(nick1)

        spec.createPlayer(nick2)

        spec.createPlayer(nick3)

        spec.updatePlayerScore(nick2, 2)

        spec.updatePlayerScore(nick1, 1)

        spec.getAllPlayers()
            .body("size()", equalTo(3))
            .body("[0].nickname", equalTo(nick2))
            .body("[0].score", equalTo(2))
            .body("[0].rank", equalTo(1))
            .body("[1].nickname", equalTo(nick1))
            .body("[1].score", equalTo(1))
            .body("[1].rank", equalTo(2))
            .body("[2].nickname", equalTo(nick3))
            .body("[2].score", equalTo(0))
            .body("[2].rank", equalTo(3))

        spec.getPlayer(nick2)
            .body("rank", equalTo(1))

        spec.getPlayer(nick1)
            .body("rank", equalTo(2))

        spec.getPlayer(nick3)
            .body("rank", equalTo(3))
    }

    private fun RequestSpecification.getAllPlayers(): ValidatableResponse {
        return this
            .`when`()
            .get(PATH)
            .then()
            .statusCode(200)
    }

    private fun RequestSpecification.getPlayer(nickname: String): ValidatableResponse {
        return this
            .`when`()
            .get("$PATH/$nickname")
            .then()
            .statusCode(200)
    }

    private fun RequestSpecification.createPlayer(nickname: String): ValidatableResponse {
        return this
            .given()
            .body(PlayerDtoCreate(nickname))
            .contentType(ContentType.JSON)
            .`when`()
            .post(PATH)
            .then()
            .statusCode(200)
    }

    private fun RequestSpecification.updatePlayerScore(
        nickname: String,
        newScore: Int,
    ): ValidatableResponse {
        return this
            .given()
            .body(PlayerDtoUpdate(newScore))
            .contentType(ContentType.JSON)
            .`when`()
            .put("$PATH/$nickname")
            .then()
            .statusCode(200)
    }
}
