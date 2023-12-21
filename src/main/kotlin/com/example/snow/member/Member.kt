package com.example.snow.member

import com.example.snow.BaseEntity
import jakarta.persistence.Entity


@Entity
class Member(
    var email: String,
    var password: String,
    var name: String,
) : BaseEntity()

data class CreateMemberReq(
    var email: String,
    var password: String,
    var name: String,
)

data class CreateMemberRes(
    var id: Long = -1,
)

data class SignInReq(
    var email: String,
    var password: String,
)

data class SignInRes(
    var id: Long = -1,
)