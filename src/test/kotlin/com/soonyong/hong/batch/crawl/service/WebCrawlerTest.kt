package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

private val log = KotlinLogging.logger {}

@SpringBootTest
@ActiveProfiles("test")
class WebCrawlerTest {

    @Autowired
    lateinit var webCrawler: WebCrawler

    @Test
    fun getTexts() {
        val target: CrawlTarget? = com.soonyong.hong.batch.config.crawl.get("ppomppu")
        requireNotNull(target)
        val results: List<String> = webCrawler.getTexts(target)
        log.debug { "result $results" }
    }
}