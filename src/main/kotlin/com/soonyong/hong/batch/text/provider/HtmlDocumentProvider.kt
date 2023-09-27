package com.soonyong.hong.batch.text.provider

import org.jsoup.nodes.Document

interface HtmlDocumentProvider {
  fun getDocument(): Document
}