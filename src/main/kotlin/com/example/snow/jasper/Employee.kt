package com.example.snow.jasper

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "employee")
class Employee {
    @Id
    var id: Long? = null

    var firstName: String? = null
    var lastName: String? = null
    var salary: Long? = null

}