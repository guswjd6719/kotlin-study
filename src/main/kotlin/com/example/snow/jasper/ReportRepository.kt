package com.example.snow.jasper

import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<Employee, Long> {

}