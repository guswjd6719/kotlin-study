package com.example.snow.member

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter {

    val basePath = "/member"
    @Bean
    fun memberRoute(handler: MemberHandler) = coRouter {
        path(basePath).nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", handler::findAll)
                POST("/", handler::create)
            }
        }
    }
}