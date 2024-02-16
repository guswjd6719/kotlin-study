package com.example.snow.jasper

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class JasperRouter {

    val basePath = "/jasper"
    @Bean
    fun jasperRoute(handler: JasperHandler) = coRouter {
        path(basePath).nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", handler::makeReport)
            }
        }
    }
}