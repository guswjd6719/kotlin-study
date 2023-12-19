package com.example.snow.member

import com.example.snow.member.service.MemberService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class MemberHandler(
    private val memberService: MemberService,
) {
    suspend fun findAll(request: ServerRequest): ServerResponse {
        return memberService.findAll().let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val member = request.awaitBody<Member>()
        return memberService.save(member).let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }

}