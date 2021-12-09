package com.soonyong.hong.batch.crawl.filter

import org.jsoup.nodes.Element

fun interface CrawlFilter {

    fun isAllowed(value: Element): Boolean
}