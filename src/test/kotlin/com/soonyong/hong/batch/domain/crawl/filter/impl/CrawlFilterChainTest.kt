package com.soonyong.hong.batch.domain.crawl.filter.impl

import com.soonyong.hong.batch.domain.crawl.filter.CrawlFilter
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.jsoup.nodes.Element

class CrawlFilterChainTest : AnnotationSpec() {
    private val trueFilter = CrawlFilter { true }
    private val falseFilter = CrawlFilter { false }

    @Test
    fun `AND test (true, true) then true`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = trueFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe true
    }

    @Test
    fun `AND test (true, false) then false`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = falseFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe false
    }

    @Test
    fun `AND test (false, true) then false`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = trueFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe false
    }

    @Test
    fun `AND test (false, false) then false`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = falseFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe false
    }

    @Test
    fun `OR test (true, false) then true`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = falseFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe true
    }

    @Test
    fun `OR test (false, true) then true`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = trueFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe true
    }

    @Test
    fun `OR test (false, false) then false`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = falseFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = falseFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe false
    }

    @Test
    fun `OR test (true, true) then true`() {
        val crawlFilterChain = CrawlFilterChain(
            delegate = trueFilter, delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = trueFilter
        )
        crawlFilterChain.isAllowed(Element("h1")) shouldBe true
    }
}