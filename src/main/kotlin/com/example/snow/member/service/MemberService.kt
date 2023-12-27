package com.example.snow.member.service

import com.example.snow.member.*

interface MemberService {
    fun findAll(): List<Member>
    fun findById(id: Long): Member
    fun findByEmail(email: String): Member?
    fun save(memberReq: CreateMemberReq): CreateMemberRes
    fun signIn(signInReq: SignInReq): SignInRes
}