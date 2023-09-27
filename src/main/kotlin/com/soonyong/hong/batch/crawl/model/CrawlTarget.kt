package com.soonyong.hong.batch.crawl.model

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import com.soonyong.hong.batch.crawl.filter.TargetTextSelector
import com.soonyong.hong.batch.text.provider.HtmlDocumentProvider

data class CrawlTarget(
    val title: String,
    val filter: CrawlFilter,
    val htmlDocumentProvider: HtmlDocumentProvider,
    val baseCssSelector: String,
    val targetTextSelector: TargetTextSelector
)
