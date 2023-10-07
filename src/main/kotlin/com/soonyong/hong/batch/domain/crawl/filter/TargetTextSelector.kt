package com.soonyong.hong.batch.domain.crawl.filter

import org.jsoup.nodes.Element

fun interface TargetTextSelector {
    fun filterText(element: Element): String
}