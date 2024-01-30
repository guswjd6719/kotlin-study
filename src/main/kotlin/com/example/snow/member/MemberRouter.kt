package com.example.snow.member

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter {

    val basePath = "/member"
    @Bean
    fun memberRoute(handler: MemberHandler) = coRouter {
        path(basePath).nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/{id}", handler::findById)
                GET("/", handler::findAll)
                POST("/sign-up", handler::create)
                POST("/sign-in", handler::signIn)
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}