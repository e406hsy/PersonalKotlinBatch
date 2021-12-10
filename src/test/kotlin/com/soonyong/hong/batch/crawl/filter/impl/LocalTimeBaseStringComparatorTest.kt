package com.soonyong.hong.batch.crawl.filter.impl

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.function.Predicate

class LocalTimeBaseStringComparatorTest {

    @Test
    fun localTimeTest() {
        val filter: Predicate<String> = LocalTimeBaseStringComparator(
            formatter = DateTimeFormatter.ofPattern("HH-mm-ss"),
            zoneId = ZoneId.of("Asia/Seoul"),
            interval = 10,
            unit = ChronoUnit.MINUTES,
            type = LocalTimeBaseStringComparator.Type.AFTER
        )
        assertTrue(filter.test("00-00-00"))
    }

    @Test
    fun localTimeMinusTest() {
        LocalTime.now().plus(-1, ChronoUnit.MINUTES).isBefore(LocalTime.now())
    }

    @Test
    fun localTimeBaseTest() {
        LocalTime.now().minus(23, ChronoUnit.HOURS).minus(55, ChronoUnit.MINUTES).isBefore(LocalTime.now())
    }

    @Test
    fun localTimeBaseTestWithZone() {
        LocalTime.now(ZoneId.of("Asia/Seoul")).isAfter(LocalTime.of(0, 0, 0))
    }

    @Test
    fun localTimeBaseTestWithZone2() {
        LocalTime.now(ZoneId.of("Asia/Seoul")).isBefore(LocalTime.of(23, 59, 55))
    }

}