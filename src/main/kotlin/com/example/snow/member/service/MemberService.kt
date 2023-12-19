package com.example.snow.member.service

import com.example.snow.member.Member

interface MemberService {
    fun findAll(): List<Member>
    fun save(member: Member): Member
}