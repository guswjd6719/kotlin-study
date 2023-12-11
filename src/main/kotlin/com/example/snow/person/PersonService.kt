package com.example.snow.person

interface PersonService {

    fun findAll(): List<Person>

    fun findById(id: Long): Person

    fun save(person: Person): Person

    fun deleteById(id: Long)

}