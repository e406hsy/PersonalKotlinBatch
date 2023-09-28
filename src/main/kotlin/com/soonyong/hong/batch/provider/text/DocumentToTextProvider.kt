package com.soonyong.hong.batch.provider.text

import com.soonyong.hong.batch.provider.document.HtmlDocumentProvider

class DocumentToTextProvider(private val htmlDocumentProvider: HtmlDocumentProvider) :
  TextProvider {
  override fun getText(): String = htmlDocumentProvider.getDocument().html()
}