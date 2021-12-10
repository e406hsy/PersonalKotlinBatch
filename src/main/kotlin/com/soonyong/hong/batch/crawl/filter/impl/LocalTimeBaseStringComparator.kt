package com.soonyong.hong.batch.crawl.filter.impl

import mu.KotlinLogging
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalUnit
import java.util.function.BiPredicate
import java.util.function.Predicate

private val log = KotlinLogging.logger {}

class LocalTimeBaseStringComparator(
    private val formatter: DateTimeFormatter,
    private val zoneId: ZoneId,
    private val interval: Long,
    private val unit: TemporalUnit,
    private val type: Type
) : Predicate<String> {

    override fun test(value: String): Boolean {
        val targetTime: LocalTime = LocalTime.parse(value, formatter)
        log.debug("is allowed called with value {}", value)
        val result = type.compare(LocalTime.now(zoneId).minus(interval, unit), targetTime)
        log.debug("and result : {}", result)
        return result
    }

    enum class Type(private val comparator: BiPredicate<LocalTime, LocalTime>) {
        AFTER({ v1, v2 -> v1.isAfter(v2) }), BEFORE({ v1, v2 -> v2.isAfter(v1) }), EQUALS({ v1, v2 -> v1.equals(v2) });

        fun compare(time1: LocalTime, time2: LocalTime): Boolean {
            return comparator.test(time1, time2)
        }
    }
}