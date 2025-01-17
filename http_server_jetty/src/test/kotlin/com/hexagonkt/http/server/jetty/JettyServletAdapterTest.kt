package com.hexagonkt.http.server.jetty

import com.hexagonkt.core.media.TextMedia.PLAIN
import com.hexagonkt.http.client.HttpClient
import com.hexagonkt.http.client.jetty.JettyClientAdapter
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.model.SuccessStatus.OK
import com.hexagonkt.http.server.handlers.path
import kotlin.test.Test
import org.junit.jupiter.api.condition.EnabledForJreRange
import org.junit.jupiter.api.condition.JRE.JAVA_17
import org.junit.jupiter.api.condition.JRE.JAVA_19
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class JettyServletAdapterTest {

    @Test
    @EnabledForJreRange(min = JAVA_19)
    fun `Virtual threads can be enabled`() {
        JettyServletAdapter(useVirtualThreads = true)
    }

    @Test
    @EnabledForJreRange(max = JAVA_17)
    fun `Virtual threads are disabled`() {
        assertFailsWith<IllegalStateException> { JettyServletAdapter(useVirtualThreads = true) }
    }

    @Test fun `Stop method works if called before running`() {
        val adapter = JettyServletAdapter()
        assert(!adapter.started())
        adapter.shutDown()
        assert(!adapter.started())
    }

    @Test fun `Getting the runtime port on stopped instance raises an exception`() {
        assertFailsWith<IllegalStateException> {
            JettyServletAdapter().runtimePort()
        }
    }

    @Test fun `Serve helper works properly`() {

        val path = path {
            get("/hello/{name}") {
                val name = pathParameters["name"]
                ok("Hello $name!", contentType = ContentType(PLAIN))
            }
        }
        val server = serve(handlers = listOf(path))

        server.use { s ->
            HttpClient(JettyClientAdapter(), URL("http://localhost:${s.runtimePort}")).use {
                it.start()
                val result = it.get("/hello/Ada")
                assertEquals("Hello Ada!", result.body)
                assertEquals(OK, result.status)
                assertEquals(ContentType(PLAIN), result.contentType)
            }
        }
    }
}
