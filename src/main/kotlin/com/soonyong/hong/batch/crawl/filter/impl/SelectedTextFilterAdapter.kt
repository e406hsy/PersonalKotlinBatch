package com.soonyong.hong.batch.crawl.filter.impl

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import org.jsoup.nodes.Element
import java.util.function.Predicate

class SelectedTextFilterAdapter(private val comparator: Predicate<String>, private val cssSelector: String? = null) :
    CrawlFilter {

    override fun isAllowed(value: Element): Boolean {
        return comparator.test((this.cssSelector?.let { value.select(it).text() } ?: value.text()).trim())
    }
}