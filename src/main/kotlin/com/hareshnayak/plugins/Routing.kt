package com.hareshnayak.plugins

import com.hareshnayak.authentication.JwtService
import com.hareshnayak.authentication.hash
import com.hareshnayak.data.model.User
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    val hashFunction = {s:String -> hash(s) }
    val jwtService = JwtService()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/token"){
            get{
                val email = call.request.queryParameters["email"]!!
                val password = call.request.queryParameters["password"]!!
                val username = call.request.queryParameters["username"]!!

                val user = User(email,hashFunction(password),username )
                call.respond(jwtService.generateToken(user))
            }
        }
    }
}
