package app.revolt.endpoints.auth

import app.revolt.RevoltApiJsonFactory
import app.revolt.RevoltApi.Companion.BASE_URL
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import java.io.File

fun mockHttpClient(
    responses: Map<String, String>,
) = HttpClient(MockEngine) {
    val jsonFiles = mutableMapOf<String, String>()
    responses.entries.forEach {
        jsonFiles["https://$BASE_URL/${it.key}"] = it.value
    }
    val headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
        }
    }

    install(ContentNegotiation) {
        val json = RevoltApiJsonFactory.create()
        json(json)
    }

    engine {
        addHandler { request ->
            val url = request.url.toString().decodeURLPart()

            val fileName = jsonFiles[url] ?: error("Unhandled url $url")
            val file = File("./src/jvmTest/resources/$fileName")
            val content = file.readText()
            respond(content = content, headers = headers)
        }
    }
}