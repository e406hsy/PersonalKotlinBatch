package com.soonyong.hong.batch.crawl.filter.impl;

import com.soonyong.hong.batch.crawl.filter.TargetTextSelector
import org.jsoup.nodes.Element

class CssSelectorTargetTextSelector(
    private val cssSelector: String
) : TargetTextSelector {
    override fun filterText(element: Element): String {
        return element.select(cssSelector).text()
    }
}
