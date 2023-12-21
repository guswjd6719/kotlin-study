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

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        return memberService.findById(id).let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val member = request.awaitBody<CreateMemberReq>()
        return memberService.save(member).let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val signInReq = request.awaitBody<SignInReq>()
        return memberService.signIn(signInReq).let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }


}