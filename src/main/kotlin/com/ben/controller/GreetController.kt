package com.ben.controller

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.serde.annotation.Serdeable.Deserializable

@Introspected
@Deserializable
data class Person(
    val name: String
)

@Controller("/")
class GreetController {
    @Get("/greet")
    fun greet(
        @QueryValue name: String?
    ): String {
        return "Hello, ${name ?: "world"}!"
    }

    @Get("/greet/{name}")
    fun greetPerson(name: String): String {
        return "Hello, $name!"
    }

    @Post("/greet")
    fun greet(@Body person: Person): String {
        return "Hello, ${person.name}!"
    }
}