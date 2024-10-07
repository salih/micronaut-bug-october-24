package com.example

import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.json.JsonSyntaxException
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton

@Controller("/test")
class DemoController {

    @Post
    fun test(@Body payload: Payload): String {
        return "OK"
    }

}

@Serdeable
data class Payload(val status: String)

@Primary
@Produces
@Singleton
@Requirements(Requires(classes = [ConversionErrorException::class, ExceptionHandler::class]))
class ConversionErrorExceptionHandler : ExceptionHandler<ConversionErrorException, HttpResponse<*>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: ConversionErrorException
    ): HttpResponse<*> {
        return HttpResponse.status<String>(HttpStatus.BAD_REQUEST).body("ConversionException is thrown")
    }
}

@Primary
@Produces
@Singleton
@Requirements(Requires(classes = [JsonSyntaxException::class, ExceptionHandler::class]))
class JsonSyntaxExceptionHandler : ExceptionHandler<JsonSyntaxException, HttpResponse<*>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: JsonSyntaxException
    ): HttpResponse<*> {
        return HttpResponse.status<String>(HttpStatus.BAD_REQUEST).body("JsonSyntaxException is thrown")
    }
}
