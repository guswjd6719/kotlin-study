package com.example.snow.member.service.impl

import com.example.snow.member.*
import com.example.snow.member.service.MemberService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) : MemberService {

    @Transactional(readOnly = true)
    override fun findAll(): List<Member> {
        return memberRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Member {
        return memberRepository.findById(id).orElseThrow()
    }

    @Transactional
    override fun save(memberReq: CreateMemberReq): CreateMemberRes {
        val encodedPassword = encodePasswordFromBase64ToBCrypt(memberReq.password)
        return CreateMemberRes(memberRepository.save(
                Member(
                    email = memberReq.email,
                    password = encodedPassword,
                    name = memberReq.name,
                )
            ).id!!
        )
    }


    private fun encodePasswordFromBase64ToBCrypt(base64Password: String): String {
        val decodedPassword = String(Base64.getDecoder().decode(base64Password))
        return passwordEncoder.encode(decodedPassword)
    }
    @Transactional
    override fun signIn(signInReq: SignInReq): SignInRes {
        return SignInRes(memberRepository.findByEmail(signInReq.email).id!!)
    }
}