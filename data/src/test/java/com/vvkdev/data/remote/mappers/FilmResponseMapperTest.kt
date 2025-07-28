package com.vvkdev.data.remote.mappers

import com.vvkdev.data.remote.models.FilmYearsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class FilmResponseMapperTest {
    // mapYears
    @Test
    fun `mapYears with zero year and null years list returns empty string`() {
        val result = mapYears(0, null)
        assertEquals("-", result)
    }

    @Test
    fun `mapYears with negative year and null years list returns empty string`() {
        val result = mapYears(-1, null)
        assertEquals("-", result)
    }

    @Test
    fun `mapYears with null year and null years list returns empty string`() {
        val result = mapYears(null, null)
        assertEquals("-", result)
    }

    @Test
    fun `mapYears with year and null years list returns year`() {
        val result = mapYears(2020, null)
        assertEquals("2020", result)
    }

    @Test
    fun `mapYears with null year and null-start years list returns end year`() {
        val years = listOf(FilmYearsResponse(start = null, end = 2022))
        val result = mapYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `mapYears with null year and zero-start years list returns end year`() {
        val years = listOf(FilmYearsResponse(start = 0, end = 2022))
        val result = mapYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `mapYears with null year and negative-start years list returns end year`() {
        val years = listOf(FilmYearsResponse(start = -1, end = 2022))
        val result = mapYears(null, years)
        assertEquals("2022", result)
    }

    @Test
    fun `mapYears with null year and null-end years list returns start year`() {
        val years = listOf(FilmYearsResponse(start = 2020, end = null))
        val result = mapYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `mapYears with null year and zero-end years list returns start year`() {
        val years = listOf(FilmYearsResponse(start = 2020, end = 0))
        val result = mapYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `mapYears with null year and negative-end years list returns start year`() {
        val years = listOf(FilmYearsResponse(start = 2020, end = -1))
        val result = mapYears(null, years)
        assertEquals("2020", result)
    }

    @Test
    fun `mapYears with null year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2022))
        val result = mapYears(null, years)
        assertEquals("2018-2022", result)
    }

    @Test
    fun `mapYears with zero year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2022))
        val result = mapYears(0, years)
        assertEquals("2018-2022", result)

    }

    @Test
    fun `mapYears with negative year and non-empty years list returns start-end range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2022))
        val result = mapYears(-1, years)
        assertEquals("2018-2022", result)

    }

    @Test
    fun `mapYears with middle year and same year list returns same year range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2018))
        val result = mapYears(2020, years)
        assertEquals("2018-2018", result)
    }

    @Test
    fun `mapYears with year and same year list returns combined range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2018))
        val result = mapYears(2017, years)
        assertEquals("2017-2018", result)
    }

    @Test
    fun `mapYears with year and years list returns combined range`() {
        val years = listOf(FilmYearsResponse(start = 2018, end = 2022))
        val result = mapYears(2020, years)
        assertEquals("2018-2022", result)
    }

    @Test
    fun `mapYears with year and two years ranges returns combined range`() {
        val years = listOf(
            FilmYearsResponse(start = 2018, end = 2022),
            FilmYearsResponse(start = 2023, end = 2025)
        )
        val result = mapYears(2020, years)
        assertEquals("2018-2025", result)
    }

    // listToBulletedString
    data class TestItem(val name: String?)

    @Test
    fun `listToBulletedString with normal items returns joined string`() {
        val list = listOf(TestItem("A"), TestItem("B"), TestItem("C"))
        assertEquals("A • B • C", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with null list returns empty string`() {
        assertEquals("", listToBulletedString<String>(null) { it })
    }

    @Test
    fun `listToBulletedString with empty list returns empty string`() {
        val list = emptyList<String>()
        assertEquals("", listToBulletedString(list) { it })
    }

    @Test
    fun `listToBulletedString with null item skips null`() {
        val list = listOf(TestItem("A"), null, TestItem("B"))
        assertEquals("A • B", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with null items skips nulls`() {
        val list = listOf(TestItem("A"), null, null, TestItem("B"))
        assertEquals("A • B", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with all null items returns empty string`() {
        val list = listOf<TestItem?>(null, null, null)
        assertEquals("", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with blank names skips blanks`() {
        val list = listOf(TestItem("A"), TestItem(""), TestItem(" "), TestItem("B"))
        assertEquals("A • B", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with blank name returns empty string`() {
        val list = listOf(TestItem(" "))
        assertEquals("", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with all blank names returns empty string`() {
        val list = listOf(TestItem(""), TestItem(" "))
        assertEquals("", listToBulletedString(list) { it.name })
    }

    @Test
    fun `listToBulletedString with all blank or null names returns empty string`() {
        val list = listOf(TestItem(""), TestItem(" "), TestItem(null))
        assertEquals("", listToBulletedString(list) { it.name })
    }
}
