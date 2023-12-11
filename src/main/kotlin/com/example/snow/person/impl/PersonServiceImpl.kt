package com.example.snow.impl

import com.example.snow.Person
import com.example.snow.PersonRepository
import com.example.snow.PersonService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PersonServiceImpl(
    private val personRepository: PersonRepository,
) : PersonService {
    @Transactional(readOnly = true)
    override fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Person {
        return personRepository.findById(id).orElseThrow()
    }

    @Transactional
    override fun save(person: Person): Person {
        return personRepository.save(person)
    }

    @Transactional
    override fun deleteById(id: Long) {
        return personRepository.deleteById(id)
    }

}