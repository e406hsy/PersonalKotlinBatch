package com.soonyong.hong.batch.domain.crawl.service

import com.soonyong.hong.batch.adpter.config.crawl.getCrawlTarget
import com.soonyong.hong.batch.domain.provider.text.TextProvider
import org.springframework.stereotype.Service

@Service
class CrawlService() {

    fun getText(title: String): String {
        val textProvider: TextProvider = getCrawlTarget(title)
            ?: throw IllegalArgumentException("Crawl target is not found for title $title")
        return textProvider.getText()
    }
}