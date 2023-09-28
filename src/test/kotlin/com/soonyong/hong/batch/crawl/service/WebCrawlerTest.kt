package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.provider.text.TextProvider
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val log = KotlinLogging.logger {}

internal class WebCrawlerTest {

    @Test
    fun getTexts() {
        val target: TextProvider? =
            com.soonyong.hong.batch.config.crawl.getCrawlTarget("quasarzone")
        requireNotNull(target)
        val results: String = target.getText()
        log.debug { "result $results" }
    }
}