package com.soonyong.hong.batch.domain.crawl.service

import com.soonyong.hong.batch.adpter.config.crawl.getCrawlTarget
import com.soonyong.hong.batch.domain.provider.text.TextProvider
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val log = KotlinLogging.logger {}

internal class WebCrawlerTest {

  @Test
  fun getTexts() {
    val target: TextProvider? = getCrawlTarget("quasarzone")
    requireNotNull(target)
    val results: String = target.getText()
    log.debug { "result $results" }
  }
}