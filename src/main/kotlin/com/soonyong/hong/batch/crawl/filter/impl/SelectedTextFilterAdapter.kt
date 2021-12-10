package com.soonyong.hong.batch.crawl.filter.impl

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import org.jsoup.nodes.Element
import java.util.function.Predicate

class SelectedTextFilterAdapter(private val comparator: Predicate<String>, private val cssSelector: String) :
    CrawlFilter {

    override fun isAllowed(value: Element): Boolean {
        return comparator.test(value.select(cssSelector).text().trim { it <= ' ' })
    }
}