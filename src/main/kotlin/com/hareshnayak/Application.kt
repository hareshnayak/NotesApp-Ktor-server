package com.hareshnayak

import com.hareshnayak.authentication.JwtService
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.hareshnayak.plugins.*
import com.hareshnayak.repository.DatabaseFactory
import com.hareshnayak.repository.Repo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.locations.*


fun main() {
        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        val jwtService = JwtService()
        val db = Repo()
            DatabaseFactory.init()
        install(Locations)
        install(Authentication){
            jwt("jwt"){
                verifier(jwtService.verifier)
                realm = "Note Server"
                validate {
                    val payload = it.payload
                    val email = payload.getClaim("email").asString()
                    val user = db.findUserByEmail(email)
                    user
                }
            }
        }
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
