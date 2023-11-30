package com.example.snow

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.Repository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.*


@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {

    @Bean
    fun routerFunctionA(handler: PersonHandler): RouterFunction<*> = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/hello-world", handler::hello)
            POST("/save", handler::save)
        }
    }

    @Component
    class PersonHandler(private val personRepository: PersonRepository) {
        suspend fun hello(request: ServerRequest): ServerResponse {
            return personRepository.findAll().let {people->
                val result = people.map {
                    PersonRes(
                        it.id,
                        it.name
                    )
                }
                ServerResponse.ok().bodyValueAndAwait(result)
            }
        }
        suspend fun save(request: ServerRequest): ServerResponse {
            val person = request.awaitBody<Person>()
            return personRepository.save(person).let {
                ServerResponse.ok().bodyValueAndAwait(it)
            }
        }
    }

}

data class PersonRes(
    val id: Long,
    val name: String
)