package com.soonyong.hong.batch.domain.provider.document

import com.soonyong.hong.batch.domain.provider.text.SimpleTextProvider
import com.soonyong.hong.batch.domain.provider.text.TextProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BasicHtmlDocumentProvider(private val urlProvider: TextProvider) : HtmlDocumentProvider {

  constructor(url: String) : this(SimpleTextProvider(url))

  override fun getDocument(): Document = Jsoup.connect(urlProvider.getText())
    .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1")
    .timeout(5000)
    .get()
  override fun toString(): String {
    return "BasicHtmlDocumentProvider(urlProvider='$urlProvider')"
  }


}