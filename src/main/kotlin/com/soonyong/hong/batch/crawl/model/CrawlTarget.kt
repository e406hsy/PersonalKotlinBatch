package com.soonyong.hong.batch.crawl.model

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import com.soonyong.hong.batch.crawl.filter.TargetTextSelector

data class CrawlTarget(
    val title: String,
    val filter: CrawlFilter,
    val url: String,
    val baseCssSelector: String,
    val targetTextSelector: TargetTextSelector
)
