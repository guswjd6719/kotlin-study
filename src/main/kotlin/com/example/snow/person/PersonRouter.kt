package com.example.snow

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.coRouter

@Configuration
@EnableWebFlux
class PersonRouter : WebFluxConfigurer {

    @Bean
    fun routerFunction(handler: PersonHandler): RouterFunction<*> = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/", handler::findAll)
            GET("/{id}", handler::findById)
            PATCH("/", handler::save)
            DELETE("/{id}", handler::delete)
            POST("/", handler::redisTest)
        }
    }
}

