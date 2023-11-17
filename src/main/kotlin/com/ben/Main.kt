package com.ben
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8080, module = Application::mainApplicationModule)
    server.start(true)
}

fun Application.mainApplicationModule() {
    routing {
        get("/") {
            call.respondText("Hello world!")
        }
    }
}
