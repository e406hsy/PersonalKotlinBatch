package com.soonyong.hong.batch.crawl.filter.impl

import com.soonyong.hong.batch.crawl.filter.TargetTextSelector
import org.jsoup.nodes.Element

class CompositeTargetTextSelector(
    private val delegate: TargetTextSelector, private val delimiter: String, private val next: TargetTextSelector

) : TargetTextSelector {
    override fun filterText(element: Element): String {
        return "${delegate.filterText(element)}$delimiter${next.filterText(element)}"
    }
}