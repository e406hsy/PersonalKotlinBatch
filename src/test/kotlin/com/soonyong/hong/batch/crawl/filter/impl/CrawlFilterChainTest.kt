package com.soonyong.hong.batch.crawl.filter.impl

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CrawlFilterChainTest {
    var trueFilter = CrawlFilter { value: Element? -> true }
    var falseFilter = CrawlFilter { value: Element? -> false }

    @Test
    @DisplayName("AND test (true, ture) -> true")
    fun andTest1() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = trueFilter
        )
        Assertions.assertTrue(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("AND test (true, false) -> false")
    fun andTest2() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = falseFilter
        )
        Assertions.assertFalse(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("AND test (false, ture) -> false")
    fun andTest3() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = trueFilter
        )
        Assertions.assertFalse(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("AND test (false, false) -> false")
    fun andTest4() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = falseFilter
        )
        Assertions.assertFalse(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("OR test (true, false) -> true")
    fun orTest1() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = falseFilter
        )
        Assertions.assertTrue(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("OR test (false, ture) -> true")
    fun orTest2() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = trueFilter
        )
        Assertions.assertTrue(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("OR test (false, false) -> false")
    fun orTest3() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = falseFilter
        )
        Assertions.assertFalse(crawlFilterChain.isAllowed(Element("h1")))
    }

    @Test
    @DisplayName("OR test (true, ture) -> true")
    fun orTest4() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = trueFilter
        )
        Assertions.assertTrue(crawlFilterChain.isAllowed(Element("h1")))
    }
}