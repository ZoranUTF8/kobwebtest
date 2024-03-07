package todo.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


@Api(routeOverride = "getweather")
suspend fun getWeather(ctx: ApiContext) {

    ctx.logger.debug("Received get weather request")

    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    val response: HttpResponse = client.post("https://httpbin.org/post") {
        contentType(ContentType.Application.Json)
    }


    ctx.logger.debug("Received get weather response: $response")

    ctx.res.setBodyText(response.toString())
    ctx.res.status = 200
}