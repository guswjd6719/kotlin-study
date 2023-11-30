package com.example.snow

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository


@Entity
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    var name: String
)


interface PersonRepository : JpaRepository<Person, Long>
