package com.soonyong.hong.batch.crawl.filter.impl

import com.soonyong.hong.batch.crawl.filter.CrawlFilter
import org.jsoup.nodes.Element
import java.util.function.Predicate

/**
 * CSS Selector를 하위 element를 선택하여 내부 텍스트를 검증하는 클래스
 *
 * @property cssSelector 하위 element 선택자. 없으면 현재 element를 사용한다.
 * @property comparator 선택된 텍스트를 검증하기 위한 검증자
 */
class SelectedTextFilterAdapter(private val comparator: Predicate<String>, private val cssSelector: String? = null) :
    CrawlFilter {

    /**
     * Element가 크롤링이 대상이 되는지 판단
     * @return 크롤링 대상일 경우 true 아니면 false
     */
    override fun isAllowed(value: Element): Boolean {
        return comparator.test((this.cssSelector?.let { value.select(it).text() } ?: value.text()).trim())
    }
}