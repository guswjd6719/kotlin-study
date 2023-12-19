package com.example.snow.member.service.impl

import com.example.snow.member.Member
import com.example.snow.member.MemberRepository
import com.example.snow.member.service.MemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberService {

    @Transactional(readOnly = true)
    override fun findAll(): List<Member> {
        return memberRepository.findAll()
    }

    @Transactional
    override fun save(member: Member): Member {
        return memberRepository.save(member)
    }
}