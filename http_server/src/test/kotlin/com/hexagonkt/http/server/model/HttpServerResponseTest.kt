package com.hexagonkt.http.server.model

import com.hexagonkt.core.media.TextMedia.HTML
import com.hexagonkt.core.media.TextMedia.RICHTEXT
import com.hexagonkt.http.model.*
import com.hexagonkt.http.model.ClientErrorStatus.NOT_FOUND
import com.hexagonkt.http.model.SuccessStatus.OK
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

internal class HttpServerResponseTest {

    private fun httpServerResponseData(
        contentType: ContentType? = ContentType(HTML)
    ): HttpServerResponse =
            HttpServerResponse(
                body = "response",
                headers = Headers(Header("hr1", "hr1v1", "hr1v2")),
                contentType = contentType,
                cookies = listOf(Cookie("cn", "cv")),
                status = NOT_FOUND,
            )

    @Test fun `HTTP Server Response comparison works ok`() {
        val httpServerRequest = httpServerResponseData()

        assertEquals(httpServerRequest, httpServerRequest)
        assertEquals(httpServerResponseData(), httpServerResponseData())
        assertFalse(httpServerRequest.equals(""))

        val headers = Headers(Header("h1", "v1"))
        val cookies = listOf(Cookie("p", "v"))
        val contentType = ContentType(RICHTEXT)

        assertNotEquals(httpServerRequest, httpServerRequest.copy(body = "body"))
        assertNotEquals(httpServerRequest, httpServerRequest.copy(headers = headers))
        assertNotEquals(httpServerRequest, httpServerRequest.copy(contentType = contentType))
        assertNotEquals(httpServerRequest, httpServerRequest.copy(cookies = cookies))
        assertNotEquals(httpServerRequest, httpServerRequest.copy(status = OK))

        assertEquals(httpServerRequest.hashCode(), httpServerResponseData().hashCode())
        assertEquals(
            httpServerRequest.copy(contentType = null).hashCode(),
            httpServerResponseData(null).hashCode()
        )
    }
}
