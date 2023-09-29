package com.soonyong.hong.batch.provider.document

import com.soonyong.hong.batch.provider.text.SimpleTextProvider
import com.soonyong.hong.batch.provider.text.TextProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BasicHtmlDocumentProvider(private val urlProvider: TextProvider) : HtmlDocumentProvider {

  constructor(url: String) : this(SimpleTextProvider(url))

  override fun getDocument(): Document = Jsoup.connect(urlProvider.getText()).get()
  override fun toString(): String {
    return "BasicHtmlDocumentProvider(urlProvider='$urlProvider')"
  }


}