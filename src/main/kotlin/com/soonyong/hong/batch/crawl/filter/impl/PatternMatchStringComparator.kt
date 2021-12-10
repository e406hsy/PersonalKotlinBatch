package com.soonyong.hong.batch.crawl.filter.impl

import mu.KotlinLogging
import java.util.function.Predicate
import java.util.regex.Pattern

private val log = KotlinLogging.logger {}

class PatternMatchStringComparator(private val pattern: Pattern) : Predicate<String> {

    override fun test(value: String): Boolean {
        log.debug("is allowed called with pattern {} and value {}", pattern, value)
        val result = pattern.matcher(value).matches()
        log.debug("and result : {}", result)
        return result
    }
}