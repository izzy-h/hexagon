package com.hexagonkt.http.model.ws

enum class CloseStatus(override val code: Int) : WsCloseStatus {
    /**
     * 1000 indicates a normal closure, meaning that the purpose for which the connection was
     * established has been fulfilled.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    NORMAL(1000),

    /**
     * 1001 indicates that an endpoint is "going away", such as a server going down or a browser
     * having navigated away from a page.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    SHUTDOWN(1001),

    /**
     * 1002 indicates that an endpoint is terminating the connection due to a protocol error.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    PROTOCOL(1002),

    /**
     * 1003 indicates that an endpoint is terminating the connection because it has received a type
     * of data it cannot accept (e.g., an endpoint that understands only text data MAY send this if
     * it receives a binary message).
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    BAD_DATA(1003),

    /**
     * Reserved. The specific meaning might be defined in the future.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    UNDEFINED(1004),

    /**
     * 1005 is a reserved value and MUST NOT be set as a status code in a Close control frame by an
     * endpoint. It is designated for use in applications expecting a status code to indicate that
     * no status code was actually present.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    NO_CODE(1005),

    /**
     * 1006 is a reserved value and MUST NOT be set as a status code in a Close control frame by an
     * endpoint. It is designated for use in applications expecting a status code to indicate that
     * the connection was closed abnormally, e.g., without sending or receiving a Close control
     * frame.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    NO_CLOSE(1006),

    /**
     * Abnormal Close is a synonym for {@link #NO_CLOSE}, used to indicate a close * condition where
     * no close frame was processed from the remote side.
     */
    ABNORMAL(NO_CLOSE.code),

    /**
     * 1007 indicates that an endpoint is terminating the connection because it has received data
     * within a message that was not consistent with the type of the message (e.g., non-UTF-8
     * [RFC3629](https://tools.ietf.org/html/rfc3629) data within a text message).
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    BAD_PAYLOAD(1007),

    /**
     * 1008 indicates that an endpoint is terminating the connection because it has received a
     * message that violates its policy. This is a generic status code that can be returned when
     * there is no other more suitable status code (e.g., 1003 or 1009) or if there is a need to
     * hide specific details about the policy.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    POLICY_VIOLATION(1008),

    /**
     * 1009 indicates that an endpoint is terminating the connection because it has received a
     * message that is too big for it to process.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    MESSAGE_TOO_LARGE(1009),

    /**
     * 1010 indicates that an endpoint (client) is terminating the connection because it has
     * expected the server to negotiate one or more extension, but the server didn't return them in
     * the response message of the WebSocket handshake. The list of extensions that are needed
     * SHOULD appear in the /reason/ part of the Close frame. Note that this status code is not used
     * by the server, because it can fail the WebSocket handshake instead.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    REQUIRED_EXTENSION(1010),

    /**
     * 1011 indicates that a server is terminating the connection because it encountered an
     * unexpected condition that prevented it from fulfilling the request.
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    SERVER_ERROR(1011),

    /**
     * 1012 indicates that the service is restarted. a client may reconnect, and if it chooses to
     * do, should reconnect using a randomized delay of 5 - 30s.
     *
     * See [hybi Additional WebSocket Close Error Codes](https://www.ietf.org/mail-archive/web/hybi/current/msg09649.html)
     */
    SERVICE_RESTART(1012),

    /**
     * 1013 indicates that the service is experiencing overload. a client should only connect to a
     * different IP (when there are multiple for the target) or reconnect to the same IP upon user
     * action.
     *
     * See [hybi Additional WebSocket Close Error Codes](https://www.ietf.org/mail-archive/web/hybi/current/msg09649.html)
     */
    TRY_AGAIN_LATER(1013),

    /**
     * 1014 indicates that a gateway or proxy received and invalid upstream response.
     *
     * See [hybi WebSocket Subprotocol Close Code: Bad Gateway](https://www.ietf.org/mail-archive/web/hybi/current/msg10748.html)
     */
    INVALID_UPSTREAM_RESPONSE(1014),

    /**
     * 1015 is a reserved value and MUST NOT be set as a status code in a Close control frame by an
     * endpoint. It is designated for use in applications expecting a status code to indicate that
     * the connection was closed due to a failure to perform a TLS handshake (e.g., the server
     * certificate can't be verified).
     *
     * See [RFC 6455, Section 7.4.1 Defined Status Codes](https://tools.ietf.org/html/rfc6455#section-7.4.1).
     */
    FAILED_TLS_HANDSHAKE(1015);

    companion object {
        private val map: Map<Int, WsCloseStatus> = values().associateBy { it.code }

        fun valueOfOrNull(code: Int): WsCloseStatus? =
            map[code]

        fun valueOf(code: Int): WsCloseStatus =
            valueOfOrNull(code) ?: CustomCloseStatus(code)

        operator fun get(code: Int): WsCloseStatus? =
            valueOfOrNull(code)
    }
}
