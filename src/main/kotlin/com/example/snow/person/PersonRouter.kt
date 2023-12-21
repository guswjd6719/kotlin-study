package com.example.snow.person

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class PersonRouter {
    val basePath ="/person"
    @Bean
    fun personRoute(handler: PersonHandler) = coRouter {
        path(basePath).nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", handler::findAll)
                GET("/{id}", handler::findById)
                PATCH("/", handler::save)
                DELETE("/{id}", handler::delete)
                POST("/", handler::redisTest)
            }
        }
    }
}

