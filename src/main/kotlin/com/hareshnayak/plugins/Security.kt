package com.hareshnayak.plugins

import com.hareshnayak.authentication.JwtService
import com.hareshnayak.repository.Repo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlin.collections.set

fun Application.configureSecurity() {

    val jwtService = JwtService()
    val db = Repo()

//    authentication{
//        jwt {
//            verifier(jwtService.verifier)
//            realm = "Note Server"
//            validate {
//                val payload = it.payload
//                val email = payload.getClaim("email").asString()
//                val user = db.findUserByEmail(email)
//                user
//            }
//        }
//    }

        //        jwt {
//            val jwtAudience = environment.config.property("jwt.audience").getString()
//            realm = environment.config.property("jwt.realm").getString()
//            verifier(
//                    JWT
//                            .require(Algorithm.HMAC256("secret"))
//                            .withAudience(jwtAudience)
//                            .withIssuer(environment.config.property("jwt.domain").getString())
//                            .build()
//            )
//            validate { credential ->
//                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
//            }
//        }
        data class MySession(val count: Int = 0)

        install(Sessions) {
            cookie<MySession>("MY_SESSION") {
                cookie.extensions["SameSite"] = "lax"
            }
        }

        routing {
            get("/session/increment") {
                val session = call.sessions.get<MySession>() ?: MySession()
                call.sessions.set(session.copy(count = session.count + 1))
                call.respondText("Counter is ${session.count}. Refresh to increment.")
            }
        }
    }

