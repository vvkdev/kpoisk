package com.vvkdev.data.remote.mapper

import com.vvkdev.data.remote.model.FilmYears
import kotlin.test.Test
import kotlin.test.assertEquals

class FilmDataMapperTest {
    // parseYears
    @Test
    fun `parseYears with zero year and null years list returns empty string`() {
        val result = parseYears(0, null)
        assertEquals("", result)
    }

    @Test
    fun `parseYears with negative year and null years list returns empty string`() {
        val result = parseYears(-1, null)
        assertEquals("", result)
    }

    @Test
    fun `parseYears with null year and null years list returns empty string`() {
        val result = parseYears(null, null)
        assertEquals("", result)
    }

    @Test
    fun `parseYears with year and null years list returns year`() {
        val result = parseYears(2020, null)
        assertEquals("2020", result)
    }

    @Test
    fun `parseYears with null year and null-start years list returns end year`() {
        val years = listOf(FilmYears(start = null, end = 2022))
        val result = parseYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `parseYears with null year and zero-start years list returns end year`() {
        val years = listOf(FilmYears(start = 0, end = 2022))
        val result = parseYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `parseYears with null year and negative-start years list returns end year`() {
        val years = listOf(FilmYears(start = -1, end = 2022))
        val result = parseYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `parseYears with null year and null-end years list returns start year`() {
        val years = listOf(FilmYears(start = 2020, end = null))
        val result = parseYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `parseYears with null year and zero-end years list returns start year`() {
        val years = listOf(FilmYears(start = 2020, end = 0))
        val result = parseYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `parseYears with null year and negative-end years list returns start year`() {
        val years = listOf(FilmYears(start = 2020, end = -1))
        val result = parseYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `parseYears with null year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYears(start = 2018, end = 2022))
        val result = parseYears(null, years)
        assertEquals("2018-2022", result)
    }

    @Test
    fun `parseYears with zero year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYears(start = 2018, end = 2022))
        val result = parseYears(0, years)
        assertEquals("2018-2022", result)

    }

    @Test
    fun `parseYears with negative year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYears(start = 2018, end = 2022))
        val result = parseYears(-1, years)
        assertEquals("2018-2022", result)

    }

    @Test
    fun `parseYears with middle year and same year list returns same year range`() {
        val years = listOf(FilmYears(start = 2018, end = 2018))
        val result = parseYears(2020, years)
        assertEquals("2018-2018", result)
    }

    @Test
    fun `parseYears with year and same year list returns combined range`() {
        val years = listOf(FilmYears(start = 2018, end = 2018))
        val result = parseYears(2017, years)
        assertEquals("2017-2018", result)
    }

    @Test
    fun `parseYears with year and years list returns combined range`() {
        val years = listOf(FilmYears(start = 2018, end = 2022))
        val result = parseYears(2020, years)
        assertEquals("2018-2022", result)
    }

    @Test
    fun `parseYears with year and two years ranges returns combined range`() {
        val years = listOf(FilmYears(start = 2018, end = 2022), FilmYears(start = 2023, end = 2025))
        val result = parseYears(2020, years)
        assertEquals("2018-2025", result)
    }

    // mapListToString
    data class TestItem(val name: String?)

    @Test
    fun `mapListToString with normal items returns joined string`() {
        val list = listOf(TestItem("A"), TestItem("B"), TestItem("C"))
        assertEquals("A • B • C", mapListToString(list) { it.name })
    }

    @Test
    fun `mapListToString with null list returns empty string`() {
        assertEquals("", mapListToString<String>(null) { it })
    }

    @Test
    fun `mapListToString with null items skips nulls`() {
        val list = listOf(TestItem("A"), null, TestItem("B"))
        assertEquals("A • B", mapListToString(list) { it?.name })
    }

    @Test
    fun `mapListToString with all null items returns empty string`() {
        val list = listOf<TestItem?>(null, null, null)
        assertEquals("", mapListToString(list) { it?.name })
    }

    @Test
    fun `mapListToString with blank names skips blanks`() {
        val list = listOf(TestItem("A"), TestItem(""), TestItem(" "), TestItem("B"))
        assertEquals("A • B", mapListToString(list) { it.name })
    }

    @Test
    fun `mapListToString with all blank or null names returns empty string`() {
        val list = listOf(TestItem(""), TestItem(" "), TestItem(null))
        assertEquals("", mapListToString(list) { it.name })
    }
}
