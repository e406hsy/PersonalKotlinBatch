package com.soonyong.hong.batch.crawl.filter

import org.jsoup.nodes.Element

fun interface CrawlFilter {

    /**
     * Element가 크롤링이 대상이 되는지 판단
     * @return 크롤링 대상일 경우 true 아니면 false
     */
    fun isAllowed(value: Element): Boolean
}