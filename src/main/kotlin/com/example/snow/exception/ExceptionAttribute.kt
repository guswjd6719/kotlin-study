package com.example.snow.exception

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Component
class GlobalErrorAttributes(
    private val messageSource: MessageSource
) : DefaultErrorAttributes() {
    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any>? {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        var statusCode = HttpStatusCode.valueOf(500)
        val locale: Locale = request.exchange().localeContext.locale ?: Locale.US
        val errorAttributes = super.getErrorAttributes(request, options)
        val error = getError(request)
        var message = error.message
        if (error is ResponseStatusException) {
            message = error.reason
            statusCode = error.statusCode
            status = HttpStatus.valueOf(statusCode.value())
        }

        if (error is ApplicationException) {
            val exception = messageSource.getMessage(error.errorMessageCode.toString(), null, null, locale)
            if (error.errors != null) {
                errorAttributes["errors"] = error.errors!!
            }

            message = exception ?: error.errorMessageCode.message
            errorAttributes["code"] = error.errorMessageCode.code
        }
        errorAttributes["timestamp"] = Date()
        errorAttributes["path"] = request.path()
        if (message != null) {
            errorAttributes["message"] = message
        }
        errorAttributes["status"] = status.value()
        errorAttributes["error"] = HttpStatus.valueOf(status.value()).reasonPhrase

        return errorAttributes
    }

}
