package com.hexagonkt.core

import kotlin.test.Test
import kotlin.IllegalArgumentException
import java.net.URL
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ChecksTest {

    data class DataClass(
        val text: String? = "text",
        val texts: List<String>? = listOf("text 1", "text 2"),
        val date: LocalDate? = LocalDate.now(),
        val url: URL? = URL("https://example.com"),
        val urls: List<URL>? = listOf(URL("https://example.com")),
        val integer: Int? = 1,
        val decimal: Double? = 1.0,
    )

    @Test fun `Require methods work properly`() {
        val data = DataClass()
        data.requireNotBlank(DataClass::text)
        data.requireNotBlanks(DataClass::texts)
        data.requireBefore(DataClass::date, LocalDate.now().plusDays(1))
        data.requireBeforeOrEquals(DataClass::date)
        data.requireBeforeOrEquals(DataClass::date, LocalDate.now())
        data.requireUrlFound(DataClass::url)
        data.requireUrlsFound(DataClass::urls)
        data.requireGreater(DataClass::integer, 0)
        data.requireGreater(DataClass::decimal, 0.0)
    }

    @Test fun `Require methods throw exceptions for incorrect data`() {
        val data = DataClass(
            text = " ",
            texts = listOf("text", " "),
            url = URL("https://example.com/not-found"),
            urls = listOf(URL("https://example.com"), URL("https://example.com/not-found")),
            integer = 0,
            decimal = 0.0,
        )

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        fail("'text' cannot be blank") { data.requireNotBlank(DataClass::text) }
        fail("'texts' cannot contain blanks") { data.requireNotBlanks(DataClass::texts) }
        fail("'date' must be before $today: $today") { data.requireBefore(DataClass::date, today) }
        fail("'date' must be before $today: $today") {
            data.requireBeforeOrEquals(DataClass::date, yesterday)
        }
        fail("'url' URL must be available: https://example.com/not-found") {
            data.requireUrlFound(DataClass::url)
        }
        fail("'urls' URL must be available: [https://example.com, https://example.com/not-found]") {
            data.requireUrlsFound(DataClass::urls)
        }
        fail("'integer' must be greater than 0: 0") {
            data.requireGreater(DataClass::integer, 0)
        }
        fail("'decimal' must be greater than 0.0: 0.0") {
            data.requireGreater(DataClass::decimal, 0.0)
        }
    }

    @Test fun `Require methods work properly with 'null' data`() {
        val data = DataClass(null, null, null, null, null, null, null)
        data.requireNotBlank(DataClass::text)
        data.requireNotBlanks(DataClass::texts)
        data.requireBefore(DataClass::date, LocalDate.now().plusDays(1))
        data.requireBeforeOrEquals(DataClass::date)
        data.requireBeforeOrEquals(DataClass::date, LocalDate.now())
        data.requireUrlFound(DataClass::url)
        data.requireGreater(DataClass::integer, 0)
        data.requireGreater(DataClass::decimal, 0.0)
    }

    private fun fail(message: String, block: () -> Unit) {
        val e = assertFailsWith<IllegalArgumentException>(block = block)
        assertEquals(message, e.message)
    }
}
