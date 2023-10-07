package com.soonyong.hong.batch.domain.crawl.model

import com.soonyong.hong.batch.domain.crawl.filter.CrawlFilter
import com.soonyong.hong.batch.domain.crawl.filter.TargetTextSelector
import com.soonyong.hong.batch.domain.provider.document.HtmlDocumentProvider

data class CrawlTarget(
  val title: String,
  val filter: CrawlFilter,
  val htmlDocumentProvider: HtmlDocumentProvider,
  val baseCssSelector: String,
  val targetTextSelector: TargetTextSelector
)
