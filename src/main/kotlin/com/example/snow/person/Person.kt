package com.example.snow.person

import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity
class Person(
    @Id
    var id: Long,
    var name: String
)

data class PersonReq(
    val name: String
)

data class PersonRes(
    val id: Long, val name: String
)
