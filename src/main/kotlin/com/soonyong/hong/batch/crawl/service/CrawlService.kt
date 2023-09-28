package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.config.crawl.getCrawlTarget
import com.soonyong.hong.batch.provider.text.TextProvider
import org.springframework.stereotype.Service

@Service
class CrawlService() {

    fun getText(title: String): String {
        val textProvider: TextProvider = getCrawlTarget(title)
            ?: throw IllegalArgumentException("Crawl target is not found for title $title")
        return textProvider.getText()
    }
}