package com.example.snow.person

import com.example.snow.person.view.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>