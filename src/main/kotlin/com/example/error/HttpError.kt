package com.example.error

import io.micronaut.http.HttpStatus

class HttpError(message: String, status: HttpStatus) : RuntimeException(message) {
}