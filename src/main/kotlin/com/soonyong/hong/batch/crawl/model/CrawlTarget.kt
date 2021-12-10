package com.soonyong.hong.batch.crawl.model

import com.soonyong.hong.batch.crawl.filter.CrawlFilter

data class CrawlTarget(
    val title: String,
    val filter: CrawlFilter,
    val url: String,
    val baseCssSelector: String,
    val targetCssSelector: String?
)
