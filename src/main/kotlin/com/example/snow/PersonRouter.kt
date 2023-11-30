package com.example.snow

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.*


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
        }
    }

    @Component
    class PersonHandler(private val personService: PersonService, private val personRepository: PersonRepository) {
        suspend fun findAll(request: ServerRequest): ServerResponse {
            return personService.findAll().let { people ->
                val result = people.map {
                    PersonRes(
                        it.id,
                        it.name
                    )
                }
                ServerResponse.ok().bodyValueAndAwait(result)
            }
        }

        suspend fun findById(request: ServerRequest): ServerResponse {
            val id = request.pathVariable("id").toLong()
            return personService.findById(id).let {
                ServerResponse.ok().bodyValueAndAwait(it)
            }
        }

        suspend fun save(request: ServerRequest): ServerResponse {
            val person = request.awaitBody<Person>()
            return personService.save(person).let {
                ServerResponse.ok().bodyValueAndAwait(it)
            }
        }

        suspend fun delete(request: ServerRequest): ServerResponse {
            val id = request.pathVariable("id").toLong()
            return personService.deleteById(id).let {
                ServerResponse.ok().bodyValueAndAwait(it)
            }
        }
    }

}

