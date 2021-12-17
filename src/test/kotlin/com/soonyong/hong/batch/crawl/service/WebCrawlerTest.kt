package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

private val log = KotlinLogging.logger {}

class WebCrawlerTest {

    val webCrawler: WebCrawler = WebCrawler()

    @Test
    fun getTexts() {
        val target: CrawlTarget? = com.soonyong.hong.batch.config.crawl.getCrawlTarget("qusarzone")
        requireNotNull(target)
        val results: List<String> = webCrawler.getTexts(target)
        log.debug { "result $results" }
    }
}