package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*

private val log = KotlinLogging.logger {}

@Service
class WebCrawler {

    fun getTexts(target: CrawlTarget): List<String> {
        return try {

            log.info { "crawl target : $target" }
            val document = Jsoup.connect(target.url).get()
            val elements: Elements = document.select(target.baseCssSelector)

            elements.filter { element: Element -> target.filter.isAllowed(element) }.map { element: Element ->
                target.targetTextSelector.filterText(element)
            }

        } catch (e: IOException) {
            Collections.emptyList()
        }
    }

}