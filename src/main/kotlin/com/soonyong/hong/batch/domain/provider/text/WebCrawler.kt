package com.soonyong.hong.batch.domain.provider.text

import com.soonyong.hong.batch.domain.crawl.model.CrawlTarget
import mu.KotlinLogging
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.util.*

private val log = KotlinLogging.logger {}

class WebCrawler(private val target: CrawlTarget) : TextsProvider {

  override fun getTexts(): List<String> {
    return try {

      log.info { "crawl target : $target" }
      val document = target.htmlDocumentProvider.getDocument()
      val elements: Elements = document.select(target.baseCssSelector)

      elements.filter { element: Element -> target.filter.isAllowed(element) }
        .map { element: Element ->
          val filterText = target.targetTextSelector.filterText(element)
          log.debug { "from Element : $element extracted $filterText" }
          filterText
        }

    } catch (e: IOException) {
      log.error("crawl failed", e)
      Collections.emptyList()
    }
  }

}