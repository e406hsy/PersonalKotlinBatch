package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val log = KotlinLogging.logger {}

class WebCrawlerTest {

    val webCrawler: WebCrawler = WebCrawler()

    @Test
    fun getTexts() {
        val target: CrawlTarget? = com.soonyong.hong.batch.config.crawl.getCrawlTarget("quasarzone")
        requireNotNull(target)
        val results: List<String> = webCrawler.getTexts(target)
        log.debug { "result $results" }
    }
}