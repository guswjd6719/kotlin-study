package com.example.snow.person

import com.example.snow.person.service.PersonService
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait


@Component
class PersonHandler(
    private val personService: PersonService,
    private val redisTemplate: RedisTemplate<String, String>
) {
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

    suspend fun redisTest(request: ServerRequest): ServerResponse {
        // path로 넘겨받기
        val key = request.queryParam("key").get()
        val value = request.queryParam("value").get()
        val count = request.queryParam("count").get()

        //lua script 파일 사용 - key:value를 set하고 get
        //TODO - path- enum으로 따로 빼기
        val luaSetStockPath = "/lua/set-stock.lua"
        val setStockScript = DefaultRedisScript<Long>()
        setStockScript.setLocation(ClassPathResource(luaSetStockPath))
        setStockScript.setResultType(Long::class.java)

        val vStock = "$key:$value"

        return redisTemplate.execute(
            setStockScript, mutableListOf(vStock), count
        ).let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }
}