package com.hexagonkt.http.client

import com.hexagonkt.http.client.model.HttpClientRequest
import com.hexagonkt.http.client.model.HttpClientResponse
import com.hexagonkt.http.model.Header
import com.hexagonkt.http.model.ServerEvent
import com.hexagonkt.http.model.ws.WsCloseStatus
import com.hexagonkt.http.model.ws.WsSession
import java.util.concurrent.Flow.Publisher

object VoidAdapter : HttpClientPort {
    var started: Boolean = false

    override fun startUp(client: HttpClient) {
        started = true
    }

    override fun shutDown() {
        started = false
    }

    override fun started() =
        started

    override fun send(request: HttpClientRequest): HttpClientResponse =
        HttpClientResponse(
            headers = request.headers + Header("-path-", request.path),
            body = request.body,
            contentType = request.contentType,
        )

    override fun sse(request: HttpClientRequest): Publisher<ServerEvent> =
        error("Unsupported operation")

    override fun ws(
        path: String,
        onConnect: WsSession.() -> Unit,
        onBinary: WsSession.(data: ByteArray) -> Unit,
        onText: WsSession.(text: String) -> Unit,
        onPing: WsSession.(data: ByteArray) -> Unit,
        onPong: WsSession.(data: ByteArray) -> Unit,
        onClose: WsSession.(status: WsCloseStatus, reason: String) -> Unit
    ): WsSession =
        error("Unsupported operation")
}
