package com.example.snow.person.service

import com.example.snow.person.Person

interface PersonService {

    fun findAll(): List<Person>

    fun findById(id: Long): Person

    fun save(person: Person): Person

    fun deleteById(id: Long)

}