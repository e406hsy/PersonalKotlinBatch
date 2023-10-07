package com.soonyong.hong.batch.domain.crawl.filter.impl

import mu.KotlinLogging
import java.util.function.Predicate
import java.util.regex.Pattern

private val log = KotlinLogging.logger {}

/**
 * 패턴과 일치하는지 확인하기 위한 검증자
 * @property pattern 패턴
 */
class PatternMatchStringComparator(private val pattern: Pattern) : Predicate<String> {

    /**
     * 패턴과 일치하는지 확인한다.
     * @return 패턴과 일치하면 true, 아니면 false
     */
    override fun test(value: String): Boolean {
        log.debug("is allowed called with pattern {} and value {}", pattern, value)
        val result = pattern.matcher(value).matches()
        log.debug("and result : {}", result)
        return result
    }
}