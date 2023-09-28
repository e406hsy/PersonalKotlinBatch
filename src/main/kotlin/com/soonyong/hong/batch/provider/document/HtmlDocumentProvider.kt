package com.soonyong.hong.batch.provider.document

import org.jsoup.nodes.Document

interface HtmlDocumentProvider {
  fun getDocument(): Document
}