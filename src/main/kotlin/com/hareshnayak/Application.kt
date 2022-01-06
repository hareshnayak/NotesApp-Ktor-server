package com.hareshnayak

import com.hareshnayak.authentication.JwtService
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.hareshnayak.plugins.*
import com.hareshnayak.repository.DatabaseFactory
import com.hareshnayak.repository.Repo

fun main() {
    DatabaseFactory.init()
    val db = Repo()


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
