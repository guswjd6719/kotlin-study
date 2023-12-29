package com.example.snow.member.service.impl

import com.example.snow.exception.ApplicationException
import com.example.snow.exception.ErrorMessageCode
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

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Member? {
        return memberRepository.findByEmail(email)
    }

    @Transactional
    override fun save(memberReq: CreateMemberReq): CreateMemberRes {
        val encodedPassword = encodePasswordFromBase64ToBCrypt(memberReq.password)
        if(memberRepository.findByEmail(memberReq.email) != null){
            throw Exception("email already exist")
        }
        return CreateMemberRes(memberRepository.save(
                Member(
                    email = memberReq.email,
                    password = encodedPassword,
                    name = memberReq.name,
                )
            ).id!!
        )
    }

    @Transactional
    override fun signIn(signInReq: SignInReq): SignInRes {
        val member = memberRepository.findByEmail(signInReq.email)
            ?: throw ApplicationException(ErrorMessageCode.MEMBER_NOT_FOUND)

        if(!passwordEncoder.matches(String(Base64.getDecoder().decode(signInReq.password)), member.password)){
            throw ApplicationException(ErrorMessageCode.MEMBER_PASSWORD_NOT_MATCH)
        }

        return SignInRes(member.id!!)
    }

    private fun encodePasswordBCrypt(basePassword: String): String {
        return passwordEncoder.encode(basePassword)
    }

    private fun encodePasswordFromBase64ToBCrypt(base64Password: String): String {
        //클라에서 Base64로 인코딩해서 보내게 될 때 사용
        val decodedPassword = String(Base64.getDecoder().decode(base64Password))
        return passwordEncoder.encode(decodedPassword)
    }
}