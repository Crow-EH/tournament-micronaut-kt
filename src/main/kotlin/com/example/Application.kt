package com.example

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    servers = [Server(url = "/api/v1")],
    info =
        Info(
            title = "Tournament API",
            version = "0.1",
            description = "A very basic api to handle one score board at a time",
        ),
)
object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        run(*args)
    }
}
