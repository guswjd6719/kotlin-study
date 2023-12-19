package com.example.snow.member

import com.example.snow.BaseEntity
import jakarta.persistence.Entity


@Entity
class Member(
    var email: String,
    var password: String,
    var name: String,
) : BaseEntity()

