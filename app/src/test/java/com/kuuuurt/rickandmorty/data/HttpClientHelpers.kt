package com.kuuuurt.rickandmorty.data

import org.junit.Assert.assertEquals
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.content.OutgoingContent
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.utils.io.core.String

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

fun mockEngine(requests: Map<String, String> = mapOf()) = MockEngine.create {
    addHandler { request ->
        when (val url = request.url.fullUrl) {
            in requests.keys -> {
                respond(
                    content = requests[url] ?: error("Request has no response"),
                    headers = headersOf(
                        "Content-Type" to listOf(ContentType.Application.Json.toString())
                    )
                )
            }
            else -> error("Unhandled ${request.url.fullUrl}")
        }
    }
}

inline fun mockEngineForAssertion(
    url: String,
    response: String,
    crossinline assertRequest: (HttpRequestData) -> Unit
) = MockEngine.create {
    addHandler { request ->
        assertEquals(url, request.url.fullUrl)
        assertRequest(request)
        respond(
            content = response,
            headers = headersOf(
                "Content-Type" to listOf(ContentType.Application.Json.toString())
            )
        )
    }
}

val OutgoingContent.json get() = String((this as OutgoingContent.ByteArrayContent).bytes())
val Url.fullUrl get() = "${protocol.name}://$host$fullPath"
