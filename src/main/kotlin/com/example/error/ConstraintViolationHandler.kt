package com.example.error

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.hibernate.exception.ConstraintViolationException

@Singleton
@Produces
class ConstraintViolationHandler : ExceptionHandler<ConstraintViolationException, HttpResponse<String>> {
    override fun handle(request: HttpRequest<*>?, exception: ConstraintViolationException?): HttpResponse<String> {
        return HttpResponse.badRequest(exception?.message ?: "unknown sql constraint violation")
    }
}