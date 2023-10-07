package com.soonyong.hong.batch.domain.crawl.filter.impl

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.regex.Pattern

internal class PatternMatchStringComparatorTest {

    @Test
    internal fun testNotMatching() {
        val patternMatchStringComparator = PatternMatchStringComparator(
            pattern = Pattern.compile("^((?!aaa).)*$")
        )

        assertTrue(patternMatchStringComparator.test("aa"))
        assertTrue(patternMatchStringComparator.test("asdg"))
        assertTrue(patternMatchStringComparator.test("aada  sdf"))
        assertTrue(patternMatchStringComparator.test("aasdfaea"))
        assertTrue(patternMatchStringComparator.test("afdsfa"))
        assertFalse(patternMatchStringComparator.test("123aaa123"))
        assertFalse(patternMatchStringComparator.test("aaa123"))
        assertFalse(patternMatchStringComparator.test("123aaa"))
        assertFalse(patternMatchStringComparator.test("aaa"))
    }
}