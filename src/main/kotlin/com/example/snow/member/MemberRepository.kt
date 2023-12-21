package com.example.snow.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>{
    fun findByEmail(email: String): Member

}