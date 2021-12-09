package com.soonyong.hong.batch.crawl.service

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

private val log = KotlinLogging.logger {}

class WebCrawler {

    fun getText(target: CrawlTarget): List<String> {
        try {

            log.info { "crawl target : $target" }
            val document = Jsoup.connect(target.url).get()
            val elements: Elements = document.select(target.baseCssSelector)

            return elements.stream()
                .filter { element: Element ->
                    target.filter.isAllowed(
                        element
                    )
                }
                .map { element: Element ->
                    target.targetCssSelector?.let { element.select(it).text() } ?: element.text()
                }
                .collect(Collectors.toList())
        } catch (e: IOException) {
            return Collections.emptyList();
        }
    }
}
/**
return elements.stream()
.filter { element: Element ->
target.filter.isAllowed(
element
)
}
.map { element: Element ->
element.let {
when (
}
}
.collect(Collectors.toList())**/