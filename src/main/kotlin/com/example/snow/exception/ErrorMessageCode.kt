package com.example.snow.exception

enum class ErrorMessageCode(val code: String, val message: String, val languageCode: String?) {
    OK("0000", "ok", null),
    MEMBER_NOT_FOUND("1000", "member not found",null),
    MEMBER_EMAIL_ALREADY_USE("1001", "email already use", null),
    MEMBER_PASSWORD_NOT_MATCH("1002", "email already use", null),

}