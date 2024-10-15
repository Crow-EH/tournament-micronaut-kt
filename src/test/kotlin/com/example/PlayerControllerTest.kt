package com.example

import com.example.model.PlayerDtoCreate
import com.example.model.PlayerDtoUpdate
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@MicronautTest
class PlayerControllerTest {

    @Test
    fun fullScenario(spec: RequestSpecification) {
        val nick1 = "bob"
        val nick2 = "tim"

        spec
            .`when`()
            .get("/players")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0))

        spec
            .given()
            .body(PlayerDtoCreate(nick1))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/players")
            .then()
            .statusCode(200)

        spec
            .given()
            .body(PlayerDtoCreate(nick2))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/players")
            .then()
            .statusCode(200)

        spec
            .`when`()
            .get("/players")
            .then()
            .statusCode(200)
            .body("size()", equalTo(2))

        spec
            .`when`()
            .get("/players/$nick1")
            .then()
            .statusCode(200)
            .body("nickname", equalTo(nick1))
            .body("score", equalTo(0))

        spec
            .given()
            .body(PlayerDtoUpdate(2))
            .contentType(ContentType.JSON)
            .`when`()
            .put("/players/$nick1")
            .then()
            .statusCode(200)

        spec
            .`when`()
            .get("/players/$nick1")
            .then()
            .statusCode(200)
            .body("nickname", equalTo(nick1))
            .body("score", equalTo(2))

        spec
            .`when`()
            .delete("/players")
            .then()
            .statusCode(200)

        spec
            .`when`()
            .get("/players")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0))
    }

    @Test
    fun shouldBeOrderByScoreDescAndHaveARank(spec: RequestSpecification) {
        val nick1 = "bob"
        val nick2 = "lea"
        val nick3 = "tim"

        spec
            .`when`()
            .get("/players")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0))

        spec
            .given()
            .body(PlayerDtoCreate(nick1))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/players")
            .then()
            .statusCode(200)

        spec
            .given()
            .body(PlayerDtoCreate(nick2))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/players")
            .then()
            .statusCode(200)

        spec
            .given()
            .body(PlayerDtoCreate(nick3))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/players")
            .then()
            .statusCode(200)

        spec
            .given()
            .body(PlayerDtoUpdate(2))
            .contentType(ContentType.JSON)
            .`when`()
            .put("/players/$nick2")
            .then()
            .statusCode(200)

        spec
            .given()
            .body(PlayerDtoUpdate(1))
            .contentType(ContentType.JSON)
            .`when`()
            .put("/players/$nick1")
            .then()
            .statusCode(200)

        spec
            .`when`()
            .get("/players")
            .then()
            .statusCode(200)
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

        spec
            .`when`()
            .get("/players/$nick2")
            .then()
            .statusCode(200)
            .body("rank", equalTo(1))

        spec
            .`when`()
            .get("/players/$nick1")
            .then()
            .statusCode(200)
            .body("rank", equalTo(2))

        spec
            .`when`()
            .get("/players/$nick3")
            .then()
            .statusCode(200)
            .body("rank", equalTo(3))

        spec
            .`when`()
            .delete("/players")
            .then()
            .statusCode(200)
    }

}
