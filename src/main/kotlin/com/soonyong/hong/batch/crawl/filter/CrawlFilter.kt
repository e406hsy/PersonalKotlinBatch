package com.soonyong.hong.batch.crawl.filter

import org.jsoup.nodes.Element

@FunctionalInterface
interface CrawlFilter {

    fun isAllowed(value: Element): Boolean
}