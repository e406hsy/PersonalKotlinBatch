package com.soonyong.hong.batch.crawl.model

import org.jsoup.nodes.Element

data class CrawlTarget(
    val title: String,
    val filter: (Element) -> Boolean,
    val url: String,
    val baseCssSelector: String,
    val targetCssSelector: String?
)
