package todo.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


@Api(routeOverride = "getWeatherData")
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

    val response: HttpResponse = client.get("https://api.weather.gov/") {

        contentType(ContentType.Application.Json)

    }


    ctx.logger.debug("Received get weather response: ${response}")

    ctx.res.status = 200
}