package com.soonyong.hong.batch.domain.provider.document

import org.jsoup.nodes.Document

interface HtmlDocumentProvider {
  fun getDocument(): Document
}