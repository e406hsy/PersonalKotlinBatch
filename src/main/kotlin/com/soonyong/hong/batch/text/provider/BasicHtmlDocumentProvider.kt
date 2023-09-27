package com.soonyong.hong.batch.text.provider

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BasicHtmlDocumentProvider(private val url: String) : HtmlDocumentProvider {
  override fun getDocument(): Document = Jsoup.connect(url).get()

}