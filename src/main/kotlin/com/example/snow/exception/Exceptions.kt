package com.example.snow.exception

open class ApplicationException(
    val errorMessageCode: ErrorMessageCode
) : RuntimeException(errorMessageCode.message) {
    open var errors: List<ApiError>? = null
        set(value) {
            errors = value
        }
}

data class ApiError(
    var code: String, var message: String
)