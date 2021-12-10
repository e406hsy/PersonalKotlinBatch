package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.config.crawl.getCrawlTarget
import com.soonyong.hong.batch.crawl.model.CrawlTarget
import org.springframework.stereotype.Service

@Service
class CrawlService(private val webCrawler: WebCrawler) {

    fun getTexts(title: String): List<String> {
        val crawlTarget: CrawlTarget =
            getCrawlTarget(title) ?: throw IllegalArgumentException("Crawl target is not found for title $title")
        return webCrawler.getTexts(crawlTarget)
    }
}