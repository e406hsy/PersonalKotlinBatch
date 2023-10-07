package com.soonyong.hong.batch.domain.crawl.filter.impl

import com.soonyong.hong.batch.domain.crawl.filter.TargetTextSelector
import org.jsoup.nodes.Element

class SimpleTextSelector : TargetTextSelector {
    override fun filterText(element: Element): String {
        return element.text()
    }
}