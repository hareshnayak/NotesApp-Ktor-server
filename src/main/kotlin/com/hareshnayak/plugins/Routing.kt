package com.hareshnayak.plugins

import com.hareshnayak.authentication.JwtService
import com.hareshnayak.authentication.hash
import com.hareshnayak.data.model.User
import com.hareshnayak.repository.Repo
import com.hareshnayak.routes.NoteRoutes
import com.hareshnayak.routes.userRoutes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    val hashFunction = {s:String -> hash(s) }
    val jwtService = JwtService()
    val db = Repo()
    routing {

        userRoutes(db,jwtService,hashFunction)
        NoteRoutes(db,hashFunction)

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
