package com.example.snow.jasper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class JasperHandler(
    private val employeeReportService: EmployeeReportService
) {
    suspend fun makeReport(request: ServerRequest): ServerResponse {
        return withContext(Dispatchers.IO) {
            employeeReportService.makeReport()
        }.let {
            ServerResponse.ok().bodyValueAndAwait(it)
        }
    }
}